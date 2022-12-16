package pendenzenliste.javafx;

import java.time.LocalDateTime;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * A widget that can be used to render a table with a list of todos.
 */
public class ToDoTableWidget extends TableView<ToDoListDTO>
{
  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be bound to this table.
   */
  public ToDoTableWidget(final ToDoListViewModel viewModel)
  {
    super();

    final TableColumn<ToDoListDTO, String> headlineColumn = new TableColumn<>("Headline");

    headlineColumn.setCellValueFactory(dto -> new SimpleStringProperty(dto.getValue().headline()));

    final TableColumn<ToDoListDTO, String> stateColumn = new TableColumn<>("State");

    stateColumn.setCellValueFactory(dto -> new SimpleStringProperty(dto.getValue().state()));

    final TableColumn<ToDoListDTO, LocalDateTime> createdColumn = new TableColumn<>("Created");

    createdColumn.setCellValueFactory(dto -> new SimpleObjectProperty<>(dto.getValue().created()));

    final TableColumn<ToDoListDTO, LocalDateTime> lastModifiedColumn =
        new TableColumn<>("Last modified");

    lastModifiedColumn.setCellValueFactory(
        dto -> new SimpleObjectProperty<>(dto.getValue().lastModified()));

    getColumns().addAll(List.of(headlineColumn, stateColumn, createdColumn, lastModifiedColumn));

    getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> viewModel.selectedTodo.set(newValue));

    setItems(viewModel.todos);

    viewModel.selectedTodo.addListener(
        (observable, oldValue, newValue) -> getSelectionModel().select(newValue));
  }
}
