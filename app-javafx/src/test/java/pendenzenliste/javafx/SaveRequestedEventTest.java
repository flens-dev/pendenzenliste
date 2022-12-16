package pendenzenliste.javafx;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

class SaveRequestedEventTest
{

  @Test
  public void visit()
  {
    var visitor = mock(ToDoEventVisitor.class);
    var event = new SaveRequestedEvent(LocalDateTime.now(), "some-identity", "some-headline",
        "some-description");

    event.visit(visitor);

    verify(visitor, times(1)).visit(event);
  }
}