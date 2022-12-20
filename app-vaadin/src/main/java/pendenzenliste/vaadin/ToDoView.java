package pendenzenliste.vaadin;

import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

/**
 * A view that can be used to display a list of todos and interact with them.
 */
public class ToDoView extends Div
{
  private final ToDoListViewModel toDoListViewModel;

  private final ToDoEditorWidget editor = new ToDoEditorWidget();

  private final ToDoListWidget todoList = new ToDoListWidget();

  /**
   * Creates a new instance.
   */
  public ToDoView(final ToDoListViewModel viewModel)
  {
    super();

    this.toDoListViewModel = requireNonNull(viewModel, "The view model may not be null");

    final var container = new Div();

    container.setHeightFull();
    container.getStyle().set("display", "grid");
    container.getStyle().set("grid-template-columns", "2fr 1fr");

    final var mainContainer = new Div();

    mainContainer.getStyle().set("padding", "var(--lumo-space-m)");

    mainContainer.add(todoList);

    final var ui = UI.getCurrent();

    toDoListViewModel.todos.bind(items -> ui.access(() -> todoList.setItems(items)));
    toDoListViewModel.headline.bindTwoWay(editor.getHeadlineField());
    toDoListViewModel.description.bindTwoWay(editor.getDescriptionField());
    toDoListViewModel.errorMessage.bind(this::showGenericErrorMessage);

    container.add(mainContainer, editor);

    add(container);

    setHeightFull();
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
      toDoListViewModel.errorMessage.clear();
    }
  }

  /**
   * Clears the editor.
   */
  public void clearEditor()
  {
    toDoListViewModel.clearEditor();
  }

  /**
   * Adds an edit listener.
   *
   * @param listener The listener.
   */
  public void addEditListener(final Consumer<ToDoListItemViewModel> listener)
  {
    todoList.addEditListener(listener);
  }

  /**
   * Adds a complete listener.
   *
   * @param listener The listener.
   */
  public void addCompleteListener(final Consumer<ToDoListItemViewModel> listener)
  {
    todoList.addCompleteListener(listener);
  }

  /**
   * Adds a delete listener.
   *
   * @param listener The listener.
   */
  public void addDeleteListener(final Consumer<ToDoListItemViewModel> listener)
  {
    todoList.addDeleteListener(listener);
  }

  /**
   * Adds a reset listener.
   *
   * @param listener The listener.
   */
  public void addResetListener(final Consumer<ToDoListItemViewModel> listener)
  {
    todoList.addResetListener(listener);
  }

  /**
   * Adds a save listener.
   *
   * @param listener The listener.
   */
  public void addSaveListener(final ComponentEventListener<ClickEvent<Button>> listener)
  {
    editor.addSaveListener(listener);
  }

  /**
   * Adds a clear listener.
   *
   * @param listener The listener.
   */
  public void addClearListener(final ComponentEventListener<ClickEvent<Button>> listener)
  {
    editor.addClearListener(listener);
  }
}
