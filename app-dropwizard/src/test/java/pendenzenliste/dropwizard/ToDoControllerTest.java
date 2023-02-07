package pendenzenliste.dropwizard;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.in.FetchToDoInputBoundary;
import pendenzenliste.todos.boundary.in.FetchToDoListInputBoundary;
import pendenzenliste.todos.boundary.in.FetchToDoRequest;
import pendenzenliste.todos.boundary.in.FetchTodoListRequest;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactory;

class ToDoControllerTest
{
  @Test
  public void list()
  {
    final var factory = mock(ToDoInputBoundaryFactory.class);
    final var inputBoundary = mock(FetchToDoListInputBoundary.class);

    when(factory.list()).thenReturn(inputBoundary);

    final var controller = new ToDoController(factory);

    controller.list();

    verify(inputBoundary, times(1))
        .execute(new FetchTodoListRequest());
  }

  @Test
  public void fetch()
  {
    final var factory = mock(ToDoInputBoundaryFactory.class);
    final var inputBoundary = mock(FetchToDoInputBoundary.class);

    when(factory.fetch()).thenReturn(inputBoundary);

    final var controller = new ToDoController(factory);

    controller.fetch("42");

    verify(inputBoundary, times(1))
        .execute(new FetchToDoRequest("42"));
  }
}