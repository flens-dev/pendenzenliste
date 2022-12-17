package pendenzenliste.vaadin;

import static java.util.Objects.requireNonNull;

import pendenzenliste.ports.out.CreateToDoOutputBoundary;
import pendenzenliste.ports.out.FetchToDoListOutputBoundary;
import pendenzenliste.ports.out.FetchToDoOutputBoundary;
import pendenzenliste.ports.out.ToDoOutputBoundaryFactory;
import pendenzenliste.ports.out.UpdateToDoOutputBoundary;

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
    return null;
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
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoOutputBoundary update()
  {
    return null;
  }
}
