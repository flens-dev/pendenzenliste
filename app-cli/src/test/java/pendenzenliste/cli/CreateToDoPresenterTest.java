package pendenzenliste.cli;

import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.out.ToDoCreatedResponse;
import pendenzenliste.todos.boundary.out.ToDoCreationFailedResponse;

import static org.mockito.Mockito.*;

class CreateToDoPresenterTest {

    @Test
    public void handleSuccessfulResponse() {
        final var result = mock(CommandLineResult.class);

        final var presenter = new CreateToDoPresenter(result);

        presenter.handleSuccessfulResponse(new ToDoCreatedResponse("42"));

        verify(result, times(1)).write("42");
        verify(result, times(1)).exitNoError();
    }

    @Test
    public void handleFailedResponse() {
        final var result = mock(CommandLineResult.class);

        final var presenter = new CreateToDoPresenter(result);

        presenter.handleFailedResponse(new ToDoCreationFailedResponse("Something bad happened"));

        verify(result, times(1)).write("Something bad happened");
        verify(result, times(1)).exitGeneralFailure();
    }
}