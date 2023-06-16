package pendenzenliste.vaadin;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pendenzenliste.achievements.boundary.in.AchievementInputBoundaryFactoryProvider;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactoryProvider;

import java.util.Optional;

/**
 * A page in the app that renders a list of todos and allows the user to modify the todos.
 */
@PageTitle("Pendenzenliste")
@Route("")
public class ToDoPage extends Composite<Div> {
    private static final long serialVersionUID = 1L;

    private final ToDoListViewModel viewModel = new ToDoListViewModel();

    /**
     * Creates a new instance.
     */
    public ToDoPage(@Autowired final ToDoInputBoundaryFactoryProvider todoFactory,
                    @Autowired final AchievementInputBoundaryFactoryProvider achievementFactory) {
        super();

        getContent().setHeightFull();
        getContent().setWidthFull();

        final var view = new ToDoView(viewModel);
        final var todoPresenter = new ToDoPresenterFactory(viewModel);
        final var achievementPresenter = new AchievementPresenterFactory(viewModel);
        final var controller = new ToDoController(
                todoFactory.getInstance(todoPresenter),
                achievementFactory.getInstance(achievementPresenter));

        view.addSaveListener(l -> controller.save(
                viewModel.identity.get(), viewModel.headline.get(), viewModel.description.get()));
        view.addClearListener(l -> viewModel.clearEditor());

        view.addEditListener(todo -> Optional.ofNullable(todo).map(t -> t.identity.get())
                .ifPresent(controller::loadForEdit));

        view.addCompleteListener(todo -> controller.complete(todo.identity.get()));
        view.addDeleteListener(todo -> controller.delete(todo.identity.get()));
        view.addResetListener(todo -> controller.reset(todo.identity.get()));
        view.addUpdateAchievementsListener(controller::fetchAchievementList);

        controller.subscribeToDoList();
        controller.subscribeAchievements();
        controller.fetchAchievementList();

        getContent().add(view);
    }
}
