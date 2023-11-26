package pendenzenliste.todos.gateway.filesystem;

import pendenzenliste.filesystem.util.FileStorage;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoAggregate;
import pendenzenliste.todos.model.ToDoDeletedEvent;
import pendenzenliste.todos.model.ToDoEntity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * A gateway that provides access to todos stored in the filesystem.
 */
public class FilesystemToDoGateway implements ToDoGateway {
    private final FileStorage<Map<IdentityValueObject, ToDoEntity>> storage;
    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param path     The path.
     * @param eventBus The event bus.
     */
    public FilesystemToDoGateway(final String path, final EventBus eventBus) {
        storage = new FileStorage<>(requireNonNull(path, "The path may not be null"));
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ToDoAggregate> findById(final IdentityValueObject id) {
        return Optional.ofNullable(storage.getOr(new ConcurrentHashMap<>()).getOrDefault(id, null))
                .map(todo -> new ToDoAggregate(todo, this, eventBus));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final IdentityValueObject id) {
        final Map<IdentityValueObject, ToDoEntity> data = storage.getOr(new ConcurrentHashMap<>());

        final var removed = data.remove(id);

        if (removed != null) {
            storage.flushToDisk(data);
            eventBus.publish(new ToDoDeletedEvent(LocalDateTime.now(), id));
        }

        return removed != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(final ToDoAggregate todo) {
        final Map<IdentityValueObject, ToDoEntity> data = storage.getOr(new ConcurrentHashMap<>());

        data.put(todo.aggregateRoot().identity(), todo.aggregateRoot());

        storage.flushToDisk(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<ToDoAggregate> fetchAll() {
        return storage.getOr(new ConcurrentHashMap<>()).values().stream()
                .map(todo -> new ToDoAggregate(todo, this, eventBus));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<ToDoAggregate> fetchAllCompletedBefore(final LocalDateTime timestamp) {
        return fetchAll().filter(todo -> todo.aggregateRoot().isClosed())
                .filter(todo -> todo.aggregateRoot().completed().isBefore(timestamp));
    }
}
