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
import pendenzenliste.boundary.in.DeleteToDoRequest;
import pendenzenliste.boundary.in.FetchToDoRequest;
import pendenzenliste.boundary.in.ResetToDoRequest;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.boundary.in.UpdateToDoRequest;
import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoResponse;
import pendenzenliste.boundary.out.ToDoFetchedResponse;
import pendenzenliste.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.boundary.out.UpdateToDoResponse;
import pendenzenliste.domain.CompletedTimestampValueObject;
import pendenzenliste.domain.CreatedTimestampValueObject;
import pendenzenliste.domain.DescriptionValueObject;
import pendenzenliste.domain.HeadlineValueObject;
import pendenzenliste.domain.IdentityValueObject;
import pendenzenliste.domain.LastModifiedTimestampValueObject;
import pendenzenliste.domain.ToDoCompletedEvent;
import pendenzenliste.domain.ToDoCreatedEvent;
import pendenzenliste.domain.ToDoDeletedEvent;
import pendenzenliste.domain.ToDoEntity;
import pendenzenliste.domain.ToDoEvent;
import pendenzenliste.domain.ToDoEventPublisher;
import pendenzenliste.domain.ToDoEventSubscriptionTopic;
import pendenzenliste.domain.ToDoResetEvent;
import pendenzenliste.domain.ToDoStateValueObject;
import pendenzenliste.domain.ToDoUpdatedEvent;
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
}
