package pendenzenliste.cli;

import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoFetchedResponse;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;

class FetchToDoPresenterTest {

    @Test
    void handleFailedResponse() {
        final var result = mock(CommandLineResult.class);

        final var presenter = new FetchToDoPresenter(result);

        presenter.handleFailedResponse(new FetchToDoFailedResponse("Something bad happened"));

        verify(result, times(1)).write("Something bad happened");
        verify(result, times(1)).exitGeneralFailure();
    }

    @Test
    void handleSuccessfulResponse() {
        final var result = mock(CommandLineResult.class);

        final var presenter = new FetchToDoPresenter(result);

        presenter.handleSuccessfulResponse(new ToDoFetchedResponse("42",
                "Test",
                "Description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "COMPLETED",
                Collections.emptyList()));

        verify(result, times(1)).exitNoError();
    }
}