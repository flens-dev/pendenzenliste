package pendenzenliste.todos.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.boundary.in.CompleteToDoInputBoundary;
import pendenzenliste.todos.boundary.in.CreateToDoInputBoundary;
import pendenzenliste.todos.boundary.in.DeleteToDoInputBoundary;
import pendenzenliste.todos.boundary.in.FetchToDoInputBoundary;
import pendenzenliste.todos.boundary.in.FetchToDoListInputBoundary;
import pendenzenliste.todos.boundary.in.ReopenToDoInputBoundary;
import pendenzenliste.todos.boundary.in.SubscribeToDoListInputBoundary;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.todos.boundary.in.UpdateToDoInputBoundary;
import pendenzenliste.todos.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.todos.gateway.ToDoGatewayProvider;

/**
 * A factory that can be used to access ToDo specific use cases.
 */
public class ToDoUseCaseFactory implements ToDoInputBoundaryFactory
{
  private final ToDoGatewayProvider provider;
  private final ToDoOutputBoundaryFactory outputBoundaryFactory;
  private final EventBus eventBus;

  /**
   * Creates a new instance.
   *
   * @param provider              The todo gateway provider.
   * @param outputBoundaryFactory The output boundary factory.
   * @param eventBus              The event bus.
   */
  public ToDoUseCaseFactory(final ToDoGatewayProvider provider,
                            final ToDoOutputBoundaryFactory outputBoundaryFactory,
                            final EventBus eventBus)
  {

    this.provider = requireNonNull(provider, "The provider may not be null");
    this.outputBoundaryFactory =
        requireNonNull(outputBoundaryFactory, "The output boundary factory may not be null");
    this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CreateToDoInputBoundary create()
  {
    return new CreateToDoUseCase(provider.getInstance(), outputBoundaryFactory.create());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CompleteToDoInputBoundary complete()
  {
    return new CompleteToDoUseCase(provider.getInstance(), outputBoundaryFactory.update());
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
        eventBus);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DeleteToDoInputBoundary delete()
  {
    return new DeleteToDoUseCase(provider.getInstance(), outputBoundaryFactory.update(),
        eventBus);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ReopenToDoInputBoundary reopen()
  {
    return new ReopenToDoUseCase(provider.getInstance(), outputBoundaryFactory.update());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoInputBoundary update()
  {
    return new UpdateToDoUseCase(provider.getInstance(), outputBoundaryFactory.update());
  }
}
