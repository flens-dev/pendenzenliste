package pendenzenliste.vaadin;

import java.util.Optional;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactoryProvider;

/**
 * A page in the app that renders a list of todos and allows the user to modify the todos.
 */
@PageTitle("Pendenzenliste")
@Route("")
public class ToDoPage extends Composite<Div>
{
  private final ToDoListViewModel viewModel = new ToDoListViewModel();

  /**
   * Creates a new instance.
   */
  public ToDoPage(@Autowired final ToDoInputBoundaryFactoryProvider inputBoundaryFactoryProvider)
  {
    super();

    getContent().setHeightFull();
    getContent().setWidthFull();

    final var view = new ToDoView(viewModel);
    final var presenter = new ToDoPresenterFactory(viewModel);
    final var controller = new ToDoController(inputBoundaryFactoryProvider.getInstance(presenter));

    view.addSaveListener(l -> controller.save(
        viewModel.identity.get(), viewModel.headline.get(), viewModel.description.get()));
    view.addClearListener(l -> viewModel.clearEditor());

    view.addEditListener(todo -> Optional.ofNullable(todo).map(t -> t.identity.get())
        .ifPresent(controller::loadForEdit));

    view.addCompleteListener(todo -> controller.complete(todo.identity.get()));
    view.addDeleteListener(todo -> controller.delete(todo.identity.get()));
    view.addResetListener(todo -> controller.reset(todo.identity.get()));

    controller.subscribeToDoList();

    getContent().add(view);
  }
}
