package pendenzenliste.boundary.out;

import java.util.Collection;

/**
 * A response that can be used to represent a list of todos.
 *
 * @param todos The todos.
 */
public record FetchedToDoListResponse(Collection<ToDoListResponseModel> todos) implements
    FetchToDoListResponse
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTo(final FetchToDoListOutputBoundary outputBoundary)
  {
    outputBoundary.handleSuccessfulResponse(this);
  }
}
