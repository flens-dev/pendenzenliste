package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.todos.ToDoEvent;

/**
 * An aggregate that can be used to represent an achievement.
 */
public class AchievementAggregate
{
  private AchievementEntity achievement;
  private final Collection<AchievementEventEntity> events;
  private final AchievementProgressTracker progressTracker;

  /**
   * Creates a new instance.
   *
   * @param achievement     The achievement.
   * @param events          The events.
   * @param progressTracker The progress tracker.
   */
  public AchievementAggregate(final AchievementEntity achievement,
                              final Collection<AchievementEventEntity> events,
                              final AchievementProgressTracker progressTracker)
  {
    this.achievement = requireNonNull(achievement, "The achievement may not be null");
    this.events = new ArrayList<>(requireNonNull(events, "The events may not be null"));
    this.progressTracker = requireNonNull(progressTracker, "The progress tracker may not be null");
  }

  /**
   * The aggregate root.
   *
   * @return The aggregate root.
   */
  public AchievementEntity aggregateRoot()
  {
    return achievement;
  }

  /**
   * The events associated with the aggregate root.
   *
   * @return The events.
   */
  public Collection<AchievementEventEntity> events()
  {
    return new ArrayList<>(events);
  }

  /**
   * Updates the events for the aggregate.
   *
   * @param events The events.
   */
  public void updateEvents(final Collection<AchievementEventEntity> events)
  {
    this.events.clear();
    this.events.addAll(events);
  }

  /**
   * Progresses the achievement based on the given event.
   *
   * @param event The event.
   */
  public void progress(final ToDoEvent event)
  {
    event.visit(progressTracker);

    if (isLocked() && progressTracker.isCompleted())
    {
      unlock();
    }
  }

  /**
   * Checks whether the achievement is locked.
   *
   * @return True if the achievement is locked, otherwise false.
   */
  private boolean isLocked()
  {
    return StateValueType.LOCKED.equals(achievement.state());
  }

  /**
   * Unlocks the achievement.
   */
  public void unlock()
  {
    final var update =
        builder().copyFrom(this).state(StateValueType.UNLOCKED).unlocked(LocalDateTime.now())
            .withEvents(new AchievementUnlockedEvent(LocalDateTime.now(), achievement.name()))
            .build();

    this.achievement = update.achievement;
    this.events.clear();
    this.events.addAll(update.events);
  }

  /**
   * The builder.
   *
   * @return The builder.
   */
  public static Builder builder()
  {
    return new Builder();
  }

  /**
   * A builder for the {@link AchievementAggregate}
   */
  public static class Builder
  {
    private IdentityValueObject identity;
    private AchievementValueType name;

    private StateValueType state;

    private UnlockedTimestampValueType unlocked;

    private AchievementProgressTracker progressTracker;

    private final Collection<AchievementEventEntity> events = new ArrayList<>();

    /**
     * Copies the data from the given aggregate.
     *
     * @param aggregate The aggregate.
     * @return The builder.
     */
    public Builder copyFrom(final AchievementAggregate aggregate)
    {
      this.identity = aggregate.achievement.identity();
      this.name = aggregate.achievement.name();
      this.state = aggregate.achievement.state();
      this.unlocked = aggregate.achievement.unlocked();
      this.events.addAll(aggregate.events);
      this.progressTracker = aggregate.progressTracker;

      return this;
    }

    /**
     * The identity of the achievement.
     *
     * @param identity The identity.
     * @return The builder.
     */
    public Builder identity(final String identity)
    {
      this.identity = new IdentityValueObject(identity);
      return this;
    }

    /**
     * Sets a random identity for the achievement.
     *
     * @return The builder.
     */
    public Builder randomIdentity()
    {
      this.identity = IdentityValueObject.random();
      return this;
    }

    /**
     * Sets the name of the achievement.
     *
     * @param name The name.
     * @return The builder.
     */
    public Builder name(final AchievementValueType name)
    {
      this.name = name;

      this.progressTracker = createProgressTrackerFor(name);

      return this;
    }

    /**
     * Creates the appropriate progress tracker for the given achievement.
     *
     * @param achievement The achievement.
     * @return The progress tracker.
     */
    private AchievementProgressTracker createProgressTrackerFor(
        final AchievementValueType achievement)
    {
      switch (achievement)
      {
        case JOURNEY_BEGINS ->
        {
          return new JourneyBeginsAchievementProgressTracker();
        }
        case DONEZO ->
        {
          return new DonezoAchievementProgressTracker();
        }
        case NEW_YEAR_NEW_ME ->
        {
          return new NewYearNewMeAchievementProgressTracker();
        }
        case IT_BURNS ->
        {
          return new ItBurnsAchievementProgressTracker();
        }
        case THIRD_TIMES_THE_CHARM ->
        {
          return new ThirdTimesTheCharmAchievementProgressTracker();
        }
        default -> throw new IllegalStateException("Unexpected value: " + achievement);
      }
    }

    /**
     * The state of the achievement.
     *
     * @param state The state.
     * @return The builder.
     */
    public Builder state(final StateValueType state)
    {
      this.state = state;
      return this;
    }

    /**
     * Sets the unlocked timestamp for the achievement.
     *
     * @param unlocked The unlocked timestamp.
     * @return The builder.
     */
    public Builder unlocked(final LocalDateTime unlocked)
    {
      this.unlocked = new UnlockedTimestampValueType(unlocked);
      return this;
    }

    /**
     * Adds the given events to the aggregate.
     *
     * @param events The events.
     * @return The builder.
     */
    public Builder withEvents(final AchievementEvent... events)
    {
      for (final AchievementEvent event : events)
      {
        this.events.add(new AchievementEventEntity(null, event));
      }
      return this;
    }

    /**
     * Builds the aggregate.
     *
     * @return The aggregate.
     */
    public AchievementAggregate build()
    {
      return new AchievementAggregate(new AchievementEntity(identity, name, state, unlocked),
          events, progressTracker);
    }
  }
}
