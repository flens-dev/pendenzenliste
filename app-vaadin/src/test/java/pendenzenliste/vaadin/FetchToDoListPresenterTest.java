package pendenzenliste.vaadin;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.out.FetchToDoListFailedResponse;
import pendenzenliste.todos.boundary.out.FetchedToDoListResponse;
import pendenzenliste.todos.boundary.out.ToDoListResponseModel;

class FetchToDoListPresenterTest
{

  @Test
  public void handleFailedResponse()
  {
    final var viewModel = new ToDoListViewModel();
    final var presenter = new FetchToDoListPresenter(viewModel);

    presenter.handleFailedResponse(
        new FetchToDoListFailedResponse("Something bad happened"));

    assertThat(viewModel.errorMessage.get()).isEqualTo("Something bad happened");
  }

  @Test
  public void handleSuccessfulResponse()
  {
    final var viewModel = new ToDoListViewModel();
    final var presenter = new FetchToDoListPresenter(viewModel);

    final var createdTimestamp = LocalDateTime.now().minusMinutes(5);
    final var lastModifiedTimestamp = LocalDateTime.now().minusMinutes(3);
    final var completedTimestamp = LocalDateTime.now().minusMinutes(1);

    presenter.handleSuccessfulResponse(new FetchedToDoListResponse(
        List.of(new ToDoListResponseModel(
            "test-identity",
            "test-headline",
            "test-description",
            createdTimestamp,
            lastModifiedTimestamp,
            completedTimestamp,
            "DONE",
            List.of("RESET")
        ))
    ));

    assertThat(viewModel.todos.get()).hasSize(1);
  }
}