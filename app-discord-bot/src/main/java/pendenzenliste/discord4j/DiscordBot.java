package pendenzenliste.discord4j;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pendenzenliste.todos.boundary.in.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * An application that can be used to run a discord bot.
 */
public final class DiscordBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordBot.class);

    private static final List<String> COMMANDS = List.of("complete-todo.json",
            "create-todo.json",
            "delete-todo.json",
            "fetch-todo.json",
            "list-todos.json",
            "reopen-todo.json");

    /**
     * The main entrypoint of the application.
     *
     * @param args The arguments passed to the application.
     */
    public static void main(final String[] args) throws Throwable {
        final var client = DiscordClient.create(System.getenv("DISCORD_KEY"));

        final var gateway = client.gateway().login().block();

        try {
            new GlobalCommandRegistrar(gateway.getRestClient()).registerCommands(COMMANDS);
        } catch (final Exception e) {
            LOGGER.error("Failed to register commands", e);
        }

        final var factory = ToDoInputBoundaryFactoryProvider.defaultProvider();

        gateway.on(ChatInputInteractionEvent.class, event -> handle(event, factory))
                .then(gateway.onDisconnect())
                .block();

    }

    /**
     * Handles the given event.
     *
     * @param event    The event.
     * @param provider The provider.
     * @return The response.
     */
    private static Mono<Void> handle(final ChatInputInteractionEvent event,
                                     final ToDoInputBoundaryFactoryProvider provider) {

        final var viewModel = new SlashCommandViewModel();

        final var factory = provider.getInstance(new ToDoPresenterFactory(viewModel, event));

        switch (event.getCommandName()) {
            case "complete-todo": {
                final var request = new CompleteToDoRequest(getStringOptionFrom("id", event));

                factory.complete().execute(request);

                return viewModel.response;
            }

            case "create-todo": {
                final var request = new CreateToDoRequest(
                        getStringOptionFrom("headline", event),
                        getStringOptionFrom("description", event));

                factory.create().execute(request);

                return viewModel.response;
            }

            case "delete-todo": {
                final var request = new DeleteToDoRequest(
                        getStringOptionFrom("id", event));

                factory.delete().execute(request);

                return viewModel.response;
            }

            case "fetch-todo": {
                final var request = new FetchToDoRequest(
                        getStringOptionFrom("id", event));

                factory.fetch().execute(request);

                return viewModel.response;
            }

            case "list-todos": {
                final var request = new FetchTodoListRequest();

                factory.list().execute(request);

                return viewModel.response;
            }

            case "reopen-todo": {
                final var request = new ReopenToDoRequest(
                        getStringOptionFrom("id", event));

                factory.reopen().execute(request);

                return viewModel.response;
            }

            default: {
                return event.reply("The command " + event.getCommandName() + " is not implemented!");
            }
        }
    }

    /**
     * Retrieves a string option from the given event.
     *
     * @param option The name of the option.
     * @param event  The event.
     * @return The string option.
     */
    private static String getStringOptionFrom(final String option, final ChatInputInteractionEvent event) {
        return event.getOption(option)
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .orElse("");
    }
}
