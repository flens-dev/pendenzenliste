package pendenzenliste.discord4j;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoOutputBoundaryFactory;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;

import static java.util.Objects.requireNonNull;

/**
 * A factory that can be used to create the various todo presenters.
 */
public final class ToDoPresenterFactory implements ToDoOutputBoundaryFactory {

    private final SlashCommandViewModel viewModel;
    private final ChatInputInteractionEvent event;

    /**
     * Creates a new instance.
     *
     * @param viewModel The view model that should be manipulated by the presenter.
     * @param event     The event that the presenter replies to.
     */
    public ToDoPresenterFactory(final SlashCommandViewModel viewModel,
                                final ChatInputInteractionEvent event) {

        this.viewModel = requireNonNull(viewModel, "The view model may not be null");
        this.event = requireNonNull(event, "The event may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateToDoPresenter create() {
        return new CreateToDoPresenter(viewModel, event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchToDoListOutputBoundary list() {
        return new FetchToDoListPresenter(viewModel, event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchToDoOutputBoundary fetch() {
        return new FetchToDoPresenter(viewModel, event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateToDoOutputBoundary update() {
        return new UpdateToDoPresenter(viewModel, event);
    }
}
