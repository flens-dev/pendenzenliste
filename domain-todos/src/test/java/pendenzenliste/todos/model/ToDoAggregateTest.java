package pendenzenliste.todos.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.messaging.EventBus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ToDoAggregateTest {

    private final ToDoService service = mock(ToDoService.class);
    private final EventBus eventBus = mock(EventBus.class);

    @Test
    public void capabilities_openToDo() {
        final var todo = createOpenTodo();

        assertThat(todo.capabilities())
                .contains(ToDoCapabilityValueObject.COMPLETE,
                        ToDoCapabilityValueObject.UPDATE,
                        ToDoCapabilityValueObject.DELETE);
    }

    @Test
    public void capabilities_completedToDo() {
        final var todo = createCompletedTodo();

        assertThat(todo.capabilities())
                .contains(ToDoCapabilityValueObject.REOPEN);
    }

    @Test
    public void complete() {
        final var todo = createOpenTodo();

        todo.complete(new CompleteToDoCommand());

        assertThat(todo.aggregateRoot().isClosed()).isTrue();
        verify(service, times(1)).store(todo);
        verify(eventBus, times(1)).publish(any(ToDoCompletedEvent.class));
    }

    @Test
    public void reopen() {
        final var todo = createCompletedTodo();

        todo.reopen(new ReopenToDoCommand());

        assertThat(todo.aggregateRoot().isClosed()).isFalse();

        verify(service, times(1)).store(todo);
        verify(eventBus, times(1)).publish(any(ToDoReopenedEvent.class));
    }

    @Test
    public void update() {
        final var todo = createOpenTodo();

        todo.update(new UpdateToDoCommand(new HeadlineValueObject("I should do something completely different"),
                new DescriptionValueObject("Yeah, I should really do that instead")));

        final SoftAssertions assertions = new SoftAssertions();

        assertions.assertThat(todo.aggregateRoot().headline())
                .isEqualTo(new HeadlineValueObject("I should do something completely different"));

        assertions.assertThat(todo.aggregateRoot().description())
                .isEqualTo(new DescriptionValueObject("Yeah, I should really do that instead"));

        assertions.assertAll();

        verify(service, times(1)).store(todo);
        verify(eventBus, times(1)).publish(any(ToDoUpdatedEvent.class));
    }

    /**
     * A factory method that can be used to create an open todo.
     *
     * @return The open todo.
     */
    private ToDoAggregate createOpenTodo() {
        return new ToDoAggregate(new ToDoEntity(IdentityValueObject.random(),
                new HeadlineValueObject("Do something"),
                new DescriptionValueObject("I should really do something"),
                CreatedTimestampValueObject.now(),
                LastModifiedTimestampValueObject.now(),
                null,
                ToDoStateValueObject.OPEN),
                service,
                eventBus);
    }

    /**
     * A factory method that can be used to create a completed todo.
     *
     * @return The completed todo.
     */
    private ToDoAggregate createCompletedTodo() {
        return new ToDoAggregate(new ToDoEntity(IdentityValueObject.random(),
                new HeadlineValueObject("Do something"),
                new DescriptionValueObject("I should really do something"),
                CreatedTimestampValueObject.now(),
                LastModifiedTimestampValueObject.now(),
                CompletedTimestampValueObject.now(),
                ToDoStateValueObject.COMPLETED),
                service,
                eventBus);
    }
}