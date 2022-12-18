package pendenzenliste.vaadin;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import pendenzenliste.ports.out.ToDoUpdateFailedResponse;
import pendenzenliste.ports.out.ToDoUpdatedResponse;

class UpdateToDoPresenterTest
{

  @Test
  public void handleSuccessfulResponse()
  {
    final var view = mock(ToDoView.class);
    final var presenter = new UpdateToDoPresenter(view);

    presenter.handleSuccessfulResponse(new ToDoUpdatedResponse());

    verify(view, times(1)).loadToDos();
    verify(view, times(1)).clearEditor();
  }

  @Test
  public void handleFailedResponse()
  {
    final var view = mock(ToDoView.class);
    final var presenter = new UpdateToDoPresenter(view);

    presenter.handleFailedResponse(new ToDoUpdateFailedResponse("something bad happened"));

    verify(view, times(1)).showGenericErrorMessage("something bad happened");
  }
}