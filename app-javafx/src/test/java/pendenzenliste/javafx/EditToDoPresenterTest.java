package pendenzenliste.javafx;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.ports.out.FetchToDoFailedResponse;
import pendenzenliste.ports.out.ToDoFetchedResponse;

class EditToDoPresenterTest
{

  @Test
  public void handleSuccessfulResponse()
  {
    var viewModel = new EditToDoViewModel();

    var presenter = new EditToDoPresenter(viewModel);

    var expectedCreatedTimestamp = LocalDateTime.now().minusMinutes(15);
    var expectedLastModifiedTimestamp = LocalDateTime.now();
    var expectedDeletedTimestamp = LocalDateTime.now();

    viewModel.errorMessage.set("Some error");

    presenter.handleSuccessfulResponse(new ToDoFetchedResponse(
        "some-identity",
        "headline",
        "description",
        expectedCreatedTimestamp,
        expectedLastModifiedTimestamp,
        expectedDeletedTimestamp,
        "DONE",
        List.of("RESET")
    ));

    var assertions = new SoftAssertions();

    assertions.assertThat(viewModel.identity.get()).isEqualTo("some-identity");
    assertions.assertThat(viewModel.headline.get()).isEqualTo("headline");
    assertions.assertThat(viewModel.description.get()).isEqualTo("description");
    assertions.assertThat(viewModel.errorMessage.get()).isEmpty();
    assertions.assertThat(viewModel.resetButtonVisible.get()).isTrue();
    assertions.assertThat(viewModel.completeButtonVisible.get()).isFalse();
    assertions.assertThat(viewModel.deleteButtonVisible.get()).isFalse();

    assertions.assertAll();
  }

  @Test
  public void handleFailedResponse()
  {
    var viewModel = new EditToDoViewModel();

    var presenter = new EditToDoPresenter(viewModel);

    presenter.handleFailedResponse(new FetchToDoFailedResponse("Some error message"));

    assertThat(viewModel.errorMessage.get()).isEqualTo("Some error message");
  }
}