package pendenzenliste.dropwizard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A JSON based DTO that represents a created todos data.
 *
 * @param id The ID of the created todo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record JsonCreatedToDoData(@JsonProperty("id") String id) {
}
