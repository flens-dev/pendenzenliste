package pendenzenliste.javafx;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

class CompleteRequestedEventTest
{

  @Test
  public void visit()
  {
    var visitor = mock(ToDoEventVisitor.class);

    var event =
        new CompleteRequestedEvent(LocalDateTime.now(), "some-identity");

    event.visit(visitor);

    verify(visitor, times(1)).visit(event);
  }
}