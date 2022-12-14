package pendenzenliste.ports.out;

import java.time.LocalDateTime;

/**
 * A response that can be used to represent a fetched ToDo.
 */
public record ToDoFetchedResponse(String identity, String headline, String description,
                                  LocalDateTime created, LocalDateTime lastModified)
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
