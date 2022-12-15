package pendenzenliste.ports.out;

/**
 * A response that can be used to represent a result to a failed list todos request.
 *
 * @param reason The reason for the failure.
 */
public record ListToDosFailedResponse(String reason) implements ListToDosResponse
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTo(final ListToDosOutputBoundary outputBoundary)
  {
    outputBoundary.handleFailedResponse(this);
  }
}
