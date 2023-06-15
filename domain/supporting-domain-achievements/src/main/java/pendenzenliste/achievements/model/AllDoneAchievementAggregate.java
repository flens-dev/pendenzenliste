package pendenzenliste.achievements.model;

import pendenzenliste.todos.model.ToDoCompletedEvent;
import pendenzenliste.todos.model.ToDoCreatedEvent;
import pendenzenliste.todos.model.ToDoReopenedEvent;

import java.util.Collection;

/**
 * An achievement aggregate that can be used to represent the 'All done!' achievement.
 * <p>
 * This achievement is unlocked, when a user completes all of his todos.
 */
public final class AllDoneAchievementAggregate extends AbstractAchievementAggregate {

    /**
     * Creates a new instance.
     *
     * @param achievement   The achievement that should be represented by this instance.
     * @param events        The events that should be represented by this instance.
     * @param progressItems The progress that should be represented by this instance.
     */
    public AllDoneAchievementAggregate(final AchievementEntity achievement,
                                       final Collection<AchievementEventEntity> events,
                                       final Collection<ProgressItemEntity> progressItems) {
        super(achievement, events, progressItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoCreatedEvent event) {
        addProgressItem(ProgressItemEntity.onlyOnce(event.identity().value()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoReopenedEvent event) {
        decrementProgressItem(event.identity().value());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoCompletedEvent event) {
        incrementProgressItem(event.identity().value());
    }

    /**
     * Creates a new builder instance.
     *
     * @return The builder.
     */
    public static AbstractBuilder<DonezoAchievementAggregate.Builder> builder() {
        return new Builder().name(AchievementValueType.ALL_DONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCompleted() {
        return progressItems().stream().allMatch(ProgressItemEntity::isCompleted);
    }

    /**
     * A builder implementation
     */
    public static class Builder extends AbstractBuilder<DonezoAchievementAggregate.Builder> {

        /**
         * {@inheritDoc}
         */
        @Override
        public AchievementAggregate build() {
            return new AllDoneAchievementAggregate(new AchievementEntity(identity, name, state, unlocked),
                    events, progressItems);
        }
    }
}
