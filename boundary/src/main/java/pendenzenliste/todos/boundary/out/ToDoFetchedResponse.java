package pendenzenliste.todos.boundary.out;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * A response that can be used to represent a fetched ToDo.
 */
public record ToDoFetchedResponse(String identity, String headline, String description,
                                  LocalDateTime created, LocalDateTime lastModified,
                                  LocalDateTime completed, String state,
                                  Collection<String> capabilities)
    implements FetchToDoResponse
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTo(final FetchToDoOutputBoundary outputBoundary)
  {
    outputBoundary.handleSuccessfulResponse(this);
  }
}
