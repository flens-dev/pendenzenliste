package pendenzenliste.dropwizard;

import java.util.Collections;

import io.dropwizard.jersey.errors.ErrorMessage;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoFetchedResponse;

class JsonFetchToDoPresenterTest
{

  @Test
  public void handleFailedResponse()
  {
    final var viewModel = new HttpResponseViewModel();
    final var presenter = new JsonFetchToDoPresenter(viewModel);

    presenter.handleFailedResponse(new FetchToDoFailedResponse("Something bad happened"));

    final var assertions = new SoftAssertions();

    assertions.assertThat(viewModel.response.getStatus()).isEqualTo(404);
    assertions.assertThat(viewModel.response.getEntity())
        .isEqualTo(new ErrorMessage("Something bad happened"));

    assertions.assertAll();
  }

  @Test
  public void handleSuccessfulResponse()
  {
    final var viewModel = new HttpResponseViewModel();
    final var presenter = new JsonFetchToDoPresenter(viewModel);

    presenter.handleSuccessfulResponse(
        new ToDoFetchedResponse("42",
            "Headline",
            "Description",
            null,
            null,
            null,
            "OPEN",
            Collections.emptyList()));

    final var assertions = new SoftAssertions();

    assertions.assertThat(viewModel.response.getStatus()).isEqualTo(200);
    assertions.assertThat(viewModel.response.getEntity())
        .isEqualTo(new JsonToDoViewModel(
            "42",
            "Headline",
            "Description",
            null,
            null,
            null,
            "OPEN",
            Collections.emptyList()
        ));

    assertions.assertAll();
  }
}