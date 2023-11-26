package pendenzenliste.achievements.model;

import org.junit.jupiter.api.Test;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoCreatedEvent;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class AchievementProgressTrackingServiceTest {

    @Test
    public void onToDoEvent() {
        final var repository = mock(AchievementRepository.class);
        final var service = new AchievementProgressTrackingService(repository);

        final var achievement = mock(AchievementAggregate.class);

        when(repository.fetchAll()).thenReturn(Stream.of(achievement));

        final var event = new ToDoCreatedEvent(LocalDateTime.now(), IdentityValueObject.random());

        service.onEvent(event);

        verify(achievement, times(1)).trackProgress(event);
    }
}