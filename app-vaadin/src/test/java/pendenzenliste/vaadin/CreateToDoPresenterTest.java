package pendenzenliste.vaadin;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.boundary.out.ToDoCreatedResponse;
import pendenzenliste.boundary.out.ToDoCreationFailedResponse;

class CreateToDoPresenterTest
{

  @Test
  public void handleSuccessfulResponse()
  {
    final var viewModel = new ToDoListViewModel();
    final var presenter = new CreateToDoPresenter(viewModel);

    viewModel.identity.set("test-identity");
    viewModel.headline.set("test-headline");
    viewModel.description.set("test-description");

    presenter.handleSuccessfulResponse(new ToDoCreatedResponse("test-identity"));

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
    final var presenter = new CreateToDoPresenter(viewModel);

    presenter.handleFailedResponse(new ToDoCreationFailedResponse("Something bad happened"));

    assertThat(viewModel.errorMessage.get()).isEqualTo("Something bad happened");
  }
}