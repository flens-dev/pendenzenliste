package pendenzenliste.vaadin;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.boundary.out.ToDoUpdatedResponse;

class UpdateToDoPresenterTest
{

  @Test
  public void handleSuccessfulResponse()
  {
    final var viewModel = new ToDoListViewModel();
    final var presenter = new UpdateToDoPresenter(viewModel);

    viewModel.identity.set("test-identity");
    viewModel.headline.set("test-headline");
    viewModel.description.set("test-description");

    presenter.handleSuccessfulResponse(new ToDoUpdatedResponse());

    final var assertions = new SoftAssertions();

    assertions.assertThat(viewModel.identity.get()).isNull();
    assertions.assertThat(viewModel.headline.get()).isEmpty();
    assertions.assertThat(viewModel.description.get()).isEmpty();

    assertions.assertAll();
  }

  @Test
  public void handleFailedResponse()
  {
    final var viewModel = new ToDoListViewModel();
    final var presenter = new UpdateToDoPresenter(viewModel);

    presenter.handleFailedResponse(new ToDoUpdateFailedResponse("something bad happened"));

    assertThat(viewModel.errorMessage.get()).isEqualTo("something bad happened");
  }
}