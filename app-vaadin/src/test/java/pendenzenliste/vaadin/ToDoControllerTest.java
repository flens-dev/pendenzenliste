package pendenzenliste.vaadin;

import org.junit.jupiter.api.Test;
import pendenzenliste.achievements.boundary.in.*;
import pendenzenliste.todos.boundary.in.*;

import static org.mockito.Mockito.*;


class ToDoControllerTest {

    private final ToDoInputBoundaryFactory inputBoundaryFactory = mock(ToDoInputBoundaryFactory.class);
    private final AchievementInputBoundaryFactory achievementFactory = mock(AchievementInputBoundaryFactory.class);
    private final ToDoController controller = new ToDoController(inputBoundaryFactory, achievementFactory);

    @Test
    public void loadForEdit() {
        var inputBoundary = mock(FetchToDoInputBoundary.class);
        when(inputBoundaryFactory.fetch()).thenReturn(inputBoundary);

        controller.loadForEdit("test-identity");

        verify(inputBoundary, times(1))
                .execute(new FetchToDoRequest("test-identity"));
    }

    @Test
    public void update() {
        var inputBoundary = mock(UpdateToDoInputBoundary.class);
        when(inputBoundaryFactory.update()).thenReturn(inputBoundary);

        controller.update("test-identity", "test-headline", "test-description");

        verify(inputBoundary, times(1))
                .execute(new UpdateToDoRequest(
                        "test-identity", "test-headline", "test-description"));
    }

    @Test
    public void create() {
        var inputBoundary = mock(CreateToDoInputBoundary.class);
        when(inputBoundaryFactory.create()).thenReturn(inputBoundary);

        controller.create("test-headline", "test-description");

        verify(inputBoundary, times(1))
                .execute(new CreateToDoRequest("test-headline", "test-description"));
    }

    @Test
    public void delete() {
        var inputBoundary = mock(DeleteToDoInputBoundary.class);
        when(inputBoundaryFactory.delete()).thenReturn(inputBoundary);

        controller.delete("test-identity");

        verify(inputBoundary, times(1))
                .execute(new DeleteToDoRequest("test-identity"));
    }

    @Test
    public void complete() {
        var inputBoundary = mock(CompleteToDoInputBoundary.class);
        when(inputBoundaryFactory.complete()).thenReturn(inputBoundary);

        controller.complete("test-identity");

        verify(inputBoundary, times(1))
                .execute(new CompleteToDoRequest("test-identity"));
    }

    @Test
    public void reset() {
        var inputBoundary = mock(ReopenToDoInputBoundary.class);
        when(inputBoundaryFactory.reopen()).thenReturn(inputBoundary);

        controller.reset("test-identity");

        verify(inputBoundary, times(1))
                .execute(new ReopenToDoRequest("test-identity"));
    }

    @Test
    public void save_nullIdentity() {
        var inputBoundary = mock(CreateToDoInputBoundary.class);
        when(inputBoundaryFactory.create()).thenReturn(inputBoundary);

        controller.save(null, "test-headline", "test-description");

        verify(inputBoundary, times(1))
                .execute(new CreateToDoRequest("test-headline", "test-description"));
    }

    @Test
    public void save_emptyIdentity() {
        var inputBoundary = mock(CreateToDoInputBoundary.class);
        when(inputBoundaryFactory.create()).thenReturn(inputBoundary);

        controller.save("", "test-headline", "test-description");

        verify(inputBoundary, times(1))
                .execute(new CreateToDoRequest("test-headline", "test-description"));
    }

    @Test
    public void save_presentIdentity() {
        var inputBoundary = mock(UpdateToDoInputBoundary.class);
        when(inputBoundaryFactory.update()).thenReturn(inputBoundary);

        controller.save("test-identity", "test-headline", "test-description");

        verify(inputBoundary, times(1))
                .execute(new UpdateToDoRequest(
                        "test-identity", "test-headline", "test-description"));
    }

    @Test
    public void subscribeToDoList() {
        var inputBoundary = mock(SubscribeToDoListInputBoundary.class);
        when(inputBoundaryFactory.subscribe()).thenReturn(inputBoundary);

        controller.subscribeToDoList();

        verify(inputBoundary, times(1)).execute(new SubscribeToDoListRequest());
    }

    @Test
    public void subscribeAchievements() {
        var inputBoundary = mock(SubscribeAchievementsInputBoundary.class);
        when(achievementFactory.subscribe()).thenReturn(inputBoundary);

        controller.subscribeAchievements();

        verify(inputBoundary, times(1)).execute(new SubscribeAchievementsRequest());
    }

    @Test
    public void fetchAchievementList() {
        var inputBoundary = mock(FetchAchievementListInputBoundary.class);
        when(achievementFactory.list()).thenReturn(inputBoundary);

        controller.fetchAchievementList();

        verify(inputBoundary, times(1)).execute(new FetchAchievementListRequest());
    }
}