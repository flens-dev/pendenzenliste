package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;
import static pendenzenliste.usecases.ResponseModelMappingUtils.mapToResponseModel;

import pendenzenliste.boundary.in.SubscribeToDoListInputBoundary;
import pendenzenliste.boundary.in.SubscribeToDoListRequest;
import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoListResponse;
import pendenzenliste.boundary.out.FetchedToDoListResponse;
import pendenzenliste.domain.ToDoEvent;
import pendenzenliste.domain.ToDoEventSubscriber;
import pendenzenliste.domain.ToDoEventSubscriptionTopic;
import pendenzenliste.gateway.ToDoGateway;

/**
 * A use case that can be used to subscribe to a list of todos.
 */
public class SubscribeToDoListUseCase implements SubscribeToDoListInputBoundary
{
  private final ToDoGateway gateway;
  private final FetchToDoListOutputBoundary outputBoundary;
  private final ToDoEventSubscriptionTopic eventTopic;

  /**
   * Creates a new instance.
   *
   * @param gateway        The gateway that should be used by this instance.
   * @param outputBoundary The output boundary that should be used by this instance.
   * @param eventTopic     The event topic that should be used by this instance.
   */
  public SubscribeToDoListUseCase(final ToDoGateway gateway,
                                  final FetchToDoListOutputBoundary outputBoundary,
                                  final ToDoEventSubscriptionTopic eventTopic)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
    this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
    this.eventTopic = requireNonNull(eventTopic, "The event topic may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final SubscribeToDoListRequest request)
  {
    final var subscriber = new ToDoEventSubscriber()
    {

      @Override
      public void next(final ToDoEvent event)
      {
        if (outputBoundary.isDetached())
        {
          eventTopic.unsubscribe(this);
        } else
        {
          fetchList();
        }
      }
    };

    eventTopic.subscribe(subscriber);

    fetchList();
  }

  /**
   * Fetches the list of todos.
   */
  private void fetchList()
  {
    final var todos = gateway.fetchAll().map(mapToResponseModel()).toList();
    final var response = new FetchedToDoListResponse(todos);

    response.applyTo(outputBoundary);
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
