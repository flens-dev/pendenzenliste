package pendenzenliste.domain.todos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An entity that can be used to represent a ToDo.
 *
 * @param identity     The identity.
 * @param headline     The headline.
 * @param description  The description.
 * @param created      The created timestamp.
 * @param lastModified The last modified timestamp.
 * @param completed    The completed timestamp.
 * @param state        The state of the todo.
 */
public record ToDoEntity(IdentityValueObject identity, HeadlineValueObject headline,
                         DescriptionValueObject description, CreatedTimestampValueObject created,
                         LastModifiedTimestampValueObject lastModified,
                         CompletedTimestampValueObject completed, ToDoStateValueObject state)
    implements Entity<IdentityValueObject>, HasCapabilities<ToDoCapabilityValueObject>, Serializable
{
  /**
   * Completes the todo.
   */
  public ToDoEntity complete()
  {
    return new ToDoEntity(identity, headline, description, created,
        LastModifiedTimestampValueObject.now(), CompletedTimestampValueObject.now(),
        ToDoStateValueObject.DONE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<ToDoCapabilityValueObject> capabilities()
  {
    final Collection<ToDoCapabilityValueObject> capabilities = new ArrayList<>();

    if (ToDoStateValueObject.OPEN.equals(state))
    {
      capabilities.add(ToDoCapabilityValueObject.COMPLETE);
      capabilities.add(ToDoCapabilityValueObject.UPDATE);
      capabilities.add(ToDoCapabilityValueObject.DELETE);
    }

    if (ToDoStateValueObject.DONE.equals(state))
    {
      capabilities.add(ToDoCapabilityValueObject.RESET);
    }

    return capabilities;
  }

  /**
   * Resets a previously closed ToDo.
   *
   * @return The reset ToDo.
   */
  public ToDoEntity reset()
  {
    return new ToDoEntity(identity, headline, description, created,
        LastModifiedTimestampValueObject.now(), null, ToDoStateValueObject.OPEN);
  }

  /**
   * Updates the todo with the given data.
   *
   * @param headline    The headline.
   * @param description The description.
   * @return The updated todo.
   */
  public ToDoEntity update(final HeadlineValueObject headline,
                           final DescriptionValueObject description)
  {
    return new ToDoEntity(identity, headline, description, created,
        LastModifiedTimestampValueObject.now(), completed, state);
  }

  /**
   * Creates a new open ToDo.
   *
   * @param headline    The headline.
   * @param description The description.
   * @return The open todo.
   */
  public static ToDoEntity createOpenToDo(final HeadlineValueObject headline,
                                          final DescriptionValueObject description)
  {
    return new ToDoEntity(IdentityValueObject.random(), headline, description,
        CreatedTimestampValueObject.now(), LastModifiedTimestampValueObject.now(), null,
        ToDoStateValueObject.OPEN);
  }
}
