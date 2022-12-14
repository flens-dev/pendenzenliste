package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.ports.in.FetchToDoInputBoundary;
import pendenzenliste.ports.in.FetchToDoRequest;
import pendenzenliste.ports.out.FetchToDoFailedResponse;
import pendenzenliste.ports.out.FetchToDoResponse;

/**
 * A use case that can be used to fetch a ToDo.
 */
public class FetchToDoUseCase implements FetchToDoInputBoundary
{
  private final ToDoGateway gateway;

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway that should be used by this instance.
   */
  public FetchToDoUseCase(final ToDoGateway gateway)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoResponse execute(final FetchToDoRequest request)
  {
    return new FetchToDoFailedResponse("Not implemented");
  }
}
