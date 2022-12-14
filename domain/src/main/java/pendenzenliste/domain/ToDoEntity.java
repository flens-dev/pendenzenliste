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
public record ToDoEntity(ToDoIdentityValueObject identity, HeadlineValueObject headline,
                         DescriptionValueObject description, CreatedTimestampValueObject created,
                         LastModifiedTimestampValueObject lastModified,
                         CompletedTimestampValueObject completed, ToDoState state)
    implements Entity<ToDoIdentityValueObject>, HasCapabilities<ToDoCapability>
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
}
