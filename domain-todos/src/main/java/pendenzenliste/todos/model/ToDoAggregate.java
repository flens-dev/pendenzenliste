package pendenzenliste.todos.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An aggregate that can be used to represent a todo.
 */
public class ToDoAggregate implements Serializable, HasCapabilities<ToDoCapabilityValueObject>
{
  private ToDoEntity todo;
  private final List<ToDoEventEntity> events;

  /**
   * Creates a new instance.
   *
   * @param todo   The todo that should be represented by this instance.
   * @param events The events that should be represented by this instance.
   */
  public ToDoAggregate(final ToDoEntity todo, final Collection<ToDoEventEntity> events)
  {

    this.todo = requireNonNull(todo, "The todo may not be null");
    this.events = new ArrayList<>(requireNonNull(events, "The events may not be null"));
  }

  /**
   * The aggregate root.
   *
   * @return The aggregate root.
   */
  public ToDoEntity aggregateRoot()
  {
    return todo;
  }

  /**
   * The events.
   *
   * @return The events.
   */
  public List<ToDoEventEntity> events()
  {
    return new ArrayList<>(events);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<ToDoCapabilityValueObject> capabilities()
  {
    final Collection<ToDoCapabilityValueObject> capabilities = new ArrayList<>();

    if (ToDoStateValueObject.OPEN.equals(aggregateRoot().state()))
    {
      capabilities.add(ToDoCapabilityValueObject.COMPLETE);
      capabilities.add(ToDoCapabilityValueObject.UPDATE);
      capabilities.add(ToDoCapabilityValueObject.DELETE);
    }

    if (ToDoStateValueObject.COMPLETED.equals(aggregateRoot().state()))
    {
      capabilities.add(ToDoCapabilityValueObject.REOPEN);
    }

    return capabilities;
  }

  /**
   * Completes the todo.
   */
  public void complete()
  {
    todo = new ToDoEntity(aggregateRoot().identity(), aggregateRoot().headline(),
        aggregateRoot().description(), aggregateRoot().created(),
        LastModifiedTimestampValueObject.now(), CompletedTimestampValueObject.now(),
        ToDoStateValueObject.COMPLETED);

    events.add(new ToDoEventEntity(null,
        new ToDoCompletedEvent(LocalDateTime.now(), aggregateRoot().identity())));
  }

  /**
   * Reopens a previously closed ToDo.
   */
  public void reopen()
  {
    todo = new ToDoEntity(aggregateRoot().identity(), aggregateRoot().headline(),
        aggregateRoot().description(), aggregateRoot().created(),
        LastModifiedTimestampValueObject.now(), null, ToDoStateValueObject.OPEN);

    events.add(new ToDoEventEntity(null,
        new ToDoReopenedEvent(LocalDateTime.now(), aggregateRoot().identity())));
  }

  /**
   * Updates the todo with the given data.
   *
   * @param headline    The headline.
   * @param description The description.
   */
  public void update(final HeadlineValueObject headline, final DescriptionValueObject description)
  {
    todo =
        new ToDoEntity(aggregateRoot().identity(), headline, description, aggregateRoot().created(),
            LastModifiedTimestampValueObject.now(), aggregateRoot().completed(),
            aggregateRoot().state());


    events.add(new ToDoEventEntity(null,
        new ToDoUpdatedEvent(LocalDateTime.now(), aggregateRoot().identity())));
  }

  /**
   * Replaces the old event with the new event.
   *
   * @param oldEvent The old event.
   * @param newEvent The new event.
   */
  public void replaceEvent(final ToDoEventEntity oldEvent, final ToDoEventEntity newEvent)
  {
    final int index = events.indexOf(oldEvent);

    events.set(index, newEvent);
  }

  /**
   * Creates a builder.
   *
   * @return The builder.
   */
  public static Builder builder()
  {
    return new Builder();
  }

  /**
   * Appends the given event.
   *
   * @param event The event that should be appended.
   */
  public void appendEvent(final ToDoEvent event)
  {
    events.add(new ToDoEventEntity(null, event));
  }

  /**
   * A builder for the todo aggregate.
   */
  public static class Builder
  {
    private IdentityValueObject identity;
    private HeadlineValueObject headline;

    private DescriptionValueObject description;

    private CreatedTimestampValueObject created;

    private LastModifiedTimestampValueObject lastModified;
    private CompletedTimestampValueObject completed;
    private ToDoStateValueObject state;

    private final Collection<ToDoEventEntity> events = new ArrayList<>();

    /**
     * Sets the identity.
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
     * Sets a random identity.
     *
     * @return The builder.
     */
    public Builder randomIdentity()
    {
      this.identity = IdentityValueObject.random();
      return this;
    }

    /**
     * Sets the headline.
     *
     * @param headline The headline.
     * @return The builder.
     */
    public Builder headline(final String headline)
    {
      this.headline = new HeadlineValueObject(headline);
      return this;
    }

    /**
     * Sets the description.
     *
     * @param description The description.
     * @return The builder.
     */
    public Builder description(final String description)
    {
      this.description = new DescriptionValueObject(description);
      return this;
    }

    /**
     * Sets the created timestamp.
     *
     * @param created The created timestamp.
     * @return The builder.
     */
    public Builder created(final LocalDateTime created)
    {
      this.created = new CreatedTimestampValueObject(created);
      return this;
    }

    /**
     * Sets the last modified timestamp.
     *
     * @param lastModified The last modified timestamp.
     * @return The builder.
     */
    public Builder lastModified(final LocalDateTime lastModified)
    {
      this.lastModified = new LastModifiedTimestampValueObject(lastModified);
      return this;
    }

    /**
     * Sets the completed timestamp.
     *
     * @param completed The completed timestamp.
     * @return The builder.
     */
    public Builder completed(final LocalDateTime completed)
    {
      if (completed == null)
      {
        this.completed = null;
      } else
      {
        this.completed = new CompletedTimestampValueObject(completed);
      }

      return this;
    }

    /**
     * Sets the state.
     *
     * @param state The state.
     * @return The builder.
     */
    public Builder state(final ToDoStateValueObject state)
    {
      this.state = state;
      return this;
    }

    /**
     * Adds the given events.
     *
     * @param events The events.
     * @return The builder.
     */
    public Builder withEvents(final ToDoEvent... events)
    {
      Arrays.stream(events).map(event -> new ToDoEventEntity(null, event))
          .forEach(this.events::add);

      return this;
    }

    /**
     * Builds the aggregate.
     *
     * @return The aggregate.
     */
    public ToDoAggregate build()
    {
      return new ToDoAggregate(
          new ToDoEntity(identity, headline, description, created, lastModified, completed, state),
          events);
    }
  }
}
