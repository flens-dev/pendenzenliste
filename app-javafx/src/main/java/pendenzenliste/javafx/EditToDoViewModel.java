package pendenzenliste.javafx;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import javafx.beans.property.SimpleBooleanProperty;
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

  private final Subject<ToDoEvent> subject = PublishSubject.create();

  /**
   * The events.
   *
   * @return The events.
   */
  public Observable<ToDoEvent> events()
  {
    return Observable.wrap(subject);
  }

  /**
   * Publishes the given event.
   *
   * @param event The event.
   */
  public void publishEvent(final ToDoEvent event)
  {
    subject.onNext(event);
  }
}
