package pendenzenliste.todos.model;

import pendenzenliste.domain.util.HasCapabilities;
import pendenzenliste.messaging.EventBus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * An aggregate that can be used to represent a todo.
 */
public class ToDoAggregate implements HasCapabilities<ToDoCapabilityValueObject> {
    private ToDoEntity todo;
    private final ToDoService service;
    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param todo     The todo that should be represented by this instance.
     * @param service  The service.
     * @param eventBus The event bus.
     */
    public ToDoAggregate(final ToDoEntity todo,
                         final ToDoService service,
                         final EventBus eventBus) {
        this.todo = requireNonNull(todo, "The todo may not be null");
        this.service = requireNonNull(service, "The service may not be null");
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * The aggregate root.
     *
     * @return The aggregate root.
     */
    public ToDoEntity aggregateRoot() {
        return todo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<ToDoCapabilityValueObject> capabilities() {
        final Collection<ToDoCapabilityValueObject> capabilities = new ArrayList<>();

        if (aggregateRoot().isOpen()) {
            capabilities.add(ToDoCapabilityValueObject.COMPLETE);
            capabilities.add(ToDoCapabilityValueObject.UPDATE);
            capabilities.add(ToDoCapabilityValueObject.DELETE);
        }

        if (aggregateRoot().isClosed()) {
            capabilities.add(ToDoCapabilityValueObject.REOPEN);
            capabilities.add(ToDoCapabilityValueObject.DELETE);
        }

        return capabilities;
    }

    /**
     * Completes the todo.
     *
     * @param command The command.
     */
    public void complete(final CompleteToDoCommand command) {
        todo = todo.complete();

        save();
        publishEvent(new ToDoCompletedEvent(LocalDateTime.now(), aggregateRoot().identity()));
    }

    /**
     * Reopens the todo.
     *
     * @param command The command that should be executed.
     */
    public void reopen(final ReopenToDoCommand command) {
        todo = todo.reopen();

        save();
        publishEvent(new ToDoReopenedEvent(LocalDateTime.now(), aggregateRoot().identity()));
    }

    /**
     * Updates the todo based on the given command.
     *
     * @param command The command that should be used to update the todo.
     */
    public void update(final UpdateToDoCommand command) {
        todo = todo.changeHeadline(command.headline())
                .describe(command.description());

        save();
        publishEvent(new ToDoUpdatedEvent(LocalDateTime.now(), todo.identity()));
    }

    /**
     * Saves the aggregate.
     */
    private void save() {
        service.store(this);
    }

    /**
     * Publishes the given event.
     *
     * @param event The event.
     */
    private void publishEvent(final ToDoEvent event) {
        eventBus.publish(event);
    }
}
