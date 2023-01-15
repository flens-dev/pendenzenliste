package pendenzenliste.gateway.redis;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoAggregate;
import pendenzenliste.domain.todos.ToDoStateValueObject;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.messaging.InMemoryEventBus;
import pendenzenliste.messaging.Subscriber;
import redis.clients.jedis.Jedis;

public class RedisToDoGatewayIntegrationTest
{
  @Test
  public void findById_notExistingToDo() throws Throwable
  {
    final RedisToDoGateway gateway = setupGateway();

    assertThat(gateway.findById(new IdentityValueObject("not-exists"))).isEmpty();
  }


  @Test
  public void findById_existingToDo()
  {
    final RedisToDoGateway gateway = setupGateway();

    final var seededToDo = ToDoAggregate.builder().randomIdentity()
        .created(LocalDateTime.now())
        .lastModified(LocalDateTime.now())
        .headline("Some todo")
        .description("")
        .state(ToDoStateValueObject.OPEN)
        .build();

    gateway.store(seededToDo);

    final var fetchedToDo = gateway.findById(seededToDo.aggregateRoot().identity());

    final var assertions = new SoftAssertions();

    assertions.assertThat(fetchedToDo).isPresent();
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(
            todo -> assertThat(todo.aggregateRoot().identity()).isEqualTo(
                seededToDo.aggregateRoot().identity()));
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(
            todo -> assertThat(todo.aggregateRoot().headline()).isEqualTo(
                seededToDo.aggregateRoot().headline()));
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(
            todo -> assertThat(todo.aggregateRoot().description()).isEqualTo(
                seededToDo.aggregateRoot().description()));
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(
            todo -> assertThat(todo.aggregateRoot().completed()).isEqualTo(
                seededToDo.aggregateRoot().completed()));
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(
            todo -> assertThat(todo.aggregateRoot().lastModified()).isEqualTo(
                seededToDo.aggregateRoot().lastModified()));
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(
            todo -> assertThat(todo.aggregateRoot().created()).isEqualTo(
                seededToDo.aggregateRoot().created()));

    assertions.assertAll();
  }

  /**
   * Sets up the gateway.
   *
   * @return The gateway.
   */
  private RedisToDoGateway setupGateway()
  {
    final var redis =
        new GenericContainer<>(DockerImageName.parse("redis:7.0.7-alpine")).withExposedPorts(6379);

    redis.start();

    final var connection = new Jedis(redis.getHost(), redis.getMappedPort(6379));

    return new RedisToDoGateway(connection, new InMemoryEventBus());
  }
}
