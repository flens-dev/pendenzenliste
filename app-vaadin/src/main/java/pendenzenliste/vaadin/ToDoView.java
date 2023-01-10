package pendenzenliste.vaadin;

import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * A view that can be used to display a list of todos and interact with them.
 */
@JsModule("./src/todo-view.ts")
@Tag("todo-view")
public class ToDoView extends Component implements HasSize, HasComponents
{
  private final ToDoListViewModel viewModel;

  private final ToDoEditorWidget editor = new ToDoEditorWidget();

  private final ToDoListWidget todoList = new ToDoListWidget();

  private final UI ui = UI.getCurrent();

  /**
   * Creates a new instance.
   */
  public ToDoView(final ToDoListViewModel viewModel)
  {
    super();

    this.viewModel = requireNonNull(viewModel, "The view model may not be null");

    final var ui = UI.getCurrent();

    this.viewModel.todos.bind(items -> ui.access(() -> todoList.setItems(items)));
    this.viewModel.headline.bindTwoWay(editor.getHeadlineField());
    this.viewModel.description.bindTwoWay(editor.getDescriptionField());
    this.viewModel.errorMessage.bind(this::showGenericErrorMessage);
    this.viewModel.unlockedAchievement.bind(this::showUnlockedAchievement);

    editor.getElement().setProperty("slot", "editor");
    todoList.getElement().setProperty("slot", "list");

    add(todoList, editor);

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
      viewModel.errorMessage.clear();
    }
  }

  /**
   * Shows an unlocked achievement.
   *
   * @param achievement The achievement.
   */
  public void showUnlockedAchievement(final UnlockedAchievementDTO achievement)
  {
    if (achievement != null)
    {
      ui.accessSynchronously(() -> {
        createAchievementUnlockedNotification(achievement).open();
        viewModel.unlockedAchievement.set(null);
      });
    }
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

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onAttach(final AttachEvent attachEvent)
  {
    super.onAttach(attachEvent);

    viewModel.detached.set(Boolean.FALSE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onDetach(final DetachEvent detachEvent)
  {
    super.onDetach(detachEvent);

    viewModel.detached.set(Boolean.TRUE);
  }

  public static Notification createAchievementUnlockedNotification(
      final UnlockedAchievementDTO achievement)
  {
    final var notification = new Notification();

    final var icon = VaadinIcon.CHECK_CIRCLE.create();
    icon.setColor("var(--lumo-success-color)");

    final var uploadSuccessful = new Div(new Text(achievement.title()));
    uploadSuccessful.getStyle().set("font-weight", "600").set("color",
        "var(--lumo-success-text-color)");

    final var info = new Div(uploadSuccessful,
        new Div(new Text(achievement.description())));
    info.getStyle().set("font-size", "var(--lumo-font-size-s)").set("color",
        "var(--lumo-secondary-text-color)");

    HorizontalLayout layout = new HorizontalLayout(icon, info);
    layout.setAlignItems(FlexComponent.Alignment.CENTER);

    notification.add(layout);
    notification.setDuration(10000);

    return notification;
  }
}
