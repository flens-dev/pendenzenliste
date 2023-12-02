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
        return new FetchToDoListPresenter(new CommandLineResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchToDoOutputBoundary fetch() {
        return new FetchToDoPresenter(new CommandLineResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateToDoOutputBoundary update() {
        return new UpdateToDoPresenter(new CommandLineResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurgeToDoOutputBoundary purge() {
        throw new UnsupportedOperationException();
    }
}
