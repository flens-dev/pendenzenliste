package pendenzenliste.cli;

import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoUpdatedResponse;

import static org.mockito.Mockito.*;

class UpdateToDoPresenterTest {

    @Test
    void handleSuccessfulResponse() {
        final var result = mock(CommandLineResult.class);

        final var presenter = new UpdateToDoPresenter(result);

        presenter.handleSuccessfulResponse(new ToDoUpdatedResponse());

        verify(result, times(1)).exitNoError();
    }

    @Test
    void handleFailedResponse() {
        final var result = mock(CommandLineResult.class);

        final var presenter = new UpdateToDoPresenter(result);

        presenter.handleFailedResponse(new ToDoUpdateFailedResponse("Something bad happened"));

        verify(result, times(1)).write("Something bad happened");
        verify(result, times(1)).exitGeneralFailure();
    }
}