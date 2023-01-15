package pendenzenliste.vaadin;

import static java.util.Objects.requireNonNull;

import pendenzenliste.todos.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;

/**
 * A factory that can be used to create presenter instances.
 */
public class ToDoPresenterFactory implements ToDoOutputBoundaryFactory
{
  private final ToDoListViewModel viewModel;

  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be used by the presenters.
   */
  public ToDoPresenterFactory(final ToDoListViewModel viewModel)
  {
    this.viewModel = requireNonNull(viewModel, "The view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CreateToDoOutputBoundary create()
  {
    return new CreateToDoPresenter(viewModel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoListOutputBoundary list()
  {
    return new FetchToDoListPresenter(viewModel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoOutputBoundary fetch()
  {
    return new EditToDoPresenter(viewModel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoOutputBoundary update()
  {
    return new UpdateToDoPresenter(viewModel);
  }
}
