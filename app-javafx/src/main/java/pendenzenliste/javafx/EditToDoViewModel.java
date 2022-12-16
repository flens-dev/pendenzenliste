package pendenzenliste.javafx;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A view model that can be used to represent the data used to edit a todo.
 */
public class EditToDoViewModel
{
  public final SimpleStringProperty identity = new SimpleStringProperty();

  public final SimpleStringProperty headline = new SimpleStringProperty();

  public final SimpleStringProperty description = new SimpleStringProperty();

  public final SimpleStringProperty errorMessage = new SimpleStringProperty();

  public final SimpleBooleanProperty completeButtonVisible = new SimpleBooleanProperty(false);

  public final SimpleBooleanProperty deleteButtonVisible = new SimpleBooleanProperty(false);

  public final SimpleBooleanProperty resetButtonVisible = new SimpleBooleanProperty(false);


  public final SimpleObjectProperty<LocalDateTime> savedTrigger = new SimpleObjectProperty<>();

  public final SimpleObjectProperty<LocalDateTime> clearedTrigger = new SimpleObjectProperty<>();
  public final SimpleObjectProperty<LocalDateTime> completedTrigger = new SimpleObjectProperty<>();

  public final SimpleObjectProperty<LocalDateTime> deletedTrigger = new SimpleObjectProperty<>();

  public final SimpleObjectProperty<LocalDateTime> resetTrigger = new SimpleObjectProperty<>();
}
