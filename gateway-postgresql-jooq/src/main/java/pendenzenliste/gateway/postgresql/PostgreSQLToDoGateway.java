package pendenzenliste.gateway.postgresql;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import pendenzenliste.gateway.postgresql.generated.public_.Tables;
import pendenzenliste.gateway.postgresql.generated.public_.tables.records.TodosRecord;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.model.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * An implementation of the {@link ToDoGateway} that stores the todos in a PostgreSQL instance.
 */
public final class PostgreSQLToDoGateway implements ToDoGateway {

    private final DSLContext sql;
    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param sql      The SQL DSL that should be used by this instance.
     * @param eventBus The event bus that should be used by this instance.
     */
    public PostgreSQLToDoGateway(final DSLContext sql,
                                 final EventBus eventBus) {
        this.sql = requireNonNull(sql, "The SQL DSL may not be null");
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ToDoAggregate> findById(final IdentityValueObject id) {
        return sql.selectFrom(Tables.TODOS).where(Tables.TODOS.ID.eq(id.value())).limit(1).fetchOptional().map(mapRecord());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final IdentityValueObject id) {
        final var deletedRows = sql.deleteFrom(Tables.TODOS).where(Tables.TODOS.ID.eq(id.value())).execute();

        final var hasBeenDeleted = deletedRows == 1;

        if (hasBeenDeleted) {
            eventBus.publish(new ToDoDeletedEvent(LocalDateTime.now(), id));
        }

        return hasBeenDeleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(final ToDoAggregate todo) {
        sql.transaction(t -> {
            final var transaction = DSL.using(t);

            final var existingTodos = transaction.selectFrom(Tables.TODOS).where(Tables.TODOS.ID.eq(todo.aggregateRoot().identity().value())).fetch();

            if (existingTodos.isEmpty()) {
                transaction.insertInto(Tables.TODOS)
                        .set(Tables.TODOS.ID, todo.aggregateRoot().identity().value())
                        .set(Tables.TODOS.COMPLETED,
                                Optional.ofNullable(todo.aggregateRoot().completed()).map(CompletedTimestampValueObject::value).orElse(null))
                        .set(Tables.TODOS.CREATED, todo.aggregateRoot().created().value())
                        .set(Tables.TODOS.DESCRIPTION, todo.aggregateRoot().description().value())
                        .set(Tables.TODOS.HEADLINE, todo.aggregateRoot().headline().value())
                        .set(Tables.TODOS.LAST_MODIFIED, todo.aggregateRoot().lastModified().value())
                        .set(Tables.TODOS.STATE, todo.aggregateRoot().state().name())
                        .execute();
            } else {
                transaction.update(Tables.TODOS)
                        .set(Tables.TODOS.COMPLETED,
                                Optional.ofNullable(todo.aggregateRoot().completed()).map(CompletedTimestampValueObject::value).orElse(null))
                        .set(Tables.TODOS.CREATED, todo.aggregateRoot().created().value())
                        .set(Tables.TODOS.DESCRIPTION, todo.aggregateRoot().description().value())
                        .set(Tables.TODOS.HEADLINE, todo.aggregateRoot().headline().value())
                        .set(Tables.TODOS.LAST_MODIFIED, todo.aggregateRoot().lastModified().value())
                        .set(Tables.TODOS.STATE, todo.aggregateRoot().state().name())
                        .where(Tables.TODOS.ID.eq(todo.aggregateRoot().identity().value()))
                        .execute();
            }

            //TODO: Persist the events
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<ToDoAggregate> fetchAll() {
        return sql.selectFrom(Tables.TODOS).stream().map(mapRecord());
    }


    /**
     * Maps the record to an aggregate instance.
     *
     * @return The mapping function.
     */
    private Function<TodosRecord, ToDoAggregate> mapRecord() {
        return record -> new ToDoAggregate(new ToDoEntity(
                new IdentityValueObject(record.getId()),
                new HeadlineValueObject(record.getHeadline()),
                new DescriptionValueObject(record.getDescription()),
                new CreatedTimestampValueObject(record.getCreated()),
                new LastModifiedTimestampValueObject(record.getLastModified()),
                Optional.ofNullable(record.getCompleted()).map(CompletedTimestampValueObject::new).orElse(null),
                ToDoStateValueObject.valueOf(record.getState())
        ),
                this,
                eventBus);
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
