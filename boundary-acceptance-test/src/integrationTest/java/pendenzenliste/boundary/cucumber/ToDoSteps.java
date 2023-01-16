package pendenzenliste.boundary.cucumber;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.boundary.in.CompleteToDoRequest;
import pendenzenliste.todos.boundary.in.CreateToDoRequest;
import pendenzenliste.todos.boundary.in.DeleteToDoRequest;
import pendenzenliste.todos.boundary.in.FetchToDoRequest;
import pendenzenliste.todos.boundary.in.ResetToDoRequest;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.todos.boundary.in.UpdateToDoRequest;
import pendenzenliste.todos.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.CreateToDoResponse;
import pendenzenliste.todos.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoResponse;
import pendenzenliste.todos.boundary.out.ToDoCreatedResponse;
import pendenzenliste.todos.boundary.out.ToDoCreationFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoFetchedResponse;
import pendenzenliste.todos.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.todos.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.UpdateToDoResponse;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.model.ToDoAggregate;
import pendenzenliste.todos.model.ToDoCompletedEvent;
import pendenzenliste.todos.model.ToDoCreatedEvent;
import pendenzenliste.todos.model.ToDoDeletedEvent;
import pendenzenliste.todos.model.ToDoEvent;
import pendenzenliste.todos.model.ToDoReopenedEvent;
import pendenzenliste.todos.model.ToDoStateValueObject;
import pendenzenliste.todos.model.ToDoUpdatedEvent;
import pendenzenliste.todos.usecases.ToDoUseCaseFactory;

/**
 * The steps used to execute the ToDo acceptance tests.
 */
public class ToDoSteps
{
  private ToDoGateway gateway;

  private final ToDoOutputBoundaryFactory outputBoundaryFactory =
      new ToDoOutputBoundaryFactory()
      {
        @Override
        public CreateToDoOutputBoundary create()
        {
          return new CreateToDoOutputBoundary()
          {
            @Override
            public void handleSuccessfulResponse(final ToDoCreatedResponse response)
            {
              createResponse = response;
            }

            @Override
            public void handleFailedResponse(final ToDoCreationFailedResponse response)
            {
              createResponse = response;
            }
          };
        }

        @Override
        public FetchToDoListOutputBoundary list()
        {
          return mock(FetchToDoListOutputBoundary.class);
        }

        @Override
        public FetchToDoOutputBoundary fetch()
        {
          return new FetchToDoOutputBoundary()
          {
            @Override
            public void handleFailedResponse(final FetchToDoFailedResponse response)
            {
              fetchResponse = response;
            }

            @Override
            public void handleSuccessfulResponse(final ToDoFetchedResponse response)
            {
              fetchResponse = response;
            }
          };
        }

        @Override
        public UpdateToDoOutputBoundary update()
        {
          return new UpdateToDoOutputBoundary()
          {
            @Override
            public void handleSuccessfulResponse(final ToDoUpdatedResponse response)
            {
              updateResponse = response;
            }

            @Override
            public void handleFailedResponse(final ToDoUpdateFailedResponse response)
            {
              updateResponse = response;
            }
          };
        }
      };

  private final EventBus eventPublisher = mock(EventBus.class);

  private final ToDoInputBoundaryFactory factory = new ToDoUseCaseFactory(() -> gateway,
      outputBoundaryFactory, eventPublisher);

  private String id;

  private String headline;

  private String description;

  private FetchToDoResponse fetchResponse;

  private UpdateToDoResponse updateResponse;

  private CreateToDoResponse createResponse;

  @Given("that I do not enter an ID")
  public void givenThatIDoNotEnterAnID()
  {
  }


  @Given("that I enter the ID {string}")
  public void givenThatIEnterTheID(String id)
  {
    this.id = id;
  }


  @Given("that the ToDo does not exist")
  public void givenThatTheToDoDoesNotExist()
  {
    gateway.fetchAll().forEach(todo -> gateway.delete(todo.aggregateRoot().identity()));
  }


  @Given("that the following ToDo exists:")
  public void givenThatTheFollowingToDoExists(DataTable data)
  {
    for (final Map<String, String> row : data.asMaps())
    {
      gateway.store(parseToDoFrom(row));
    }
  }

  @Given("that deleting the ToDo fails")
  public void givenThatDeletingTheToDoFails()
  {
  }

  @Given("that deleting the ToDo succeeds")
  public void givenThatDeletingTheToDoSucceeds()
  {
  }

  @Given("that I enter the headline {string}")
  public void givenThatIEnterTheHeadline(String headline)
  {
    this.headline = headline;
  }

  @Given("that I enter the description {string}")
  public void givenThatIEnterTheDescription(String description)
  {
    this.description = description;
  }

  @When("I try to fetch the ToDo")
  public void whenITryToFetchTheToDo()
  {
    final var request = new FetchToDoRequest(id);

    factory.fetch().execute(request);
  }


  @When("I try to delete the ToDo")
  public void whenITryToDeleteTheToDo()
  {
    final var request = new DeleteToDoRequest(id);

    factory.delete().execute(request);
  }

  @When("I try to complete the ToDo")
  public void whenITryToCompleteTheToDo()
  {
    final var request = new CompleteToDoRequest(id);

    factory.complete().execute(request);
  }


  @When("I try to reset the ToDo")
  public void whenITryToResetTheToDo()
  {
    final var request = new ResetToDoRequest(id);

    factory.reopen().execute(request);
  }

  @When("I try to update the ToDo")
  public void whenITryToUpdateTheToDo()
  {
    final var request = new UpdateToDoRequest(id, headline, description);

    factory.update().execute(request);
  }

