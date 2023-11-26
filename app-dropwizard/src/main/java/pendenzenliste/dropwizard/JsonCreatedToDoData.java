package pendenzenliste.dropwizard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

/**
 * A JSON based DTO that represents a created todos data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCreatedToDoData {

    private final String id;

    /**
     * Creates a new instance.
     *
     * @param id The ID of the created todo.
     */
    @JsonCreator
    public JsonCreatedToDoData(final String id) {
        this.id = id;
    }


    /**
     * Retrieves the ID.
     *
     * @return The ID.
     */
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonCreatedToDoData that = (JsonCreatedToDoData) o;
        return Objects.equals(id, that.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
