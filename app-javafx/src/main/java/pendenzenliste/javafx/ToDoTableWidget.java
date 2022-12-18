package pendenzenliste.javafx;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * A widget that can be used to render a table with a list of todos.
 */
public class ToDoTableWidget extends TableView<ToDoListItemViewModel>
{
  private final Collection<Consumer<ToDoListItemViewModel>> deleteListeners = new ArrayList<>();

  private final Collection<Consumer<ToDoListItemViewModel>> completeListeners = new ArrayList<>();

  private final Collection<Consumer<ToDoListItemViewModel>> resetListeners = new ArrayList<>();

  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be bound to this table.
   */
  public ToDoTableWidget(final ToDoListViewModel viewModel)
  {
    super();

    final TableColumn<ToDoListItemViewModel, String> headlineColumn = new TableColumn<>("Headline");

    headlineColumn.setCellValueFactory(item -> item.getValue().headline);

    final TableColumn<ToDoListItemViewModel, String> stateColumn = new TableColumn<>("State");

    stateColumn.setCellValueFactory(item -> item.getValue().state);

    final TableColumn<ToDoListItemViewModel, LocalDateTime> createdColumn =
        new TableColumn<>("Created");

    createdColumn.setCellValueFactory(item -> item.getValue().created);

    final TableColumn<ToDoListItemViewModel, LocalDateTime> lastModifiedColumn =
        new TableColumn<>("Last modified");

    lastModifiedColumn.setCellValueFactory(item -> item.getValue().lastModified);

    getColumns().addAll(List.of(headlineColumn, stateColumn, createdColumn, lastModifiedColumn));

    getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> viewModel.selectedTodo.set(newValue));

    setRowFactory(renderRow());
    setItems(viewModel.todos);

    viewModel.selectedTodo.addListener(
        (observable, oldValue, newValue) -> getSelectionModel().select(newValue));
  }

  /**
   * A renderer for table rows.
   *
   * @return The renderer.
   */
  private Callback<TableView<ToDoListItemViewModel>, TableRow<ToDoListItemViewModel>> renderRow()
  {
    return table -> {
      final TableRow<ToDoListItemViewModel> row = new TableRow<>();
      final var menu = new ContextMenu();

      final var completeItem = new MenuItem("Complete");
      final var resetItem = new MenuItem("Reset");
      final var deleteItem = new MenuItem("Delete");

      completeItem.setOnAction(
          event -> completeListeners.forEach(listener -> listener.accept(row.getItem())));

      resetItem.setOnAction(
          event -> resetListeners.forEach(listener -> listener.accept(row.getItem())));

      deleteItem.setOnAction(
          event -> deleteListeners.forEach(listener -> listener.accept(row.getItem())));

      menu.getItems().addAll(completeItem, resetItem, deleteItem);
      row.setContextMenu(menu);

      return row;
    };
  }

  /**
   * Adds a delete listener.
   *
   * @param listener The listener.
   */
  public void addDeleteListener(final Consumer<ToDoListItemViewModel> listener)
  {
    this.deleteListeners.add(listener);
  }

  /**
   * Adds a complete listener.
   *
   * @param listener The listener.
   */
  public void addCompleteListener(final Consumer<ToDoListItemViewModel> listener)
  {
    this.completeListeners.add(listener);
  }

  /**
   * Adds a reset listener.
   *
   * @param listener The listener.
   */
  public void addResetListener(final Consumer<ToDoListItemViewModel> listener)
  {
    this.resetListeners.add(listener);
  }
}
