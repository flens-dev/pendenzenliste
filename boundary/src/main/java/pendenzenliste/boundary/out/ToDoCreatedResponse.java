package pendenzenliste.boundary.out;

/**
 * A response that can be used to represent a result for a successful create todo request.
 *
 * @param identity The identity.
 */
public record ToDoCreatedResponse(String identity) implements CreateToDoResponse
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTo(final CreateToDoOutputBoundary outputBoundary)
  {
    outputBoundary.handleSuccessfulResponse(this);
  }
}
