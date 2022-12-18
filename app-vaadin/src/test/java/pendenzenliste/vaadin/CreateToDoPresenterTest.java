package pendenzenliste.vaadin;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import pendenzenliste.boundary.out.ToDoCreatedResponse;
import pendenzenliste.boundary.out.ToDoCreationFailedResponse;

class CreateToDoPresenterTest
{

  @Test
  public void handleSuccessfulResponse()
  {
    final var view = mock(ToDoView.class);
    final var presenter = new CreateToDoPresenter(view);

    presenter.handleSuccessfulResponse(new ToDoCreatedResponse("test-identity"));

    verify(view, times(1)).clearEditor();
    verify(view, times(1)).loadToDos();
  }

  @Test
  public void handleFailedResponse()
  {
    final var view = mock(ToDoView.class);
    final var presenter = new CreateToDoPresenter(view);

    presenter.handleFailedResponse(new ToDoCreationFailedResponse("Something bad happened"));

    verify(view, times(1)).showGenericErrorMessage("Something bad happened");
  }
}