  @Then("fetching the ToDo should have failed with the message: {string}")
  public void thenFetchingTheToDoShouldHaveFailedWithTheMessage(final String expectedMessage)
  {
    fetchResponse.applyTo(new FetchToDoOutputBoundary()
    {
      @Override
      public void handleFailedResponse(final FetchToDoFailedResponse response)
      {
        assertThat(response.reason()).isEqualTo(expectedMessage);
      }

      @Override
      public void handleSuccessfulResponse(final ToDoFetchedResponse response)
      {
        fail("The request should have failed");
      }
    });
  }

  @Then("the fetched Todo should have the following values:")
  public void thenTheFetchedTodoShouldHaveTheFollowingValues(final DataTable data)
  {
    fetchResponse.applyTo(new FetchToDoOutputBoundary()
    {
      @Override
      public void handleFailedResponse(final FetchToDoFailedResponse response)
      {
        fail(response.reason());
      }

      @Override
      public void handleSuccessfulResponse(final ToDoFetchedResponse response)
      {
        final var todo = parseToDoFrom(data.asMaps().stream().findFirst().get());

        final SoftAssertions assertions = new SoftAssertions();

        assertions.assertThat(response.identity())
            .isEqualTo(todo.aggregateRoot().identity().value());
        assertions.assertThat(response.headline())
            .isEqualTo(todo.aggregateRoot().headline().value());
        assertions.assertThat(response.description())
            .isEqualTo(todo.aggregateRoot().description().value());
        assertions.assertThat(response.created()).isEqualTo(todo.aggregateRoot().created().value());
        assertions.assertThat(response.lastModified())
            .isEqualTo(todo.aggregateRoot().lastModified().value());
        assertions.assertThat(response.state()).isEqualTo(todo.aggregateRoot().state().name());

        assertions.assertAll();
      }
    });
  }

  @Then("the todo update should have failed with the message: {string}")
  public void thenTheTodoUpdateShouldHaveFailedWithTheMessage(String expectedMessage)
  {
    updateResponse.applyTo(new UpdateToDoOutputBoundary()
    {
      @Override
      public void handleSuccessfulResponse(ToDoUpdatedResponse response)
      {
        fail("The request should have failed");
      }

      @Override
      public void handleFailedResponse(ToDoUpdateFailedResponse response)
      {
        assertThat(response.reason()).isEqualTo(expectedMessage);
      }
    });
  }

  @Then("the todo update should have been successful")
  public void theTodoUpdateShouldHaveBeenSuccessful()
  {
    updateResponse.applyTo(new UpdateToDoOutputBoundary()
    {
      @Override
      public void handleSuccessfulResponse(final ToDoUpdatedResponse response)
      {
        assertThat(response).isNotNull();
      }

      @Override
      public void handleFailedResponse(final ToDoUpdateFailedResponse response)
      {
        fail(response.reason());
      }
    });
  }


  /**
   * Parses a ToDo from the given row.
   *
   * @param row The row that should be parsed.
   * @return The parsed entity.
   */
  private static ToDoAggregate parseToDoFrom(final Map<String, String> row)
  {
    return ToDoAggregate.builder()
        .identity(row.get("identity"))
        .headline(row.get("headline"))
        .description(row.get("description"))
        .created(LocalDateTime.parse(row.get("created")))
        .lastModified(LocalDateTime.parse(row.get("last modified")))
        .completed(
            Optional.ofNullable(row.getOrDefault("completed", null)).map(LocalDateTime::parse)
                .orElse(null))
        .state(ToDoStateValueObject.valueOf(row.get("state")))
        .build();
  }

  @Then("a {string} should have been published")
  public void thenAnEventEventShouldHaveBeenPublished(final String type)
  {
    final Map<String, ? extends Class<? extends ToDoEvent>> typeMap =
        List.of(ToDoCompletedEvent.class,
                ToDoCreatedEvent.class,
                ToDoDeletedEvent.class,
                ToDoReopenedEvent.class,
                ToDoUpdatedEvent.class)
            .stream()
            .collect(Collectors.toMap(Class::getSimpleName, clazz -> clazz));

    final Class<? extends ToDoEvent> expectedEventType = typeMap.get(type);

    verify(eventPublisher, times(1)).publish(any(expectedEventType));
  }

  @Given("that I do not enter a headline")
  public void givenThatIDoNotEnterAHeadline()
  {
  }

  @Given("that I do not enter a description")
  public void givenThatIDoNotEnterADescription()
  {
  }

  @When("I try to create the todo")
  public void whenITryToCreateTheTodo()
  {
    var request = new CreateToDoRequest(headline, description);

    factory.create().execute(request);
  }

  @Then("creating the todo should have failed with the message {string}")
  public void creatingTheTodoShouldHaveFailedWithTheMessage(final String expectedMessage)
  {
    createResponse.applyTo(new CreateToDoOutputBoundary()
    {
      @Override
      public void handleSuccessfulResponse(ToDoCreatedResponse response)
      {
        fail("The request should have failed");
      }

      @Override
      public void handleFailedResponse(ToDoCreationFailedResponse response)
      {
        assertThat(response.reason()).isEqualTo(expectedMessage);
      }
    });
  }

  @Then("creating the todo should have succeeded")
  public void creatingTheTodoShouldHaveSucceeded()
  {
    createResponse.applyTo(new CreateToDoOutputBoundary()
    {
      @Override
      public void handleSuccessfulResponse(ToDoCreatedResponse response)
      {
        assertThat(response).isNotNull();
      }

      @Override
      public void handleFailedResponse(ToDoCreationFailedResponse response)
      {
        fail(response.reason());
      }
    });
  }

  @Given("that I configure the application to use the {string} todo gateway")
  public void thatIConfigureTheApplicationToUseTheBackendTodoGateway(final String backend)
  {
    this.gateway = new ToDoGatewayFactory(eventPublisher).create(backend);
  }
}
