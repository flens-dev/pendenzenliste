package pendenzenliste.boundary.cucumber;

import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.flywaydb.core.Flyway;
import org.jooq.impl.DSL;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import pendenzenliste.acceptancetests.ToolVersion;
import pendenzenliste.acceptancetests.VersionParser;
import pendenzenliste.gateway.inmemory.InMemoryToDoGateway;
import pendenzenliste.gateway.postgresql.PostgreSQLToDoGateway;
import pendenzenliste.gateway.redis.RedisToDoGateway;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.gateway.eclipsestore.EclipseStoreTodoGateway;
import pendenzenliste.todos.gateway.filesystem.FilesystemToDoGateway;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * A factory that can be used to create {@link ToDoGateway} instances.
 */
public class ToDoGatewayFactory {

    private final Collection<GenericContainer<?>> runningContainers = new ArrayList<>();

    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param eventBus The event bus that should be used by this instance.
     */
    public ToDoGatewayFactory(final EventBus eventBus) {
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * Creates a new instance for the given gateway type.
     *
     * @param type The type.
     * @return The todo gateway.
     */
    public ToDoGateway create(final String type) {
        ToolVersion toolVersion = VersionParser.parse(type);

        switch (toolVersion.tool()) {
            case "redis":
                return createRedisGateway();

            case "inmemory":
                return createInMemoryGateway();

            case "filesystem":
                return createFilesystemGateway();

            case "postgresql":
                return createPostgreSQLGateway(toolVersion);

            case "eclipse-store":
                return createEclipseStoreGateway();

            default:
                throw new IllegalStateException("Unknown backend " + type);
        }
    }

    /**
     * Creates the eclipse store gateway.
     *
     * @return The eclipse store gateway.
     */
    private ToDoGateway createEclipseStoreGateway() {
        try {
            final var storageManager = EmbeddedStorage.start(
                    Files.createTempDirectory("todos")
            );

            storageManager.storeRoot();

            return new EclipseStoreTodoGateway(storageManager, eventBus);
        } catch (final Throwable e) {
            throw new IllegalStateException("Failed to create Eclipse Store Gateway", e);
        }
    }

    /**
     * Creates the PostgreSQL gateway.
     *
     * @return The PostgreSQL gateway.
     */
    private ToDoGateway createPostgreSQLGateway(final ToolVersion toolVersion) {
        final String version = toolVersion.version()
                .orElseThrow(() -> new IllegalArgumentException("Missing version parameter: " + toolVersion));

        final var postgresql = new PostgreSQLContainer<>("postgres:" + version)
                .withDatabaseName("pendenzenliste");

        postgresql.start();
        runningContainers.add(postgresql);

        Flyway.configure()
                .dataSource(postgresql.getJdbcUrl(),
                        postgresql.getUsername(),
                        postgresql.getPassword())
                .load()
                .migrate();

        return new PostgreSQLToDoGateway(DSL.using(postgresql.getJdbcUrl(), postgresql.getUsername(), postgresql.getPassword()), eventBus);
    }

    /**
     * Creates the filesystem gateway.
     *
     * @return The filesystem gateway.
     */
    private ToDoGateway createFilesystemGateway() {
        try {
            final var storagePath = Files.createTempDirectory("todoAcceptanceTest").toAbsolutePath();

            return new FilesystemToDoGateway(storagePath.toString().concat("/todoData"), eventBus);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an in-memory gateway.
     *
     * @return The in-memory gateway.
     */
    private ToDoGateway createInMemoryGateway() {
        return new InMemoryToDoGateway(eventBus);
    }

    /**
     * Creates a redis gateway.
     *
     * @return The redis gateway.
     */
    private ToDoGateway createRedisGateway() {
        final var redis =
                new GenericContainer<>(DockerImageName.parse("redis:7.2.1-alpine")).withExposedPorts(6379);

        redis.start();
        runningContainers.add(redis);

        final var connection = new Jedis(redis.getHost(), redis.getMappedPort(6379));

        return new RedisToDoGateway(connection, eventBus);
    }

    public void tearDown() {
        for (final var runningContainer : runningContainers) {
            runningContainer.stop();
        }
    }
}
