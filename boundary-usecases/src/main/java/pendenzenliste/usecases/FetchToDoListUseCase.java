package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;
import static pendenzenliste.usecases.ResponseModelMappingUtils.mapToResponseModel;

import pendenzenliste.boundary.in.FetchToDoListInputBoundary;
import pendenzenliste.boundary.in.FetchTodoListRequest;
import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoListResponse;
import pendenzenliste.boundary.out.FetchedToDoListResponse;
import pendenzenliste.gateway.ToDoGateway;

/**
 * A use case that can be used to fetch a list of todos
 */
public class FetchToDoListUseCase implements FetchToDoListInputBoundary
{
  private final ToDoGateway gateway;
  private final FetchToDoListOutputBoundary outputBoundary;

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway that should be used by this instance.
   */
  public FetchToDoListUseCase(final ToDoGateway gateway,
                              final FetchToDoListOutputBoundary outputBoundary)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
    this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final FetchTodoListRequest request)
  {
    executeRequest(request).applyTo(outputBoundary);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoListResponse executeRequest(final FetchTodoListRequest request)
  {
    return new FetchedToDoListResponse(gateway.fetchAll().map(mapToResponseModel()).toList());
  }
}