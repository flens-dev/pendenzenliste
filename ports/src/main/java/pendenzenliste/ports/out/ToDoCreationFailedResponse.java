package pendenzenliste.ports.out;

/**
 * A response that can be used to create the result of a failed todo creation.
 *
 * @param reason The reason for the failure.
 */
public record ToDoCreationFailedResponse(String reason) implements CreateToDoResponse
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTo(final CreateToDoOutputBoundary outputBoundary)
  {
    outputBoundary.handleFailedResponse(this);
  }
}
