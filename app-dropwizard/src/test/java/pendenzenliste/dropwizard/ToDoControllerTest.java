package pendenzenliste.dropwizard;

import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.in.*;

import static org.mockito.Mockito.*;

class ToDoControllerTest {
    @Test
    public void list() {
        final var factory = mock(ToDoInputBoundaryFactory.class);
        final var inputBoundary = mock(FetchToDoListInputBoundary.class);

        when(factory.list()).thenReturn(inputBoundary);

        final var controller = new ToDoController(factory);

        controller.list();

        verify(inputBoundary, times(1))
                .execute(new FetchTodoListRequest());
    }

    @Test
    public void fetch() {
        final var factory = mock(ToDoInputBoundaryFactory.class);
        final var inputBoundary = mock(FetchToDoInputBoundary.class);

        when(factory.fetch()).thenReturn(inputBoundary);

        final var controller = new ToDoController(factory);

        controller.fetch("42");

        verify(inputBoundary, times(1))
                .execute(new FetchToDoRequest("42"));
    }

    @Test
    public void create() {
        final var factory = mock(ToDoInputBoundaryFactory.class);
        final var inputBoundary = mock(CreateToDoInputBoundary.class);

        when(factory.create()).thenReturn(inputBoundary);

        final var controller = new ToDoController(factory);

        doAnswer(invocation -> {
            return null;
        }).when(inputBoundary).execute(new CreateToDoRequest("Headline", "Description"));

        controller.create(new JsonCreateToDoData("Headline", "Description"));

        verify(inputBoundary, times(1))
                .execute(new CreateToDoRequest("Headline", "Description"));
    }
}