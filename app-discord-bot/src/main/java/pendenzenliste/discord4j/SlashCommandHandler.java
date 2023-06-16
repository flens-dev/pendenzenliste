package pendenzenliste.discord4j;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.InteractionCallbackSpec;
import discord4j.core.spec.InteractionFollowupCreateMono;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactoryProvider;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class SlashCommandHandler implements Function<ApplicationCommandInteractionEvent, Mono<Message>> {

    private final ToDoInputBoundaryFactoryProvider inputBoundaries;

    /**
     * Creates a new instance.
     *
     * @param inputBoundaries The input boundaries.
     */
    public SlashCommandHandler(final ToDoInputBoundaryFactoryProvider inputBoundaries) {

        this.inputBoundaries =
                requireNonNull(inputBoundaries, "The input boundaries may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Message> apply(final ApplicationCommandInteractionEvent event) {

        switch (event.getCommandName()) {
            case "create-todo": {
                event.reply("Dong");
                return event.deferReply(InteractionCallbackSpec.create().withEphemeral(true))
                        .then(createTodo(event));
            }

            default: {
                return event.deferReply().then(event.createFollowup("Unhandled command!"));
            }
        }
    }

    public InteractionFollowupCreateMono createTodo(final ApplicationCommandInteractionEvent event) {
        return event.createFollowup("Not implemented yet");
    }
}
