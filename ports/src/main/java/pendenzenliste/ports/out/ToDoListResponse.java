package pendenzenliste.ports.out;

import java.util.Collection;

/**
 * A response that can be used to represent a list of todos.
 *
 * @param todos The todos.
 */
public record ToDoListResponse(Collection<ToDoListResponseModel> todos) implements ListToDosResponse
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTo(final ListToDosOutputBoundary outputBoundary)
  {
    outputBoundary.handleSuccessfulResponse(this);
  }
}
