package pendenzenliste.cli;

import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;

/**
 * A factory that can be used to create CLI presenters.
 */
public class ToDoPresenterFactory implements ToDoOutputBoundaryFactory
{
  /**
   * {@inheritDoc}
   */
  @Override
  public CreateToDoOutputBoundary create()
  {
    return new CreateToDoPresenter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoListOutputBoundary list()
  {
    return new FetchToDoListPresenter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoOutputBoundary fetch()
  {
    return new FetchToDoPresenter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoOutputBoundary update()
  {
    return new UpdateToDoPresenter();
  }
}
