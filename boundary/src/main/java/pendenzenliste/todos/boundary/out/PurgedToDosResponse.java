package pendenzenliste.todos.boundary.out;

import java.util.Collection;

/**
 * A response that can be used to represent a list of deleted todo ids.
 *
 * @param deletedToDoIds The deleted todo IDs.
 */
public record PurgedToDosResponse(Collection<String> deletedToDoIds) implements PurgeToDosResponse {
    @Override
    public void applyTo(final PurgeToDoOutputBoundary out) {
        out.handleSuccessfulResponse(this);
    }
}
