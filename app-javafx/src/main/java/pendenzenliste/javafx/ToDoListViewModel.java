package pendenzenliste.javafx;

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
  public ObservableList<ToDoListItemViewModel> todos = FXCollections.observableArrayList();

  /**
   * This property is used to represent the selected todo.
   */
  public SimpleObjectProperty<ToDoListItemViewModel> selectedTodo = new SimpleObjectProperty<>();
}
