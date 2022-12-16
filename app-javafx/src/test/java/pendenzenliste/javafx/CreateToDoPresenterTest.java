package pendenzenliste.javafx;

import java.time.LocalDateTime;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.ports.out.ToDoCreatedResponse;

class CreateToDoPresenterTest
{

  @Test
  public void successfulResponse()
  {
    final var listViewModel = new ToDoListViewModel();
    final var editViewModel = new EditToDoViewModel();
    final var initialTimestamp = LocalDateTime.now().minusMinutes(5);

    listViewModel.listUpdated.set(initialTimestamp);
    editViewModel.clearedTrigger.set(initialTimestamp);

    final var presenter = new CreateToDoPresenter(listViewModel, editViewModel);

    presenter.handleSuccessfulResponse(new ToDoCreatedResponse("some-new-identity"));

    final var assertions = new SoftAssertions();

    assertions.assertThat(listViewModel.listUpdated.get()).isAfter(initialTimestamp);
    assertions.assertThat(editViewModel.clearedTrigger.get()).isAfter(initialTimestamp);

    assertions.assertAll();
  }
}