package pendenzenliste.ports.out;

public record ToDoUpdatedResponse() implements UpdateToDoResponse
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTo(final UpdateToDoOutputBoundary outputBoundary)
  {
    outputBoundary.handleSuccessfulResponse(this);
  }
}
