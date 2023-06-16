package pendenzenliste.discord4j;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.InteractionApplicationCommandCallbackSpec;
import pendenzenliste.todos.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoFetchedResponse;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class FetchToDoPresenter implements FetchToDoOutputBoundary {

    private final SlashCommandViewModel viewModel;
    private final ChatInputInteractionEvent event;

    /**
     * Creates a new instance.
     *
     * @param viewModel The view model.
     * @param event     The event may not be null.
     */
    public FetchToDoPresenter(final SlashCommandViewModel viewModel,
                              final ChatInputInteractionEvent event) {

        this.viewModel = requireNonNull(viewModel, "The view model may not be null");
        this.event = requireNonNull(event, "The event may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailedResponse(final FetchToDoFailedResponse response) {
        viewModel.response = event.reply(response.reason());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final ToDoFetchedResponse response) {
        viewModel.response =
                event.reply(InteractionApplicationCommandCallbackSpec.builder()
                        .embeds(List.of(EmbedCreateSpec.builder()
                                .addField("id", response.identity(), false)
                                .addField("headline", response.headline(), false)
                                .addField("description", response.description(), false)
                                .addField("state", response.state(), false)
                                .addField("created", response.created().toString(), false)
                                .build()))
                        .build());
    }
}
