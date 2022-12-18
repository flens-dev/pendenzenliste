package pendenzenliste.boundary.out;

/**
 * A response that can be used to represent a reason for not being able to fetch the requested ToDo.
 *
 * @param reason The reason for the failure.
 */
public record FetchToDoFailedResponse(String reason) implements FetchToDoResponse
{
  @Override
  public void applyTo(final FetchToDoOutputBoundary outputBoundary)
  {
    outputBoundary.handleFailedResponse(this);
  }
}
