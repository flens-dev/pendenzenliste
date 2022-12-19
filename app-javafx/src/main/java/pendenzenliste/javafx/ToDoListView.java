package pendenzenliste.javafx;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

/**
 * A list view for todos.
 */
public class ToDoListView extends Scene
{
  private final EditToDoViewModel editViewModel;

  /**
   * Creates a new instance.
   *
   * @param listViewModel The list view model.
   * @param editViewModel The edit view model.
   */
  public ToDoListView(final ToDoListViewModel listViewModel, final EditToDoViewModel editViewModel)
  {
    this(new AppLayout(), listViewModel, editViewModel);
  }

  /**
   * Creates a new instance.
   *
   * @param layout        The layout.
   * @param listViewModel The list view model.
   * @param editViewModel The edit view model.
   */
  public ToDoListView(final AppLayout layout, final ToDoListViewModel listViewModel,
                      final EditToDoViewModel editViewModel)
  {
    super(layout);

    requireNonNull(listViewModel, "The list view model may not be null");
    this.editViewModel = requireNonNull(editViewModel, "The edit view model may not be null");

    final var table = new ToDoTableWidget(listViewModel);

    layout.addToMain(table);
    layout.addToDetail(buildEditForm());

    table.addResetListener(todo -> editViewModel.publishEvent(
        new ResetRequestedEvent(LocalDateTime.now(), todo.identity.get())));

    table.addCompleteListener(todo -> editViewModel.publishEvent(
        new CompleteRequestedEvent(LocalDateTime.now(), todo.identity.get())));

    table.addDeleteListener(todo -> editViewModel.publishEvent(
        new DeleteRequestedEvent(LocalDateTime.now(), todo.identity.get())));
  }

  /**
   * Builds an edit form.
   *
   * @return The edit form.
   */
  private ToDoEditorWidget buildEditForm()
  {
    final var editor = new ToDoEditorWidget();

    final var layout = new VBox();

    layout.setSpacing(10);

    editor.getHeadlineField().textProperty().bindBidirectional(editViewModel.headline);
    editor.getDescriptionField().textProperty().bindBidirectional(editViewModel.description);
    editor.getErrorMessageLabel().textProperty().bind(editViewModel.errorMessage);

    editor.addClearListener(
        () -> editViewModel.publishEvent(new ClearEditorRequestedEvent(LocalDateTime.now())));

    editor.addSaveListener(() -> editViewModel.publishEvent(
        new SaveRequestedEvent(LocalDateTime.now(), editViewModel.identity.get(),
            editViewModel.headline.get(), editViewModel.description.get())));

    return editor;
  }
}
