package pendenzenliste.vaadin;

import java.util.Collection;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;
import pendenzenliste.ports.in.ToDoInputBoundaryFactoryProvider;

/**
 * A view that can be used to display a list of todos and interact with them.
 */
@Route("")
public class ToDoView extends Div
{
  private final ToDoListViewModel toDoListViewModel = new ToDoListViewModel();

  private final ToDoController controller;

  /**
   * Creates a new instance.
   */
  public ToDoView()
  {
    //TODO: Instance should be injected from somewhere I guess...
    this(ToDoInputBoundaryFactoryProvider.defaultProvider());
  }

  /**
   * Creates a new instance.
   */
  public ToDoView(final ToDoInputBoundaryFactoryProvider inputBoundaryFactoryProvider)
  {
    super();

    this.controller = new ToDoController(requireNonNull(inputBoundaryFactoryProvider,
        "The input boundary factory provider may not be null").getInstance(
        new ToDoPresenterFactory(this)));

    final var container = new Div();

    container.setHeightFull();
    container.getStyle().set("display", "grid");
    container.getStyle().set("grid-template-columns", "2fr 1fr");

    final var mainContainer = new Div();

    mainContainer.getStyle().set("padding", "var(--lumo-space-m)");

    final var todoList = new ToDoListWidget();

    todoList.addEditListener(l -> Optional.ofNullable(l).map(todo -> todo.identity.get())
        .ifPresent(controller::loadForEdit));

    todoList.addCompleteListener(l -> controller.complete(l.identity.get()));
    todoList.addDeleteListener(l -> controller.delete(l.identity.get()));
    todoList.addResetListener(l -> controller.reset(l.identity.get()));

    mainContainer.add(todoList);

    final var editor = new ToDoEditorWidget();

    editor.addSaveListener(saveToDo());
    editor.addClearListener(l -> clearEditor());

    toDoListViewModel.todos.bind(todoList::setItems);
    toDoListViewModel.headline.bindTwoWay(editor.getHeadlineField());
    toDoListViewModel.description.bindTwoWay(editor.getDescriptionField());
    toDoListViewModel.errorMessage.bind(this::showGenericErrorMessage);

    container.add(mainContainer, editor);

    add(container);

    setHeightFull();
  }

  /**
   * Saves the todo.
   *
   * @return The listener.
   */
  private ComponentEventListener<ClickEvent<Button>> saveToDo()
  {
    return l -> {
      if (toDoListViewModel.identity.isEmpty())
      {
        controller.create(toDoListViewModel.headline.get(), toDoListViewModel.description.get());
      } else
      {
        controller.update(toDoListViewModel.identity.get(), toDoListViewModel.headline.get(),
            toDoListViewModel.description.get());
      }
    };
  }

  /**
   * Loads the todos.
   */
  public void loadToDos()
  {
    controller.loadTodos();
  }

  /**
   * Sets the todos.
   *
   * @param todos The todos.
   */
  public void setToDos(final Collection<ToDoListItemViewModel> todos)
  {
    toDoListViewModel.todos.set(todos);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onAttach(final AttachEvent attachEvent)
  {
    super.onAttach(attachEvent);

    loadToDos();
  }

  /**
   * Shows a generic error message.
   *
   * @param message The message that should be shown.
   */
  public void showGenericErrorMessage(final String message)
  {
    if (message != null && !message.isEmpty())
    {
      Notification.show(message).addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
  }

  /**
   * Sets the selected todo.
   *
   * @param viewModel The view model.
   */
  public void setSelectedToDo(final ToDoViewModel viewModel)
  {
    if (viewModel == null)
    {
      clearEditor();
    } else
    {
      toDoListViewModel.identity.set(viewModel.identity());
      toDoListViewModel.headline.set(viewModel.headline());
      toDoListViewModel.description.set(viewModel.description());
    }
  }

  /**
   * Clears the editor.
   */
  public void clearEditor()
  {
    toDoListViewModel.identity.set(null);
    toDoListViewModel.headline.set("");
    toDoListViewModel.description.set("");
  }
}
