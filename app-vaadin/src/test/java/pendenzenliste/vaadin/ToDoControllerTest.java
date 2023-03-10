package pendenzenliste.vaadin;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import pendenzenliste.achievements.boundary.in.AchievementInputBoundaryFactory;
import pendenzenliste.todos.boundary.in.CompleteToDoInputBoundary;
import pendenzenliste.todos.boundary.in.CompleteToDoRequest;
import pendenzenliste.todos.boundary.in.CreateToDoInputBoundary;
import pendenzenliste.todos.boundary.in.CreateToDoRequest;
import pendenzenliste.todos.boundary.in.DeleteToDoInputBoundary;
import pendenzenliste.todos.boundary.in.DeleteToDoRequest;
import pendenzenliste.todos.boundary.in.FetchToDoInputBoundary;
import pendenzenliste.todos.boundary.in.FetchToDoRequest;
import pendenzenliste.todos.boundary.in.ReopenToDoInputBoundary;
import pendenzenliste.todos.boundary.in.ResetToDoRequest;
import pendenzenliste.todos.boundary.in.SubscribeToDoListInputBoundary;
import pendenzenliste.todos.boundary.in.SubscribeToDoListRequest;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.todos.boundary.in.UpdateToDoInputBoundary;
import pendenzenliste.todos.boundary.in.UpdateToDoRequest;


class ToDoControllerTest
{
  @Test
  public void loadForEdit()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(FetchToDoInputBoundary.class);
    when(inputBoundaryFactory.fetch()).thenReturn(inputBoundary);
    var achievementFactory = mock(AchievementInputBoundaryFactory.class);

    var controller = new ToDoController(inputBoundaryFactory, achievementFactory);

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
    var achievementFactory = mock(AchievementInputBoundaryFactory.class);

    var controller = new ToDoController(inputBoundaryFactory, achievementFactory);

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
    var achievementFactory = mock(AchievementInputBoundaryFactory.class);

    var controller = new ToDoController(inputBoundaryFactory, achievementFactory);

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
    var achievementFactory = mock(AchievementInputBoundaryFactory.class);

    var controller = new ToDoController(inputBoundaryFactory, achievementFactory);

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
    var achievementFactory = mock(AchievementInputBoundaryFactory.class);

    var controller = new ToDoController(inputBoundaryFactory, achievementFactory);

    controller.complete("test-identity");

    verify(inputBoundary, times(1))
        .execute(new CompleteToDoRequest("test-identity"));
  }

  @Test
  public void reset()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(ReopenToDoInputBoundary.class);
    when(inputBoundaryFactory.reopen()).thenReturn(inputBoundary);
    var achievementFactory = mock(AchievementInputBoundaryFactory.class);

    var controller = new ToDoController(inputBoundaryFactory, achievementFactory);

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
    var achievementFactory = mock(AchievementInputBoundaryFactory.class);

    var controller = new ToDoController(inputBoundaryFactory, achievementFactory);

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
    var achievementFactory = mock(AchievementInputBoundaryFactory.class);

    var controller = new ToDoController(inputBoundaryFactory, achievementFactory);

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
    var achievementFactory = mock(AchievementInputBoundaryFactory.class);

    var controller = new ToDoController(inputBoundaryFactory, achievementFactory);

    controller.save("test-identity", "test-headline", "test-description");

    verify(inputBoundary, times(1))
        .execute(new UpdateToDoRequest(
            "test-identity", "test-headline", "test-description"));
  }

  @Test
  public void subscribeToDoList()
  {
    var inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    var inputBoundary = mock(SubscribeToDoListInputBoundary.class);
    when(inputBoundaryFactory.subscribe()).thenReturn(inputBoundary);
    var achievementFactory = mock(AchievementInputBoundaryFactory.class);

    var controller = new ToDoController(inputBoundaryFactory, achievementFactory);

    controller.subscribeToDoList();

    verify(inputBoundary, times(1)).execute(new SubscribeToDoListRequest());
  }
}