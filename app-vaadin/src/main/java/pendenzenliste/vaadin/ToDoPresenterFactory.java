package pendenzenliste.vaadin;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;

/**
 * A factory that can be used to create presenter instances.
 */
public class ToDoPresenterFactory implements ToDoOutputBoundaryFactory
{
  private final ToDoView view;
  private final ToDoListViewModel viewModel;

  /**
   * Creates a new instance.
   *
   * @param view      The view that should be updated by the presenters.
   * @param viewModel
   */
  public ToDoPresenterFactory(final ToDoView view,
                              final ToDoListViewModel viewModel)
  {
    this.view = requireNonNull(view, "The view may not be null");
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
    return new FetchToDoListPresenter(view, viewModel);
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
