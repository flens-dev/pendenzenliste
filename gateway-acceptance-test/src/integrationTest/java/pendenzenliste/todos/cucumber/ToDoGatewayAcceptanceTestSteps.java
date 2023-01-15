package pendenzenliste.todos.cucumber;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import pendenzenliste.gateway.inmemory.InMemoryToDoGateway;
import pendenzenliste.gateway.redis.RedisToDoGateway;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.gateway.filesystem.FilesystemToDoGateway;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoAggregate;
import pendenzenliste.todos.model.ToDoStateValueObject;
import redis.clients.jedis.Jedis;

public class ToDoGatewayAcceptanceTestSteps
{
  private Optional<ToDoAggregate> todo;
  private String id;

  private ToDoGateway gateway;

  @Given("that no todos exist")
  public void givenThatNoTodosExist()
  {
  }

  @Given("that I enter the todo id {string}")
  public void givenThatIEnterTheTodoId(final String id)
  {
    this.id = id;
  }

  @When("I try to fetch the todo")
  public void whenITryToFetchTheTodo()
  {
    todo = gateway.findById(new IdentityValueObject(id));
  }

  @Then("I should have received no todo")
  public void thenIShouldHaveReceivedNoTodo()
  {
    assertThat(todo).isNotPresent();
  }

  @Given("that I configure the application to use the {string} todo gateway")
  public void givenThatIConfigureTheApplicationToUseTheBackendTodoGateway(final String type)
  {
    switch (type)
    {
      case "redis" -> this.gateway = createRedisGateway();
      case "inmemory" -> this.gateway = createInMemoryGateway();
      case "filesystem" -> this.gateway = createFilesystemGateway();

      default -> throw new IllegalStateException("Unexpected value: " + type);
    }
  }

  @Given("that the following todos exist:")
  public void givenThatTheFollowingTodosExist(final DataTable data)
  {
    for (final Map<String, String> row : data.asMaps())
    {
      final var todo = ToDoAggregate.builder()
          .identity(row.get("identity"))
          .headline(row.get("headline"))
          .description(Optional.ofNullable(row.get("description")).orElse(""))
          .created(mapDate(row.get("created")))
          .lastModified(mapDate(row.get("last modified")))
          .completed(mapDate(row.get("completed")))
          .state(ToDoStateValueObject.valueOf(row.get("state")))
          .build();

      gateway.store(todo);
    }
  }

  @Then("I should have received a todo")
  public void thenIShouldHaveReceivedATodo()
  {
    assertThat(todo).isPresent();
  }

  @Then("the todo should have the following data:")
  public void thenTheTodoShouldHaveTheFollowingData(final DataTable data)
  {
    final var expectedTodo = ToDoAggregate.builder()
        .identity(data.row(1).get(0))
        .headline(data.row(1).get(1))
        .description(Optional.ofNullable(data.row(1).get(2)).orElse(""))
        .created(mapDate(data.row(1).get(3)))
        .lastModified(mapDate(data.row(1).get(4)))
        .completed(mapDate(data.row(1).get(5)))
        .state(ToDoStateValueObject.valueOf(data.row(1).get(6)))
        .build();

    assertThat(todo).map(ToDoAggregate::aggregateRoot).hasValue(expectedTodo.aggregateRoot());
  }

  private LocalDateTime mapDate(final String value)
  {
    return Optional.ofNullable(value).filter(v -> !v.isEmpty())
        .map(v -> LocalDateTime.parse(value))
        .orElse(null);
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
      final var storagePath =
          Files.createTempDirectory("todoAcceptanceTest").toAbsolutePath();

      return new FilesystemToDoGateway(storagePath.toString().concat("/todoData"),
          EventBus.defaultEventBus());
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
    return new InMemoryToDoGateway(EventBus.defaultEventBus());
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

    return new RedisToDoGateway(connection, EventBus.defaultEventBus());
  }
}
