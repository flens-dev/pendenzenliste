package pendenzenliste.domain;

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
                         CompletedTimestampValueObject completed, ToDoState state)
    implements Entity<IdentityValueObject>, HasCapabilities<ToDoCapability>
{
  /**
   * Completes the todo.
   */
  public ToDoEntity complete()
  {
    return new ToDoEntity(identity, headline, description, created,
        LastModifiedTimestampValueObject.now(), CompletedTimestampValueObject.now(),
        ToDoState.DONE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<ToDoCapability> capabilities()
  {
    final Collection<ToDoCapability> capabilities = new ArrayList<>();

    if (ToDoState.OPEN.equals(state))
    {
      capabilities.add(ToDoCapability.COMPLETE);
      capabilities.add(ToDoCapability.UPDATE);
    }

    if (ToDoState.DONE.equals(state))
    {
      capabilities.add(ToDoCapability.RESET);
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
        LastModifiedTimestampValueObject.now(), null, ToDoState.OPEN);
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
}
