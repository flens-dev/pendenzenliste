package pendenzenliste;

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
import static org.mockito.Mockito.when;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import pendenzenliste.boundary.in.CompleteToDoRequest;
import pendenzenliste.boundary.in.CreateToDoRequest;
import pendenzenliste.boundary.in.DeleteToDoRequest;
import pendenzenliste.boundary.in.FetchToDoRequest;
import pendenzenliste.boundary.in.ResetToDoRequest;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.boundary.in.UpdateToDoRequest;
import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.CreateToDoResponse;
import pendenzenliste.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoResponse;
import pendenzenliste.boundary.out.ToDoCreatedResponse;
import pendenzenliste.boundary.out.ToDoCreationFailedResponse;
import pendenzenliste.boundary.out.ToDoFetchedResponse;
import pendenzenliste.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.boundary.out.UpdateToDoResponse;
import pendenzenliste.domain.todos.CompletedTimestampValueObject;
import pendenzenliste.domain.todos.CreatedTimestampValueObject;
import pendenzenliste.domain.todos.DescriptionValueObject;
import pendenzenliste.domain.todos.HeadlineValueObject;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.LastModifiedTimestampValueObject;
import pendenzenliste.domain.todos.ToDoCompletedEvent;
import pendenzenliste.domain.todos.ToDoCreatedEvent;
import pendenzenliste.domain.todos.ToDoDeletedEvent;
import pendenzenliste.domain.todos.ToDoEntity;
import pendenzenliste.domain.todos.ToDoEvent;
import pendenzenliste.domain.todos.ToDoEventPublisher;
import pendenzenliste.domain.todos.ToDoEventSubscriptionTopic;
import pendenzenliste.domain.todos.ToDoResetEvent;
import pendenzenliste.domain.todos.ToDoStateValueObject;
import pendenzenliste.domain.todos.ToDoUpdatedEvent;
import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.usecases.ToDoUseCaseFactory;

/**
 * The steps used to execute the ToDo acceptance tests.
 */
public class ToDoSteps
{
  private final ToDoGateway gateway = mock(ToDoGateway.class);

  private final ToDoOutputBoundaryFactory outputBoundaryFactory =
      new ToDoOutputBoundaryFactory()
      {
        @Override
        public CreateToDoOutputBoundary create()
        {
          return mock(CreateToDoOutputBoundary.class);
        }

        @Override
        public FetchToDoListOutputBoundary list()
        {
          return mock(FetchToDoListOutputBoundary.class);
        }

        @Override
        public FetchToDoOutputBoundary fetch()
        {
          return mock(FetchToDoOutputBoundary.class);
        }

        @Override
        public UpdateToDoOutputBoundary update()
        {
          return mock(UpdateToDoOutputBoundary.class);
        }
      };

  private final ToDoEventPublisher eventPublisher = mock(ToDoEventPublisher.class);

  private final ToDoInputBoundaryFactory factory = new ToDoUseCaseFactory(() -> gateway,
      outputBoundaryFactory, eventPublisher,
      ToDoEventSubscriptionTopic.defaultSubscriptionTopic());

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
    when(gateway.findById(new IdentityValueObject(id))).thenReturn(Optional.empty());
  }


  @Given("that the following ToDo exists:")
  public void givenThatTheFollowingToDoExists(DataTable data)
  {
    for (final Map<String, String> row : data.asMaps())
    {
      var identity = new IdentityValueObject(row.get("identity"));

      when(gateway.findById(identity)).thenReturn(Optional.of(parseToDoFrom(row)));
    }
  }

  @Given("that deleting the ToDo fails")
  public void givenThatDeletingTheToDoFails()
  {
    when(gateway.delete(new IdentityValueObject(id))).thenReturn(false);
  }

  @Given("that deleting the ToDo succeeds")
  public void givenThatDeletingTheToDoSucceeds()
  {
    when(gateway.delete(new IdentityValueObject(id))).thenReturn(true);
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

    fetchResponse = factory.fetch().executeRequest(request);
  }


  @When("I try to delete the ToDo")
  public void whenITryToDeleteTheToDo()
  {
    final var request = new DeleteToDoRequest(id);

    updateResponse = factory.delete().executeRequest(request);
  }

  @When("I try to complete the ToDo")
  public void whenITryToCompleteTheToDo()
  {
    final var request = new CompleteToDoRequest(id);

    updateResponse = factory.complete().executeRequest(request);
  }


  @When("I try to reset the ToDo")
  public void whenITryToResetTheToDo()
  {
    final var request = new ResetToDoRequest(id);

    updateResponse = factory.reset().executeRequest(request);
  }

  @When("I try to update the ToDo")
  public void whenITryToUpdateTheToDo()
  {
    final var request = new UpdateToDoRequest(id, headline, description);

    updateResponse = factory.update().executeRequest(request);
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

        assertions.assertThat(response.identity()).isEqualTo(todo.identity().value());
        assertions.assertThat(response.headline()).isEqualTo(todo.headline().value());
        assertions.assertThat(response.description()).isEqualTo(todo.description().value());
        assertions.assertThat(response.created()).isEqualTo(todo.created().value());
        assertions.assertThat(response.lastModified()).isEqualTo(todo.lastModified().value());
        assertions.assertThat(response.state()).isEqualTo(todo.state().name());

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
  private static ToDoEntity parseToDoFrom(final Map<String, String> row)
  {
    return new ToDoEntity(new IdentityValueObject(row.get("identity")),
        new HeadlineValueObject(row.get("headline")),
        new DescriptionValueObject(row.get("description")),
        new CreatedTimestampValueObject(LocalDateTime.parse(row.get("created"))),
        new LastModifiedTimestampValueObject(LocalDateTime.parse(row.get("last modified"))),
        Optional.ofNullable(row.getOrDefault("completed", null)).map(LocalDateTime::parse)
            .map(CompletedTimestampValueObject::new).orElse(null),
        ToDoStateValueObject.valueOf(row.get("state")));
  }

  @Then("a {string} should have been published")
  public void thenAnEventEventShouldHaveBeenPublished(final String type)
  {
    final Map<String, ? extends Class<? extends ToDoEvent>> typeMap =
        List.of(ToDoCompletedEvent.class,
                ToDoCreatedEvent.class,
                ToDoDeletedEvent.class,
                ToDoResetEvent.class,
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

    createResponse = factory.create().executeRequest(request);
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
}
