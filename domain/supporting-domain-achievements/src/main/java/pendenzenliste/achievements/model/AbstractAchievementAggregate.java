package pendenzenliste.achievements.model;

import pendenzenliste.todos.model.*;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * An abstract base class that can be used to represent an achievement aggregate.
 */
public abstract class AbstractAchievementAggregate
        implements AchievementAggregate, ToDoEventVisitor, AchievementEventVisitor {
    protected AchievementEntity achievement;
    private final List<AchievementEventEntity> events;
    private final List<ProgressItemEntity> progressItems;

    /**
     * Creates a new instance.
     *
     * @param achievement   The achievement that should be represented by this instance.
     * @param events        The events that should be represented by this instance.
     * @param progressItems The progress that should be represented by this instance.
     */
    public AbstractAchievementAggregate(final AchievementEntity achievement,
                                        final Collection<AchievementEventEntity> events,
                                        final Collection<ProgressItemEntity> progressItems) {
        this.achievement = requireNonNull(achievement, "The achievement may not be null");
        this.events = new ArrayList<>(requireNonNull(events, "The events may not be null"));
        this.progressItems =
                new ArrayList<>(requireNonNull(progressItems, "The progress may not be null"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AchievementEntity aggregateRoot() {
        return achievement;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<AchievementEventEntity> events() {
        return new ArrayList<>(events);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<ProgressItemEntity> progressItems() {
        return new ArrayList<>(progressItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlockIfCompleted() {
        if (isCompleted() && isLocked()) {
            achievement =
                    new AchievementEntity(achievement.identity(), achievement.name(), StateValueType.UNLOCKED,
                            UnlockedTimestampValueType.now());

            events.add(new AchievementEventEntity(null,
                    new AchievementUnlockedEvent(LocalDateTime.now(), achievement.name())));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void trackProgress(final ToDoEvent event) {
        event.visit(this);

        unlockIfCompleted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void trackProgress(final AchievementEvent event) {
        event.visit(this);

        unlockIfCompleted();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoCompletedEvent event) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoCreatedEvent event) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoReopenedEvent event) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoDeletedEvent event) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ToDoUpdatedEvent event) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final AchievementUnlockedEvent event) {
    }

    /**
     * Adds the progress item.
     *
     * @param progress The progress item.
     */
    protected void addProgressItem(final ProgressItemEntity progress) {
        this.progressItems.add(progress);
    }

    /**
     * Replaces the progress item.
     *
     * @param currentValue The current value.
     * @param newValue     The new value.
     */
    protected void replaceProgressItem(final ProgressItemEntity currentValue,
                                       final ProgressItemEntity newValue) {
        final int index = this.progressItems.indexOf(currentValue);

        this.progressItems.set(index, newValue);
    }

    /**
     * Finds the progress item with the given identity.
     *
     * @param identity The identity of the progress item.
     * @return The progress item or {@link Optional#empty()}
     */
    protected Optional<ProgressItemEntity> findProgressItem(final String identity) {
        return this.progressItems.stream()
                .filter(i -> Objects.equals(i.identity().value(), identity))
                .findFirst();
    }

    /**
     * Increments a specific progress item.
     *
     * @param identity The identity of the progress item.
     */
    protected void incrementProgressItem(final String identity) {
        final ProgressItemEntity progressItem =
                findProgressItem(identity)
                        .orElseThrow(() -> new NoSuchElementException("The progress item with the ID " + identity + " does not exist"));

        replaceProgressItem(progressItem, progressItem.inc());
    }

    /**
     * Decrements the progress item.
     *
     * @param identity The identity of the progress item.
     */
    protected void decrementProgressItem(final String identity) {
        final ProgressItemEntity progressItem =
                findProgressItem(identity)
                        .orElseThrow(() -> new NoSuchElementException("The progress item with the ID " + identity + " does not exist"));

        replaceProgressItem(progressItem, progressItem.inc());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceEvent(final AchievementEventEntity oldEvent,
                             final AchievementEventEntity newEvent) {
        final List<AchievementEventEntity> currentEvents = new ArrayList<>(events);

        final int index = currentEvents.indexOf(oldEvent);

        currentEvents.set(index, newEvent);

        this.events.clear();
        this.events.addAll(currentEvents);
    }

    /**
     * An abstract builder for achievement aggregates.
     *
     * @param <T> The type of the builder.
     */
    public static abstract class AbstractBuilder<T extends AbstractBuilder<T>> {

        protected IdentityValueObject identity;
        protected AchievementValueType name;

        protected StateValueType state;

        protected UnlockedTimestampValueType unlocked;

        protected final Collection<AchievementEventEntity> events = new ArrayList<>();
        protected final Collection<ProgressItemEntity> progressItems = new ArrayList<>();

        /**
         * The identity of the achievement.
         *
         * @param identity The identity.
         * @return The builder.
         */
        public AbstractBuilder<T> identity(final String identity) {
            this.identity = new IdentityValueObject(identity);
            return this;
        }

        /**
         * Sets a random identity for the achievement.
         *
         * @return The builder.
         */
        public AbstractBuilder<T> randomIdentity() {
            this.identity = IdentityValueObject.random();
            return this;
        }

        /**
         * Sets the name of the achievement.
         *
         * @param name The name.
         * @return The builder.
         */
        protected AbstractBuilder<T> name(final AchievementValueType name) {
            this.name = name;

            return this;
        }

        /**
         * The state of the achievement.
         *
         * @param state The state.
         * @return The builder.
         */
        public AbstractBuilder<T> state(final StateValueType state) {
            this.state = state;
            return this;
        }

        /**
         * Sets the unlocked timestamp for the achievement.
         *
         * @param unlocked The unlocked timestamp.
         * @return The builder.
         */
        public AbstractBuilder<T> unlocked(final LocalDateTime unlocked) {
            this.unlocked = new UnlockedTimestampValueType(unlocked);
            return this;
        }

        /**
         * Adds the given events to the aggregate.
         *
         * @param events The events.
         * @return The builder.
         */
        public AbstractBuilder<T> withEvents(final AchievementEvent... events) {
            for (final AchievementEvent event : events) {
                this.events.add(new AchievementEventEntity(null, event));
            }
            return this;
        }

        /**
         * Builds the aggregate.
         *
         * @return The aggregate.
         */
        public abstract AchievementAggregate build();
    }
}
