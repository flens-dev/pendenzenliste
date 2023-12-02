package pendenzenliste.dropwizard;

import pendenzenliste.todos.boundary.out.*;

import static java.util.Objects.requireNonNull;

/**
 * A factory that can be used to access JSON based todo output boundaries.
 */
public class JsonToDoPresenterFactory implements ToDoOutputBoundaryFactory {
    private final HttpResponseViewModel viewModel;

    /**
     * Creates a new instance.
     *
     * @param viewModel The view model that should be used by this instance.
     */
    public JsonToDoPresenterFactory(final HttpResponseViewModel viewModel) {
        this.viewModel = requireNonNull(viewModel, "The view model may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateToDoOutputBoundary create() {
        return new JsonCreateToDoPresenter(viewModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchToDoListOutputBoundary list() {
        return new JsonFetchToDoListPresenter(viewModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchToDoOutputBoundary fetch() {
        return new JsonFetchToDoPresenter(viewModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateToDoOutputBoundary update() {
        return new JsonUpdateToDoPresenter(viewModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurgeToDoOutputBoundary purge() {
        throw new UnsupportedOperationException();
    }
}
