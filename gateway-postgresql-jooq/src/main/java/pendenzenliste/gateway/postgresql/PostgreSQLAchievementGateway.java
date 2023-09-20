package pendenzenliste.gateway.postgresql;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import pendenzenliste.achievements.gateway.AchievementGateway;
import pendenzenliste.achievements.model.AchievementAggregate;
import pendenzenliste.achievements.model.AchievementEvent;
import pendenzenliste.achievements.model.AchievementEventEntity;
import pendenzenliste.achievements.model.IdentityValueObject;
import pendenzenliste.gateway.postgresql.generated.public_.Tables;
import pendenzenliste.gateway.postgresql.generated.public_.tables.records.AchievementsRecord;
import pendenzenliste.messaging.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * An implementation of the {@link AchievementGateway} interface that can be used to store achievements in a PostgreSQL
 * database.
 */
public final class PostgreSQLAchievementGateway implements AchievementGateway {

    private final DSLContext sql;
    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param sql The SQL DSL that should be used by this instance.
     */
    public PostgreSQLAchievementGateway(final DSLContext sql,
                                        final EventBus eventBus) {

        this.sql = requireNonNull(sql, "The SQL DSL may not be null");
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AchievementAggregate> findById(final IdentityValueObject identity) {
        //TODO: Load progress items
        return sql.selectFrom(Tables.ACHIEVEMENTS)
                .where(Tables.ACHIEVEMENTS.ID.eq(identity.value()))
                .limit(1)
                .fetchOptional()
                .map(mapRecord());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<AchievementAggregate> fetchAll() {
        //TODO: Load progress items
        return sql.selectFrom(Tables.ACHIEVEMENTS)
                .fetch()
                .stream()
                .map(mapRecord());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(final AchievementAggregate achievement) {
        final Collection<AchievementEvent> eventQueue = new ArrayList<>();

        for (final AchievementEventEntity event :
                achievement.events()
                        .stream()
                        .filter(event -> event.identity() == null)
                        .toList()) {
            achievement.replaceEvent(event, event.withIdentity(IdentityValueObject.random()));
            eventQueue.add(event.event());
        }

        sql.transaction(t -> {
            final var transaction = DSL.using(t);

            final var existingAchievements = transaction.selectFrom(Tables.ACHIEVEMENTS)
                    .where(Tables.ACHIEVEMENTS.ID.eq(achievement.aggregateRoot().identity().value()))
                    .fetch();

            if (existingAchievements.isEmpty()) {
                transaction.insertInto(Tables.ACHIEVEMENTS)
                        .set(Tables.ACHIEVEMENTS.ID, achievement.aggregateRoot().identity().value())
                        .set(Tables.ACHIEVEMENTS.NAME, achievement.aggregateRoot().name().name())
                        .set(Tables.ACHIEVEMENTS.STATE, achievement.aggregateRoot().state().name())
                        .set(Tables.ACHIEVEMENTS.UNLOCKED, achievement.aggregateRoot().unlocked().value())
                        .execute();
            } else {
                transaction.update(Tables.ACHIEVEMENTS)
                        .set(Tables.ACHIEVEMENTS.NAME, achievement.aggregateRoot().name().name())
                        .set(Tables.ACHIEVEMENTS.STATE, achievement.aggregateRoot().state().name())
                        .set(Tables.ACHIEVEMENTS.UNLOCKED, achievement.aggregateRoot().unlocked().value())
                        .where(Tables.ACHIEVEMENTS.ID.eq(achievement.aggregateRoot().identity().value()))
                        .execute();
            }

            //TODO: Persist the events
            //TODO: Persist the progress items
        });

        eventQueue.forEach(eventBus::publish);
    }

    /**
     * Maps an {@link AchievementsRecord} to an {@link AchievementAggregate}
     *
     * @return The mapping function.
     */
    private static Function<AchievementsRecord, AchievementAggregate> mapRecord() {
        //TODO: Properly map the records into AchievementAggregate instances
        return record -> null;
    }
}
