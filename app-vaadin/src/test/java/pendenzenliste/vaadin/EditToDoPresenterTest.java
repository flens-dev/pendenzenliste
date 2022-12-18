package pendenzenliste.vaadin;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import pendenzenliste.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.boundary.out.ToDoFetchedResponse;

class EditToDoPresenterTest
{

  @Test
  public void handleFailedResponse()
  {
    final var view = mock(ToDoView.class);
    final var presenter = new EditToDoPresenter(view);

    presenter.handleFailedResponse(new FetchToDoFailedResponse("Something bad happened"));

    verify(view, times(1)).showGenericErrorMessage("Something bad happened");
  }

  @Test
  public void handleSuccessfulResponse()
  {
    final var view = mock(ToDoView.class);
    final var presenter = new EditToDoPresenter(view);

    presenter.handleSuccessfulResponse(new ToDoFetchedResponse(
        "test-identity",
        "test-headline",
        "test-description",
        LocalDateTime.now(),
        LocalDateTime.now(),
        LocalDateTime.now(),
        "DONE",
        List.of("UPDATE")
    ));

    verify(view, times(1)).setSelectedToDo(new ToDoViewModel(
        "test-identity",
        "test-headline",
        "test-description"
    ));
  }
}