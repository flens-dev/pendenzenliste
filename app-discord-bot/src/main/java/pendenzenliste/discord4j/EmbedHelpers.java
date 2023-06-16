package pendenzenliste.discord4j;

import discord4j.core.spec.EmbedCreateSpec;

/**
 * This class contains some helper functions that are used to generate discord embed specs.
 */
public class EmbedHelpers {

    /**
     * Creates a new update message.
     *
     * @param id      The ID of the todo.
     * @param message The message.
     * @return The create spec.
     */
    public static EmbedCreateSpec updateMessage(final String id,
                                                final String message) {
        return EmbedCreateSpec.builder()
                .addField("Message", message, false)
                .addField("id", id, false)
                .description(message)
                .build();
    }
}
