package pendenzenliste.todos.boundary.out;

/**
 * A response that can be used to represent a failed ToDo update.
 *
 * @param reason The reason for the failure.
 */
public record ToDoUpdateFailedResponse(String reason) implements UpdateToDoResponse
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTo(final UpdateToDoOutputBoundary outputBoundary)
  {
    outputBoundary.handleFailedResponse(this);
  }
}
