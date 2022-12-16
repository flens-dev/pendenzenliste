package pendenzenliste.javafx;

import java.time.LocalDateTime;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * A widget that can be used to render a table with a list of todos.
 */
public class ToDoTableWidget extends TableView<ToDoListItemViewModel>
{
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

    setItems(viewModel.todos);

    viewModel.selectedTodo.addListener(
        (observable, oldValue, newValue) -> getSelectionModel().select(newValue));
  }
}
