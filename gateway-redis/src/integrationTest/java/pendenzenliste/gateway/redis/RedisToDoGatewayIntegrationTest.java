package pendenzenliste.gateway.redis;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import pendenzenliste.domain.todos.DescriptionValueObject;
import pendenzenliste.domain.todos.HeadlineValueObject;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoEntity;
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

    final var seededToDo = ToDoEntity.createOpenToDo(new HeadlineValueObject("Some todo"),
        new DescriptionValueObject(""));

    gateway.store(seededToDo);

    final var fetchedToDo = gateway.findById(seededToDo.identity());

    final var assertions = new SoftAssertions();

    assertions.assertThat(fetchedToDo).isPresent();
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(todo -> assertThat(todo.identity()).isEqualTo(seededToDo.identity()));
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(todo -> assertThat(todo.headline()).isEqualTo(seededToDo.headline()));
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(
            todo -> assertThat(todo.description()).isEqualTo(seededToDo.description()));
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(todo -> assertThat(todo.completed()).isEqualTo(seededToDo.completed()));
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(
            todo -> assertThat(todo.lastModified()).isEqualTo(seededToDo.lastModified()));
    assertions.assertThat(fetchedToDo)
        .hasValueSatisfying(todo -> assertThat(todo.created()).isEqualTo(seededToDo.created()));

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

    return new RedisToDoGateway(connection);
  }
}
