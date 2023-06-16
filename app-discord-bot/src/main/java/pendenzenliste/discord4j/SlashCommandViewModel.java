package pendenzenliste.discord4j;

import reactor.core.publisher.Mono;

/**
 * A view modeln that can be used to represent the results of a slash command.
 */
public class SlashCommandViewModel {

    public Mono<Void> response;
}
