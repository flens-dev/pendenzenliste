package pendenzenliste.vaadin;

import java.util.Collection;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

import com.vaadin.flow.component.AttachEvent;
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
  private final ToDoListWidget todoList = new ToDoListWidget();

  private final ToDoEditorWidget editor = new ToDoEditorWidget();

  private final ToDoController controller;
  private ToDoViewModel viewModel;

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
    mainContainer.add(todoList);

    todoList.addEditListener(l -> Optional.ofNullable(l)
        .map(ToDoListItemViewModel::identity)
        .ifPresent(controller::loadForEdit));

    todoList.addCompleteListener(l -> controller.complete(l.identity()));
    todoList.addDeleteListener(l -> controller.delete(l.identity()));
    todoList.addResetListener(l -> controller.reset(l.identity()));

    editor.addSaveListener(l -> {
      if (viewModel == null)
      {
        controller.create(editor.getHeadline(), editor.getDescription());
      } else
      {
        controller.update(viewModel.identity(), editor.getHeadline(), editor.getDescription());
      }
    });

    editor.addClearListener(l -> {
      editor.clear();
      viewModel = null;
    });

    container.add(mainContainer, editor);

    add(container);

    setHeightFull();
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
    todoList.setItems(todos);
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
    Notification.show(message).addThemeVariants(NotificationVariant.LUMO_ERROR);
  }

  /**
   * Sets the selected todo.
   *
   * @param viewModel The view model.
   */
  public void setSelectedToDo(final ToDoViewModel viewModel)
  {
    this.viewModel = viewModel;

    if (viewModel == null)
    {
      clearEditor();
    } else
    {
      editor.setHeadline(viewModel.headline());
      editor.setDescription(viewModel.description());
    }
  }

  /**
   * Clears the editor.
   */
  public void clearEditor()
  {
    editor.clear();
  }
}
