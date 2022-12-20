package pendenzenliste.vaadin;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import pendenzenliste.boundary.in.CompleteToDoInputBoundary;
import pendenzenliste.boundary.in.CompleteToDoRequest;
import pendenzenliste.boundary.in.CreateToDoInputBoundary;
import pendenzenliste.boundary.in.CreateToDoRequest;
import pendenzenliste.boundary.in.DeleteToDoInputBoundary;
import pendenzenliste.boundary.in.DeleteToDoRequest;
import pendenzenliste.boundary.in.FetchToDoInputBoundary;
import pendenzenliste.boundary.in.FetchToDoRequest;
import pendenzenliste.boundary.in.ResetToDoInputBoundary;
import pendenzenliste.boundary.in.ResetToDoRequest;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.boundary.in.UpdateToDoInputBoundary;
import pendenzenliste.boundary.in.UpdateToDoRequest;

class ToDoControllerTest
{
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

  @Test
  public void save_nullIdentity()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(CreateToDoInputBoundary.class);
    when(inputBoundaryFactory.create()).thenReturn(inputBoundary);

    var controller = new ToDoController(inputBoundaryFactory);

    controller.save(null, "test-headline", "test-description");

    verify(inputBoundary, times(1))
        .execute(new CreateToDoRequest("test-headline", "test-description"));
  }

  @Test
  public void save_emptyIdentity()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(CreateToDoInputBoundary.class);
    when(inputBoundaryFactory.create()).thenReturn(inputBoundary);

    var controller = new ToDoController(inputBoundaryFactory);

    controller.save("", "test-headline", "test-description");

    verify(inputBoundary, times(1))
        .execute(new CreateToDoRequest("test-headline", "test-description"));
  }

  @Test
  public void save_presentIdentity()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(UpdateToDoInputBoundary.class);
    when(inputBoundaryFactory.update()).thenReturn(inputBoundary);

    var controller = new ToDoController(inputBoundaryFactory);

    controller.save("test-identity", "test-headline", "test-description");

    verify(inputBoundary, times(1))
        .execute(new UpdateToDoRequest(
            "test-identity", "test-headline", "test-description"));
  }
}