package pendenzenliste.boundary.out;

/**
 * A response that can be used to represent a result to a failed list todos request.
 *
 * @param reason The reason for the failure.
 */
public record FetchToDoListFailedResponse(String reason) implements FetchToDoListResponse
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTo(final FetchToDoListOutputBoundary outputBoundary)
  {
    outputBoundary.handleFailedResponse(this);
  }
}
