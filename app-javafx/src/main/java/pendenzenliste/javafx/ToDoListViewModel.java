package pendenzenliste.javafx;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A view model for the todo list view.
 */
public class ToDoListViewModel
{

  /**
   * This property is used to represent the todos displayed by the list.
   */
  public ObservableList<ToDoListDTO> todos = FXCollections.observableArrayList();

  /**
   * This property is used to represent the selected todo.
   */
  public SimpleObjectProperty<ToDoListDTO> selectedTodo = new SimpleObjectProperty<>();

  /**
   * This property is used to notify the view that the list has been updated.
   */
  public SimpleObjectProperty<LocalDateTime> listUpdated = new SimpleObjectProperty<>();
}
