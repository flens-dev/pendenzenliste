package pendenzenliste.dropwizard;

import java.util.Collections;
import java.util.List;

import io.dropwizard.jersey.errors.ErrorMessage;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.out.FetchToDoListFailedResponse;
import pendenzenliste.todos.boundary.out.FetchedToDoListResponse;
import pendenzenliste.todos.boundary.out.ToDoListResponseModel;

class JsonFetchToDoListPresenterTest
{

  @Test
  public void handleFailedResponse()
  {
    final var viewModel = new HttpResponseViewModel();
    final var presenter = new JsonFetchToDoListPresenter(viewModel);

    presenter.handleFailedResponse(new FetchToDoListFailedResponse("Something bad happened"));

    final var assertions = new SoftAssertions();

    assertions.assertThat(viewModel.response.getStatus()).isEqualTo(400);
    assertions.assertThat(viewModel.response.getEntity())
        .isEqualTo(new ErrorMessage("Something bad happened"));
    assertions.assertAll();
  }

  @Test
  public void handleSuccessfulResponse()
  {

    final var viewModel = new HttpResponseViewModel();
    final var presenter = new JsonFetchToDoListPresenter(viewModel);

    presenter.handleSuccessfulResponse(new FetchedToDoListResponse(List.of(
        new ToDoListResponseModel(
            "42",
            "Headline",
            "Description",
            null,
            null,
            null,
            "OPEN",
            Collections.emptyList()))));

    final var assertions = new SoftAssertions();

    assertions.assertThat(viewModel.response.getStatus()).isEqualTo(200);
    assertions.assertThat(viewModel.response.getEntity()).isEqualTo(List.of(
        new JsonToDoViewModel(
            "42",
            "Headline",
            "Description",
            null,
            null,
            null,
            "OPEN",
            Collections.emptyList())));
    assertions.assertAll();
  }
}