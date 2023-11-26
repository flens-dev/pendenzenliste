package pendenzenliste.dropwizard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A data model for a json message used to create a todo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCreateToDoData {

    private final String headline;
    private final String description;

    /**
     * Creates a new instance.
     *
     * @param headline    The headline.
     * @param description The description.
     */
    @JsonCreator
    public JsonCreateToDoData(@JsonProperty("headline") final String headline,
                              @JsonProperty("description") final String description) {

        this.headline = headline;
        this.description = description;
    }

    /**
     * Retrieves the headline of the todo.
     *
     * @return The headline.
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * Retrieves the description of the todo.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }
}
