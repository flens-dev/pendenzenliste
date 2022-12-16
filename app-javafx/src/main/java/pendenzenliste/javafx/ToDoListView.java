package pendenzenliste.javafx;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
  public ToDoListView(final ToDoListViewModel listViewModel,
                      final EditToDoViewModel editViewModel)
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
  public ToDoListView(final AppLayout layout,
                      final ToDoListViewModel listViewModel, final EditToDoViewModel editViewModel)
  {
    super(layout);

    requireNonNull(listViewModel, "The list view model may not be null");
    this.editViewModel = requireNonNull(editViewModel, "The edit view model may not be null");

    layout.addToMain(new ToDoTableWidget(listViewModel));
    layout.addToDetail(buildEditForm());

    listViewModel.listUpdated.set(LocalDateTime.now());
  }

  /**
   * Builds an edit form.
   *
   * @return The edit form.
   */
  private VBox buildEditForm()
  {
    final var layout = new VBox();

    layout.setSpacing(10);

    final var headlineLabel = new Label("Headline");
    final var headlineField = new TextField();

    final var descriptionLabel = new Label("Description");
    final var descriptionField = new TextArea();

    final var errorMessage = new Label("");

    final var completeButton = new Button("Complete");
    final var deleteButton = new Button("Delete");
    final var resetButton = new Button("Reset");
    final var clearButton = new Button("Clear");
    final var saveButton = new Button("Save");

    headlineField.textProperty().bindBidirectional(editViewModel.headline);
    descriptionField.textProperty().bindBidirectional(editViewModel.description);
    errorMessage.textProperty().bind(editViewModel.errorMessage);

    editViewModel.completeButtonVisible.bindBidirectional(completeButton.visibleProperty());
    editViewModel.deleteButtonVisible.bindBidirectional(deleteButton.visibleProperty());
    editViewModel.resetButtonVisible.bindBidirectional(resetButton.visibleProperty());

    clearButton.setOnAction(event -> clearEditor());
    saveButton.setOnAction(event -> editViewModel.savedTrigger.set(LocalDateTime.now()));
    completeButton.setOnAction(event -> editViewModel.completedTrigger.set(LocalDateTime.now()));
    deleteButton.setOnAction(event -> editViewModel.deletedTrigger.set(LocalDateTime.now()));
    resetButton.setOnAction(event -> editViewModel.resetTrigger.set(LocalDateTime.now()));

    final var actions =
        new HBox(deleteButton, completeButton, resetButton, clearButton, saveButton);

    actions.setSpacing(10);

    layout.getChildren()
        .addAll(headlineLabel, headlineField, descriptionLabel, descriptionField, errorMessage,
            actions);

    return layout;
  }

  /**
   * Clears the editor.
   */
  public void clearEditor()
  {
    editViewModel.clearedTrigger.set(LocalDateTime.now());
  }
}
