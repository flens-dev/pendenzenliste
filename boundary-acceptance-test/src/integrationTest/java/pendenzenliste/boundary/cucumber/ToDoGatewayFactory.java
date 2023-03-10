package pendenzenliste.boundary.cucumber;

import java.io.IOException;
import java.nio.file.Files;

import static java.util.Objects.requireNonNull;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import pendenzenliste.gateway.inmemory.InMemoryToDoGateway;
import pendenzenliste.gateway.redis.RedisToDoGateway;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.gateway.filesystem.FilesystemToDoGateway;
import redis.clients.jedis.Jedis;

/**
 * A factory that can be used to create {@link ToDoGateway} instances.
 */
public class ToDoGatewayFactory
{
  private final EventBus eventBus;

  /**
   * Creates a new instance.
   *
   * @param eventBus The event bus that should be used by this instance.
   */
  public ToDoGatewayFactory(final EventBus eventBus)
  {
    this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
  }

  /**
   * Creates a new instance for the given gateway type.
   *
   * @param type The type.
   * @return The todo gateway.
   */
  public ToDoGateway create(final String type)
  {
    switch (type)
    {
      case "redis":
        return createRedisGateway();

      case "inmemory":
        return createInMemoryGateway();

      case "filesystem":
        return createFilesystemGateway();

      default:
        throw new IllegalStateException("Unknown backend " + type);
    }
  }

  /**
   * Creates the filesystem gateway.
   *
   * @return The filesystem gateway.
   */
  private ToDoGateway createFilesystemGateway()
  {
    try
    {
      final var storagePath = Files.createTempDirectory("todoAcceptanceTest").toAbsolutePath();

      return new FilesystemToDoGateway(storagePath.toString().concat("/todoData"), eventBus);
    } catch (final IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates an in-memory gateway.
   *
   * @return The in-memory gateway.
   */
  private ToDoGateway createInMemoryGateway()
  {
    return new InMemoryToDoGateway(eventBus);
  }

  /**
   * Creates a redis gateway.
   *
   * @return The redis gateway.
   */
  private ToDoGateway createRedisGateway()
  {
    final var redis =
        new GenericContainer<>(DockerImageName.parse("redis:7.0.7-alpine")).withExposedPorts(6379);

    redis.start();

    final var connection = new Jedis(redis.getHost(), redis.getMappedPort(6379));

    return new RedisToDoGateway(connection, eventBus);
  }
}
