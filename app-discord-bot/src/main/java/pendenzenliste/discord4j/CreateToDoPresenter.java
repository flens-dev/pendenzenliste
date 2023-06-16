package pendenzenliste.discord4j;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.spec.InteractionApplicationCommandCallbackSpec;
import pendenzenliste.todos.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoCreatedResponse;
import pendenzenliste.todos.boundary.out.ToDoCreationFailedResponse;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A presenter that can be used to handle responses to a create todo request.
 */
public final class CreateToDoPresenter implements CreateToDoOutputBoundary {

    private final SlashCommandViewModel viewModel;
    private final ChatInputInteractionEvent event;

    /**
     * Creates a new instance.
     *
     * @param viewModel The view model.
     * @param event     The event may not be null.
     */
    public CreateToDoPresenter(final SlashCommandViewModel viewModel,
                               final ChatInputInteractionEvent event) {

        this.viewModel = requireNonNull(viewModel, "The view model may not be null");
        this.event = requireNonNull(event, "The event may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final ToDoCreatedResponse response) {
        viewModel.response =
                event.reply(InteractionApplicationCommandCallbackSpec.builder()
                        .embeds(List.of(EmbedHelpers.updateMessage(response.identity(),
                                "The ToDo has been created")))
                        .build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailedResponse(final ToDoCreationFailedResponse response) {
        viewModel.response = event.reply(response.reason());
    }
}
