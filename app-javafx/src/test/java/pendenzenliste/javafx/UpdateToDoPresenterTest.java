package pendenzenliste.javafx;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoUpdatedResponse;

class UpdateToDoPresenterTest
{

  @Test
  public void handleSuccessfulResponse()
  {
    var viewModel = mock(EditToDoViewModel.class);
    var presenter = new UpdateToDoPresenter(viewModel);

    presenter.handleSuccessfulResponse(new ToDoUpdatedResponse());

    verify(viewModel, times(1)).publishEvent(any(ClearEditorRequestedEvent.class));
  }

  @Test
  public void handleFailedResponse()
  {
    var viewModel = new EditToDoViewModel();
    var presenter = new UpdateToDoPresenter(viewModel);

    presenter.handleFailedResponse(new ToDoUpdateFailedResponse("Some error occurred"));

    assertThat(viewModel.errorMessage.get()).isEqualTo("Some error occurred");
  }
}