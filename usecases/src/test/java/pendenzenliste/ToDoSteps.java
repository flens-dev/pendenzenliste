package pendenzenliste;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import pendenzenliste.domain.CompletedTimestampValueObject;
import pendenzenliste.domain.CreatedTimestampValueObject;
import pendenzenliste.domain.DescriptionValueObject;
import pendenzenliste.domain.HeadlineValueObject;
import pendenzenliste.domain.LastModifiedTimestampValueObject;
import pendenzenliste.domain.ToDoEntity;
import pendenzenliste.domain.ToDoIdentityValueObject;
import pendenzenliste.domain.ToDoState;
import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.ports.in.CompleteToDoRequest;
import pendenzenliste.ports.in.DeleteToDoRequest;
import pendenzenliste.ports.in.FetchToDoRequest;
import pendenzenliste.ports.in.ToDoInputBoundaryFactory;
import pendenzenliste.ports.out.FetchToDoFailedResponse;
import pendenzenliste.ports.out.FetchToDoOutputBoundary;
import pendenzenliste.ports.out.FetchToDoResponse;
import pendenzenliste.ports.out.ToDoFetchedResponse;
import pendenzenliste.ports.out.ToDoUpdateFailedResponse;
import pendenzenliste.ports.out.ToDoUpdatedResponse;
import pendenzenliste.ports.out.UpdateToDoOutputBoundary;
import pendenzenliste.ports.out.UpdateToDoResponse;
import pendenzenliste.usecases.ToDoUseCaseFactory;

/**
 * The steps used to execute the ToDo acceptance tests.
 */
public class ToDoSteps
{
  private final ToDoGateway gateway = mock(ToDoGateway.class);

  private final ToDoInputBoundaryFactory factory = new ToDoUseCaseFactory(() -> gateway);

  private String id;

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
    when(gateway.findById(new ToDoIdentityValueObject(id))).thenReturn(Optional.empty());
  }


  @Given("that the following ToDo exists:")
  public void givenThatTheFollowingToDoExists(DataTable data)
  {
    for (final Map<String, String> row : data.asMaps())
    {
      var identity = new ToDoIdentityValueObject(row.get("identity"));

      when(gateway.findById(identity)).thenReturn(Optional.of(parseToDoFrom(row)));
    }
  }

  @Given("that deleting the ToDo fails")
  public void givenThatDeletingTheToDoFails()
  {
    when(gateway.delete(new ToDoIdentityValueObject(id))).thenReturn(false);
  }

  @Given("that deleting the ToDo succeeds")
  public void givenThatDeletingTheToDoSucceeds()
  {
    when(gateway.delete(new ToDoIdentityValueObject(id))).thenReturn(true);
  }

  @When("I try to fetch the ToDo")
  public void whenITryToFetchTheToDo()
  {
    final var request = new FetchToDoRequest(id);

    fetchResponse = factory.fetch().execute(request);
  }


  @When("I try to delete the ToDo")
  public void whenITryToDeleteTheToDo()
  {
    final var request = new DeleteToDoRequest(id);

    updateResponse = factory.delete().execute(request);
  }

  @When("I try to complete the ToDo")
  public void whenITryToCompleteTheToDo()
  {
    final var request = new CompleteToDoRequest(id);

    updateResponse = factory.complete().execute(request);
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
    return new ToDoEntity(new ToDoIdentityValueObject(row.get("identity")),
        new HeadlineValueObject(row.get("headline")),
        new DescriptionValueObject(row.get("description")),
        new CreatedTimestampValueObject(LocalDateTime.parse(row.get("created"))),
        new LastModifiedTimestampValueObject(LocalDateTime.parse(row.get("last modified"))),
        Optional.ofNullable(row.getOrDefault("completed", null))
            .map(LocalDateTime::parse)
            .map(CompletedTimestampValueObject::new)
            .orElse(null),
        ToDoState.valueOf(row.get("state")));
  }
}
