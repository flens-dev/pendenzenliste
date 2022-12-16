package pendenzenliste.javafx;

import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A view model that can be used to represent a todo in a list view.
 */
public class ToDoListItemViewModel
{
  public final StringProperty identity = new SimpleStringProperty();

  public final StringProperty headline = new SimpleStringProperty();

  public final ObjectProperty<LocalDateTime> created = new SimpleObjectProperty<>();

  public final ObjectProperty<LocalDateTime> lastModified = new SimpleObjectProperty<>();

  public final ObjectProperty<LocalDateTime> completed = new SimpleObjectProperty<>();

  public final StringProperty state = new SimpleStringProperty();
}
