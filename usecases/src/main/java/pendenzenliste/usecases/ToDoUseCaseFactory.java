package pendenzenliste.usecases;

import static java.util.Objects.requireNonNull;

import pendenzenliste.gateway.ToDoGatewayProvider;
import pendenzenliste.ports.in.CompleteToDoInputBoundary;
import pendenzenliste.ports.in.DeleteToDoInputBoundary;
import pendenzenliste.ports.in.FetchToDoInputBoundary;
import pendenzenliste.ports.in.ToDoInputBoundaryFactory;

/**
 * A factory that can be used to access ToDo specific use cases.
 */
public class ToDoUseCaseFactory implements ToDoInputBoundaryFactory
{
  private final ToDoGatewayProvider provider;

  /**
   * Creates a new instance.
   *
   * @param provider The provider that should be used by this instance.
   */
  public ToDoUseCaseFactory(final ToDoGatewayProvider provider)
  {
    this.provider = requireNonNull(provider, "The provider may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CompleteToDoInputBoundary complete()
  {
    return new CompleteToDoUseCase(provider.getInstance());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoInputBoundary fetch()
  {
    return new FetchToDoUseCase(provider.getInstance());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DeleteToDoInputBoundary delete()
  {
    return new DeleteToDoUseCase(provider.getInstance());
  }
}
