package pendenzenliste.cli;

import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.out.FetchToDoListFailedResponse;
import pendenzenliste.todos.boundary.out.FetchedToDoListResponse;
import pendenzenliste.todos.boundary.out.ToDoListResponseModel;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class FetchToDoListPresenterTest {

    @Test
    void handleFailedResponse() {
        final var result = mock(CommandLineResult.class);

        final var presenter = new FetchToDoListPresenter(result);

        presenter.handleFailedResponse(new FetchToDoListFailedResponse("Something bad happened"));

        verify(result, times(1)).write("Something bad happened");
        verify(result, times(1)).exitGeneralFailure();
    }

    @Test
    void handleSuccessfulResponse() {
        final var result = mock(CommandLineResult.class);

        final var presenter = new FetchToDoListPresenter(result);

        presenter.handleSuccessfulResponse(new FetchedToDoListResponse(
                List.of(new ToDoListResponseModel(
                        "42",
                        "Test",
                        "Lorem ipsum",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        "COMPLETED",
                        Collections.emptyList()
                ))
        ));

        verify(result, times(1)).exitNoError();
    }
}