package pendenzenliste.achievements.model;

import java.time.LocalDateTime;
import java.util.Collection;

import pendenzenliste.todos.model.ToDoCreatedEvent;

/**
 * An achievement aggregate that can be used to represent the 'New Year, New Me!' achievement
 * <p>
 * This achievement should be unlocked when the user creates his first todo on new year.
 */
public class NewYearNewMeAchievementAggregate extends AbstractAchievementAggregate {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance.
     *
     * @param achievement   The achievement that should be represented by this instance.
     * @param events        The events that should be represented by this instance.
     * @param progressItems The progress that should be represented by this instance.
     */
    public NewYearNewMeAchievementAggregate(final AchievementEntity achievement,
                                            final Collection<AchievementEventEntity> events,
                                            final Collection<ProgressItemEntity> progressItems) {
        super(achievement, events, progressItems);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoCreatedEvent event) {
        if (isNewYear(event.timestamp())) {
            final ProgressItemEntity progressItem = progressItems().stream()
                    .findFirst()
                    .orElse(new ProgressItemEntity(new IdentityValueObject("*"), 0, 1));

            addProgressItem(progressItem.inc());
        }
    }

    /**
     * Checks whether the given timestamp is on new year.
     *
     * @param timestamp The timestamp.
     * @return True if the given timestamp is on new year.
     */
    private boolean isNewYear(final LocalDateTime timestamp) {
        return timestamp.getDayOfYear() == 1;
    }

    /**
     * Creates a new builder instance.
     *
     * @return The builder.
     */
    public static AbstractBuilder<NewYearNewMeAchievementAggregate.Builder> builder() {
        return new NewYearNewMeAchievementAggregate.Builder().name(
                AchievementValueType.NEW_YEAR_NEW_ME);
    }

    /**
     * A builder implementation
     */
    public static class Builder extends AbstractBuilder<NewYearNewMeAchievementAggregate.Builder> {

        /**
         * {@inheritDoc}
         */
        @Override
        public AchievementAggregate build() {
            return new NewYearNewMeAchievementAggregate(
                    new AchievementEntity(identity, name, state, unlocked),
                    events, progressItems);
        }
    }
}
