package pendenzenliste.javafx;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import pendenzenliste.ports.out.ToDoCreatedResponse;
import pendenzenliste.ports.out.ToDoCreationFailedResponse;

class CreateToDoPresenterTest
{

  @Test
  public void handleSuccessfulResponse()
  {
    var viewModel = mock(EditToDoViewModel.class);

    var presenter = new CreateToDoPresenter(viewModel);

    presenter.handleSuccessfulResponse(new ToDoCreatedResponse("some-uid"));

    verify(viewModel, times(1)).publishEvent(any(ClearEditorRequestedEvent.class));
    verify(viewModel, times(1)).publishEvent(any(ListUpdateRequiredEvent.class));
  }

  @Test
  public void handleFailedResponse()
  {
    var viewModel = new EditToDoViewModel();

    var presenter = new CreateToDoPresenter(viewModel);

    presenter.handleFailedResponse(new ToDoCreationFailedResponse("Some error message"));

    assertThat(viewModel.errorMessage.get()).isEqualTo("Some error message");
  }
}