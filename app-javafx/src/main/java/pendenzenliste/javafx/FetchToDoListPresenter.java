package pendenzenliste.javafx;

import pendenzenliste.todos.boundary.out.FetchToDoListFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchedToDoListResponse;
import pendenzenliste.todos.boundary.out.ToDoListResponseModel;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * A presenter that can be used to handle the results of a fetch todo list request.
 */
public class FetchToDoListPresenter implements FetchToDoListOutputBoundary {
    private static final System.Logger LOGGER = System.getLogger(FetchToDoListPresenter.class.getName());

    private final ToDoListViewModel viewModel;

    /**
     * Creates a new instance.
     *
     * @param viewModel The view model that should be used by this instance.
     */
    public FetchToDoListPresenter(final ToDoListViewModel viewModel) {
        this.viewModel = requireNonNull(viewModel, "The viewModel may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailedResponse(final FetchToDoListFailedResponse response) {
        //TODO: Show some kind of error message
        LOGGER.log(System.Logger.Level.ERROR, response.reason());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final FetchedToDoListResponse response) {
        viewModel.todos.setAll(response.todos().stream().map(mapToViewModel()).toList());
    }

    /**
     * Maps the given response model to a DTO.
     *
     * @return The mapping function.
     */
    private static Function<ToDoListResponseModel, ToDoListItemViewModel> mapToViewModel() {
        return todo -> {
            var viewModel = new ToDoListItemViewModel();

            viewModel.identity.set(todo.identity());
            viewModel.headline.set(todo.headline());
            viewModel.created.set(todo.created());
            viewModel.lastModified.set(todo.lastModified());
            viewModel.completed.set(todo.completed());
            viewModel.state.set(todo.state());

            return viewModel;
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDetached() {
        return false;
    }
}
