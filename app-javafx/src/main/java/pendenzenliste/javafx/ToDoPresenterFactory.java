package pendenzenliste.javafx;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;

/**
 * A factory that provides access to the various presenters used in the context of todos.
 */
public class ToDoPresenterFactory implements ToDoOutputBoundaryFactory
{
  private final ToDoListViewModel listViewModel;
  private final EditToDoViewModel editViewModel;

  /**
   * Creates a new instance.
   *
   * @param listViewModel The list view model that should be used by this instance.
   * @param editViewModel The edit view model that should be used by this instance.
   */
  public ToDoPresenterFactory(final ToDoListViewModel listViewModel,
                              final EditToDoViewModel editViewModel)
  {

    this.listViewModel = requireNonNull(listViewModel, "The list view model may not be null");
    this.editViewModel = requireNonNull(editViewModel, "The edit view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CreateToDoOutputBoundary create()
  {
    return new CreateToDoPresenter(editViewModel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoListOutputBoundary list()
  {
    return new FetchToDoListPresenter(listViewModel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoOutputBoundary fetch()
  {
    return new EditToDoPresenter(editViewModel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoOutputBoundary update()
  {
    return new UpdateToDoPresenter(editViewModel);
  }
}
