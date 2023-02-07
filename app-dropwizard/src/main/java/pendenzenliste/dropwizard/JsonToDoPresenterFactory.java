package pendenzenliste.dropwizard;

import static java.util.Objects.requireNonNull;

import pendenzenliste.todos.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;

/**
 * A factory that can be used to access JSON based todo output boundaries.
 */
public class JsonToDoPresenterFactory implements ToDoOutputBoundaryFactory
{
  private final HttpResponseViewModel viewModel;

  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be used by this instance.
   */
  public JsonToDoPresenterFactory(final HttpResponseViewModel viewModel)
  {
    this.viewModel = requireNonNull(viewModel, "The view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CreateToDoOutputBoundary create()
  {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoListOutputBoundary list()
  {
    return new JsonFetchToDoListPresenter(viewModel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FetchToDoOutputBoundary fetch()
  {
    return new JsonFetchToDoPresenter(viewModel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UpdateToDoOutputBoundary update()
  {
    throw new UnsupportedOperationException();
  }
}
