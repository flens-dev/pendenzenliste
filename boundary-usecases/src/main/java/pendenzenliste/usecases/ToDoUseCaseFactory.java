package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.in.CompleteToDoInputBoundary;
import pendenzenliste.boundary.in.CreateToDoInputBoundary;
import pendenzenliste.boundary.in.DeleteToDoInputBoundary;
import pendenzenliste.boundary.in.FetchToDoInputBoundary;
import pendenzenliste.boundary.in.FetchToDoListInputBoundary;
import pendenzenliste.boundary.in.ResetToDoInputBoundary;
import pendenzenliste.boundary.in.SubscribeToDoListInputBoundary;
import pendenzenliste.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.boundary.in.UpdateToDoInputBoundary;
import pendenzenliste.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.domain.todos.ToDoEventPublisher;
import pendenzenliste.domain.todos.ToDoEventSubscriptionTopic;
import pendenzenliste.gateway.ToDoGatewayProvider;

/**
 * A factory that can be used to access ToDo specific use cases.
 */
public class ToDoUseCaseFactory implements ToDoInputBoundaryFactory
{
  private final ToDoGatewayProvider provider;
  private final ToDoOutputBoundaryFactory outputBoundaryFactory;
  private final ToDoEventPublisher eventPublisher;
  private final ToDoEventSubscriptionTopic subscriptionTopic;

  /**
   * Creates a new instance.
   *
   * @param provider              The provider that should be used by this instance.
   * @param outputBoundaryFactory The output boundary that should be used by this instance.
   * @param eventPublisher        The event publisher that should be used by this instance.
   * @param subscriptionTopic     The event subscription topic that should be used by this instance.
   */
  public ToDoUseCaseFactory(final ToDoGatewayProvider provider,
                            final ToDoOutputBoundaryFactory outputBoundaryFactory,
                            final ToDoEventPublisher eventPublisher,
                            final ToDoEventSubscriptionTopic subscriptionTopic)
  {
    this.provider = requireNonNull(provider, "The provider may not be null");
    this.outputBoundaryFactory =
        requireNonNull(outputBoundaryFactory, "The output boundary factory may not be null");
    this.eventPublisher = requireNonNull(eventPublisher, "The event publisher may not be null");
    this.subscriptionTopic =
        requireNonNull(subscriptionTopic, "The subscription topic may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CreateToDoInputBoundary create()
  {
    return new CreateToDoUseCase(provider.getInstance(), outputBoundaryFactory.create(),
        eventPublisher);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CompleteToDoInputBoundary complete()
  {
    return new CompleteToDoUseCase(provider.getInstance(), outputBoundaryFactory.update(),
        eventPublisher);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoInputBoundary fetch()
  {
    return new FetchToDoUseCase(provider.getInstance(), outputBoundaryFactory.fetch());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoListInputBoundary list()
  {
    return new FetchToDoListUseCase(provider.getInstance(), outputBoundaryFactory.list());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SubscribeToDoListInputBoundary subscribe()
  {
    return new SubscribeToDoListUseCase(provider.getInstance(), outputBoundaryFactory.list(),
        subscriptionTopic);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DeleteToDoInputBoundary delete()
  {
    return new DeleteToDoUseCase(provider.getInstance(), outputBoundaryFactory.update(),
        eventPublisher);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResetToDoInputBoundary reset()
  {
    return new ResetToDoUseCase(provider.getInstance(), outputBoundaryFactory.update(),
        eventPublisher);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoInputBoundary update()
  {
    return new UpdateToDoUseCase(provider.getInstance(), outputBoundaryFactory.update(),
        eventPublisher);
  }
}
