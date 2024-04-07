package pendenzenliste.todos.gateway.eclipsestore;

import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoAggregate;
import pendenzenliste.todos.model.ToDoDeletedEvent;
import pendenzenliste.todos.model.ToDoEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * An implementation of the {@link ToDoGateway} that makes use of the eclipse
 * store library to provide persistence.
 */
public class EclipseStoreTodoGateway implements ToDoGateway {
    private final EmbeddedStorageManager storageManager;
    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param storageManager The storage manager.
     * @param eventBus       The event bus.
     */
    public EclipseStoreTodoGateway(final EmbeddedStorageManager storageManager, final EventBus eventBus) {
        this.storageManager = requireNonNull(storageManager, "The storage manager may not be null");
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ToDoAggregate> findById(final IdentityValueObject id) {
        return getStore().getTodos().stream()
                .filter(todo -> Objects.equals(todo.identity(), id))
                .map(todo -> new ToDoAggregate(todo, this, eventBus))
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final IdentityValueObject id) {
        final ToDoStore store = getStore();
        final List<ToDoEntity> todos = store.getTodos();

        final Optional<ToDoEntity> todo =
                todos.stream().filter(t -> Objects.equals(t.identity(), id))
                        .findFirst();

        if (todo.isPresent()) {
            todos.remove(todo.get());
            store.setTodos(todos);
            storageManager.setRoot(store);
            storageManager.storeRoot();
            eventBus.publish(new ToDoDeletedEvent(LocalDateTime.now(), id));
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<ToDoAggregate> fetchAll() {
        return getStore().getTodos().stream().map(todo -> new ToDoAggregate(todo, this, eventBus));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<ToDoAggregate> fetchAllCompletedBefore(final LocalDateTime timestamp) {
        return fetchAll().filter(todo -> todo.aggregateRoot().isClosed())
                .filter(todo -> todo.aggregateRoot().completed().isBefore(timestamp));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(final ToDoAggregate todo) {
        final Optional<ToDoAggregate> existingToDo =
                findById(todo.aggregateRoot().identity());

        final ToDoStore store = getStore();

        final List<ToDoEntity> todos = store.getTodos();

        if (existingToDo.isPresent()) {
            final int index = todos.indexOf(existingToDo.get().aggregateRoot());
            todos.set(index, todo.aggregateRoot());
        } else {
            todos.add(todo.aggregateRoot());
        }
        store.setTodos(todos);
        storageManager.setRoot(store);
        storageManager.storeRoot();
    }

    /**
     * Retrieves the store.
     *
     * @return The store.
     */
    private ToDoStore getStore() {
        return Optional.ofNullable(storageManager.root())
                .map(r -> (ToDoStore) r)
                .orElse(new ToDoStore());
    }
}
