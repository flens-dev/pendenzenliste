package pendenzenliste.vaadin;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.boundary.out.ToDoFetchedResponse;

class EditToDoPresenterTest
{

  @Test
  public void handleFailedResponse()
  {
    final var viewModel = new ToDoListViewModel();
    final var presenter = new EditToDoPresenter(viewModel);

    presenter.handleFailedResponse(new FetchToDoFailedResponse("Something bad happened"));

    assertThat(viewModel.errorMessage.get()).isEqualTo("Something bad happened");
  }

  @Test
  public void handleSuccessfulResponse()
  {
    final var viewModel = new ToDoListViewModel();
    final var presenter = new EditToDoPresenter(viewModel);

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

    final var assertions = new SoftAssertions();

    assertions.assertThat(viewModel.identity.get()).isEqualTo("test-identity");
    assertions.assertThat(viewModel.headline.get()).isEqualTo("test-headline");
    assertions.assertThat(viewModel.description.get()).isEqualTo("test-description");

    assertions.assertAll();
  }
}