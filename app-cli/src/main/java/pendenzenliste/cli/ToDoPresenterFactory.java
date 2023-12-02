package pendenzenliste.cli;

import pendenzenliste.todos.boundary.out.*;

/**
 * A factory that can be used to create CLI presenters.
 */
public class ToDoPresenterFactory implements ToDoOutputBoundaryFactory {
    /**
     * {@inheritDoc}
     */
    @Override
    public CreateToDoOutputBoundary create() {
        return new CreateToDoPresenter(new CommandLineResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchToDoListOutputBoundary list() {
        return new FetchToDoListPresenter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchToDoOutputBoundary fetch() {
        return new FetchToDoPresenter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateToDoOutputBoundary update() {
        return new UpdateToDoPresenter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurgeToDoOutputBoundary purge() {
        throw new UnsupportedOperationException();
    }
}
