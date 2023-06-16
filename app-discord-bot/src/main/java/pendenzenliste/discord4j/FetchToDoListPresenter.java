package pendenzenliste.discord4j;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.InteractionApplicationCommandCallbackSpec;
import pendenzenliste.todos.boundary.out.FetchToDoListFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchedToDoListResponse;
import pendenzenliste.todos.boundary.out.ToDoListResponseModel;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * A presenter that can be used to handle the results of a fetch todo list request.
 */
public final class FetchToDoListPresenter implements FetchToDoListOutputBoundary {

    private final SlashCommandViewModel viewModel;
    private final ChatInputInteractionEvent event;

    /**
     * Creates a new instance.
     *
     * @param viewModel The view model.
     * @param event     The event may not be null.
     */
    public FetchToDoListPresenter(final SlashCommandViewModel viewModel,
                                  final ChatInputInteractionEvent event) {

        this.viewModel = requireNonNull(viewModel, "The view model may not be null");
        this.event = requireNonNull(event, "The event may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailedResponse(final FetchToDoListFailedResponse response) {
        viewModel.response = event.reply(response.reason());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final FetchedToDoListResponse response) {
        viewModel.response =
                event.reply(InteractionApplicationCommandCallbackSpec.builder()
                        .embeds(response.todos()
                                .stream()
                                .map(toEmbedSpec())
                                .toList())
                        .build());
    }

    /**
     * Maps the response model to its embed spec.
     *
     * @return The mapping function.
     */
    private static Function<ToDoListResponseModel, EmbedCreateSpec> toEmbedSpec() {
        return todo -> EmbedCreateSpec.builder()
                .addField("id", todo.identity(), false)
                .addField("headline", todo.headline(), false)
                .addField("description", todo.description(), false)
                .addField("state", todo.state(), false)
                .addField("created", todo.created().toString(), false)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDetached() {
        return false;
    }
}
