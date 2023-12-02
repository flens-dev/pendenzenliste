package pendenzenliste.dropwizard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A data model for a json message used to create a todo.
 *
 * @param headline    The headline of the todo
 * @param description The description of the todo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record JsonCreateToDoData(@JsonProperty("headline") String headline,
                                 @JsonProperty("description") String description) {
}
