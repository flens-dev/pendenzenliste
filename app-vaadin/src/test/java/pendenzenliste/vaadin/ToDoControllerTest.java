package pendenzenliste.vaadin;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import pendenzenliste.ports.in.CompleteToDoInputBoundary;
import pendenzenliste.ports.in.CompleteToDoRequest;
import pendenzenliste.ports.in.CreateToDoInputBoundary;
import pendenzenliste.ports.in.CreateToDoRequest;
import pendenzenliste.ports.in.DeleteToDoInputBoundary;
import pendenzenliste.ports.in.DeleteToDoRequest;
import pendenzenliste.ports.in.FetchToDoInputBoundary;
import pendenzenliste.ports.in.FetchToDoListInputBoundary;
import pendenzenliste.ports.in.FetchToDoRequest;
import pendenzenliste.ports.in.FetchTodoListRequest;
import pendenzenliste.ports.in.ResetToDoInputBoundary;
import pendenzenliste.ports.in.ResetToDoRequest;
import pendenzenliste.ports.in.ToDoInputBoundaryFactory;
import pendenzenliste.ports.in.UpdateToDoInputBoundary;
import pendenzenliste.ports.in.UpdateToDoRequest;

class ToDoControllerTest
{

  @Test
  public void loadTodos()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(FetchToDoListInputBoundary.class);
    when(inputBoundaryFactory.list()).thenReturn(inputBoundary);

    var controller = new ToDoController(inputBoundaryFactory);

    controller.loadTodos();

    verify(inputBoundary, times(1)).execute(new FetchTodoListRequest());
  }

  @Test
  public void loadForEdit()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(FetchToDoInputBoundary.class);
    when(inputBoundaryFactory.fetch()).thenReturn(inputBoundary);

    var controller = new ToDoController(inputBoundaryFactory);

    controller.loadForEdit("test-identity");

    verify(inputBoundary, times(1))
        .execute(new FetchToDoRequest("test-identity"));
  }

  @Test
  public void update()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(UpdateToDoInputBoundary.class);
    when(inputBoundaryFactory.update()).thenReturn(inputBoundary);

    var controller = new ToDoController(inputBoundaryFactory);

    controller.update("test-identity", "test-headline", "test-description");

    verify(inputBoundary, times(1))
        .execute(new UpdateToDoRequest(
            "test-identity", "test-headline", "test-description"));
  }

  @Test
  public void create()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(CreateToDoInputBoundary.class);
    when(inputBoundaryFactory.create()).thenReturn(inputBoundary);

    var controller = new ToDoController(inputBoundaryFactory);

    controller.create("test-headline", "test-description");

    verify(inputBoundary, times(1))
        .execute(new CreateToDoRequest("test-headline", "test-description"));
  }

  @Test
  public void delete()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(DeleteToDoInputBoundary.class);
    when(inputBoundaryFactory.delete()).thenReturn(inputBoundary);

    var controller = new ToDoController(inputBoundaryFactory);

    controller.delete("test-identity");

    verify(inputBoundary, times(1))
        .execute(new DeleteToDoRequest("test-identity"));
  }

  @Test
  public void complete()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(CompleteToDoInputBoundary.class);
    when(inputBoundaryFactory.complete()).thenReturn(inputBoundary);

    var controller = new ToDoController(inputBoundaryFactory);

    controller.complete("test-identity");

    verify(inputBoundary, times(1))
        .execute(new CompleteToDoRequest("test-identity"));
  }

  @Test
  public void reset()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(ResetToDoInputBoundary.class);
    when(inputBoundaryFactory.reset()).thenReturn(inputBoundary);

    var controller = new ToDoController(inputBoundaryFactory);

    controller.reset("test-identity");

    verify(inputBoundary, times(1))
        .execute(new ResetToDoRequest("test-identity"));
  }
}