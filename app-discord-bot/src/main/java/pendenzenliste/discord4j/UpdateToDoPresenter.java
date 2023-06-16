package pendenzenliste.discord4j;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.InteractionApplicationCommandCallbackSpec;
import pendenzenliste.todos.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class UpdateToDoPresenter implements UpdateToDoOutputBoundary {

    private final SlashCommandViewModel viewModel;
    private final ChatInputInteractionEvent event;

    /**
     * Creates a new instance.
     *
     * @param viewModel The view model.
     * @param event     The event may not be null.
     */
    public UpdateToDoPresenter(final SlashCommandViewModel viewModel,
                               final ChatInputInteractionEvent event) {

        this.viewModel = requireNonNull(viewModel, "The view model may not be null");
        this.event = requireNonNull(event, "The event may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final ToDoUpdatedResponse response) {
        viewModel.response =
                event.reply(InteractionApplicationCommandCallbackSpec.builder()
                        .embeds(List.of(EmbedCreateSpec.builder().description("The ToDo has been updated").build()))
                        .build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailedResponse(final ToDoUpdateFailedResponse response) {
        viewModel.response = event.reply(response.reason());
    }
}
