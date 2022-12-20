package pendenzenliste.usecases;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;
import static pendenzenliste.usecases.ResponseModelMappingUtils.mapToResponseModel;

import io.reactivex.rxjava3.core.Observable;
import pendenzenliste.boundary.in.SubscribeToDoListInputBoundary;
import pendenzenliste.boundary.in.SubscribeToDoListRequest;
import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoListResponse;
import pendenzenliste.boundary.out.FetchedToDoListResponse;
import pendenzenliste.gateway.ToDoGateway;

/**
 * A use case that can be used to subscribe to a list of todos.
 */
public class SubscribeToDoListUseCase implements SubscribeToDoListInputBoundary
{
  private final ToDoGateway gateway;
  private final FetchToDoListOutputBoundary outputBoundary;

  /**
   * Creates a new instance.
   *
   * @param gateway        The gateway that should be used by this instance.
   * @param outputBoundary The output boundary that should be used by this instance.
   */
  public SubscribeToDoListUseCase(final ToDoGateway gateway,
                                  final FetchToDoListOutputBoundary outputBoundary)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
    this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final SubscribeToDoListRequest request)
  {
    Observable.interval(0, 1, TimeUnit.SECONDS)
        .map(l -> gateway.fetchAll().map(mapToResponseModel()).toList())
        .map(FetchedToDoListResponse::new)
        .distinctUntilChanged()
        .takeUntil(r -> {
          return outputBoundary.isDetached();
        })
        .subscribe(response -> response.applyTo(outputBoundary));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoListResponse executeRequest(final SubscribeToDoListRequest request)
  {
    throw new IllegalStateException("Not implemented");
  }
}