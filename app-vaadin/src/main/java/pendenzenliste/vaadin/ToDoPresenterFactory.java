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

  /**
   * Creates a new instance.
   *
   * @param view The view that should be updated by the presenters.
   */
  public ToDoPresenterFactory(final ToDoView view)
  {
    this.view = requireNonNull(view, "The view may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CreateToDoOutputBoundary create()
  {
    return new CreateToDoPresenter(view);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoListOutputBoundary list()
  {
    return new FetchToDoListPresenter(view);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoOutputBoundary fetch()
  {
    return new EditToDoPresenter(view);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoOutputBoundary update()
  {
    return new UpdateToDoPresenter(view);
  }
}
