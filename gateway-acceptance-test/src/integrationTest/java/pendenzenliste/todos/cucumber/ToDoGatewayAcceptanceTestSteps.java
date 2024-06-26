package pendenzenliste.todos.cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.flywaydb.core.Flyway;
import org.jooq.impl.DSL;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import pendenzenliste.gateway.inmemory.InMemoryToDoGateway;
import pendenzenliste.gateway.postgresql.PostgreSQLToDoGateway;
import pendenzenliste.gateway.redis.RedisToDoGateway;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.gateway.eclipsestore.EclipseStoreToDoGatewayProvider;
import pendenzenliste.todos.gateway.filesystem.FilesystemToDoGateway;
import pendenzenliste.todos.model.*;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ToDoGatewayAcceptanceTestSteps {
    private Optional<ToDoAggregate> todo;
    private String id;

    private ToDoGateway gateway;
    private boolean deleted;

    @Given("that no todos exist")
    public void givenThatNoTodosExist() {
    }

    @Given("that I enter the todo id {string}")
    public void givenThatIEnterTheTodoId(final String id) {
        this.id = id;
    }

    @When("I try to fetch the todo")
    public void whenITryToFetchTheTodo() {
        todo = gateway.findById(new IdentityValueObject(id));
    }

    @Then("I should have received no todo")
    public void thenIShouldHaveReceivedNoTodo() {
        assertThat(todo).isNotPresent();
    }

    @Given("that I configure the application to use the {string} todo gateway")
    public void givenThatIConfigureTheApplicationToUseTheBackendTodoGateway(final String type) {
        switch (type) {
            case "eclipse-store" -> this.gateway = createEclipseStoreGateway();
            case "redis" -> this.gateway = createRedisGateway();
            case "inmemory" -> this.gateway = createInMemoryGateway();
            case "filesystem" -> this.gateway = createFilesystemGateway();
            case "postgresql" -> this.gateway = createPostgreSQLGateway();

            default ->
                    throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    @Given("that the following todos exist:")
    public void givenThatTheFollowingTodosExist(final DataTable data) {
        for (final Map<String, String> row : data.asMaps()) {
            final var todo = new ToDoAggregate(new ToDoEntity(
                    new IdentityValueObject(row.get("identity")),
                    new HeadlineValueObject(row.get("headline")),
                    new DescriptionValueObject(Optional.ofNullable(row.get("description")).orElse("")),
                    new CreatedTimestampValueObject(LocalDateTime.parse(row.get("created"))),
                    new LastModifiedTimestampValueObject(LocalDateTime.parse(row.get("last modified"))),
                    Optional.ofNullable(row.getOrDefault("completed", null))
                            .map(LocalDateTime::parse)
                            .map(CompletedTimestampValueObject::new)
                            .orElse(null),
                    ToDoStateValueObject.valueOf(row.get("state"))
            ), gateway, EventBus.defaultEventBus());

            gateway.store(todo);
        }
    }

    @Then("I should have received a todo")
    public void thenIShouldHaveReceivedATodo() {
        assertThat(todo).isPresent();
    }

    @Then("the todo should have the following data:")
    public void thenTheTodoShouldHaveTheFollowingData(final DataTable data) {
        final var expectedTodo = new ToDoAggregate(new ToDoEntity(
                new IdentityValueObject(data.row(1).get(0)),
                new HeadlineValueObject(data.row(1).get(1)),
                new DescriptionValueObject(Optional.ofNullable(data.row(1).get(2)).orElse("")),
                new CreatedTimestampValueObject(mapDate(data.row(1).get(3))),
                new LastModifiedTimestampValueObject(mapDate(data.row(1).get(4))),
                Optional.ofNullable(mapDate(data.row(1).get(5))).map(CompletedTimestampValueObject::new).orElse(null),
                ToDoStateValueObject.valueOf(data.row(1).get(6))
        ), gateway, EventBus.defaultEventBus());

        assertThat(todo).map(ToDoAggregate::aggregateRoot).hasValue(expectedTodo.aggregateRoot());
    }

    private LocalDateTime mapDate(final String value) {
        return Optional.ofNullable(value).filter(v -> !v.isEmpty()).map(v -> LocalDateTime.parse(value)).orElse(null);
    }

    /**
     * Creates the filesystem gateway.
     *
     * @return The filesystem gateway.
     */
    private ToDoGateway createFilesystemGateway() {
        try {
            final var storagePath = Files.createTempDirectory("todoAcceptanceTest").toAbsolutePath();

            return new FilesystemToDoGateway(storagePath.toString().concat("/todoData"), EventBus.defaultEventBus());
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
        return new InMemoryToDoGateway(EventBus.defaultEventBus());
    }


    /**
     * Creates an eclipse store gateway.
     *
     * @return The eclipse store gateway.
     */
    private ToDoGateway createEclipseStoreGateway() {
        return new EclipseStoreToDoGatewayProvider().getInstance();
    }

    /**
     * Creates a redis gateway.
     *
     * @return The redis gateway.
     */
    private ToDoGateway createRedisGateway() {
        final var redis = new GenericContainer<>(DockerImageName.parse("redis:7.0.11-alpine")).withExposedPorts(6379);

        redis.start();

        final var connection = new Jedis(redis.getHost(), redis.getMappedPort(6379));

        return new RedisToDoGateway(connection, EventBus.defaultEventBus());
    }


    /**
     * Creates a new PostgreSQL based gateway.
     *
     * @return The PostgreSQL gateway.
     */
    private ToDoGateway createPostgreSQLGateway() {
        final var postgresql = new PostgreSQLContainer<>("postgres:15.4-alpine3.18")
                .withDatabaseName("pendenzenliste");

        postgresql.start();

        Flyway.configure()
                .dataSource(postgresql.getJdbcUrl(),
                        postgresql.getUsername(),
                        postgresql.getPassword())
                .load()
                .migrate();

        return new PostgreSQLToDoGateway(DSL.using(postgresql.getJdbcUrl(), postgresql.getUsername(), postgresql.getPassword()), EventBus.defaultEventBus());
    }

    @When("I try to delete the todo")
    public void whenITryToDeleteTheTodo() {
        this.deleted = gateway.delete(new IdentityValueObject(id));
    }

    @Then("deleting the todo should have failed")
    public void deletingTheTodoShouldHaveFailed() {
        assertThat(deleted).isFalse();
    }

    @Then("deleting the todo should have succeeded")
    public void thenDeletingTheTodoShouldHaveSucceeded() {
        assertThat(deleted).isTrue();
    }
}
