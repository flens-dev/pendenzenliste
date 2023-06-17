package pendenzenliste.achievements.model;

import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An achievement aggregate that can be used to represent the 'Lazy dog' achievement.
 * <p>
 * This achievement should be unlocked when the user has an open todo for 3 months.
 * <p>
 * Currently this achievement has a minor issue. As it is time based it will only be unlocked, when
 * actual interactions happen in the application. In the future it should be possible to evaluate the
 * progress regardless of the users interaction patterns, e.g. on a scheduled basis.
 */
public class LazyDogAchievementAggregate extends AbstractAchievementAggregate {

    private static final long serialVersionUID = 1L;

    private final Map<IdentityValueObject, LocalDateTime> timestamps = new ConcurrentHashMap<>();

    /**
     * Creates a new instance.
     *
     * @param achievement   The achievement that should be represented by this instance.
     * @param events        The events that should be represented by this instance.
     * @param progressItems The progress that should be represented by this instance.
     */
    public LazyDogAchievementAggregate(final AchievementEntity achievement,
                                       final Collection<AchievementEventEntity> events,
                                       final Collection<ProgressItemEntity> progressItems) {
        super(achievement, events, progressItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoCompletedEvent event) {
        timestamps.remove(event.identity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoReopenedEvent event) {
        timestamps.put(event.identity(), event.timestamp());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoCreatedEvent event) {
        timestamps.put(event.identity(), event.timestamp());

        addProgressItem(ProgressItemEntity.onlyOnce(event.identity().value()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoDeletedEvent event) {
        timestamps.remove(event.identity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCompleted() {
        final var threeMonthsAgo = LocalDateTime.now().minus(3, ChronoUnit.MONTHS);

        for (final Map.Entry<IdentityValueObject, LocalDateTime> entry : timestamps.entrySet()) {
            if (threeMonthsAgo.isAfter(entry.getValue()) || threeMonthsAgo.isEqual(entry.getValue())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates a new builder instance.
     *
     * @return The builder.
     */
    public static AbstractBuilder<Builder> builder() {
        return new Builder().name(AchievementValueType.LAZY_DOG);
    }

    /**
     * A builder implementation
     */
    public static class Builder extends AbstractBuilder<Builder> {

        /**
         * {@inheritDoc}
         */
        @Override
        public AchievementAggregate build() {
            return new LazyDogAchievementAggregate(new AchievementEntity(identity, name, state, unlocked),
                    events, progressItems);
        }
    }
}
