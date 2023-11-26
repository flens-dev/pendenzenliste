package pendenzenliste.dropwizard;

import io.dropwizard.jersey.errors.ErrorMessage;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.out.ToDoCreatedResponse;
import pendenzenliste.todos.boundary.out.ToDoCreationFailedResponse;

class JsonCreateToDoPresenterTest {

    @Test
    public void handleSuccessfulResponse() {
        final HttpResponseViewModel viewModel = new HttpResponseViewModel();

        JsonCreateToDoPresenter presenter = new JsonCreateToDoPresenter(viewModel);

        presenter.handleSuccessfulResponse(new ToDoCreatedResponse("42"));

        final var assertions = new SoftAssertions();

        assertions.assertThat(viewModel.response.getStatus())
                .isEqualTo(201);
        assertions.assertThat(viewModel.response.getEntity())
                .isEqualTo(new JsonCreatedToDoData("42"));

        assertions.assertAll();
    }

    @Test
    public void handleFailedResponse() {
        final HttpResponseViewModel viewModel = new HttpResponseViewModel();

        JsonCreateToDoPresenter presenter = new JsonCreateToDoPresenter(viewModel);

        presenter.handleFailedResponse(new ToDoCreationFailedResponse("Something bad happened"));

        final var assertions = new SoftAssertions();

        assertions.assertThat(viewModel.response.getStatus())
                .isEqualTo(400);
        assertions.assertThat(viewModel.response.getEntity())
                .isEqualTo(new ErrorMessage("Failed to create the todo"));

        assertions.assertAll();
    }
}