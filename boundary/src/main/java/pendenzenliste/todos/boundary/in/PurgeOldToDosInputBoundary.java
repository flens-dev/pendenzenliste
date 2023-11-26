package pendenzenliste.todos.boundary.in;

import pendenzenliste.boundary.in.InputBoundary;
import pendenzenliste.todos.boundary.out.PurgeToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.PurgeToDosResponse;

/**
 * An input boundary that can be used to purge old todos.
 */
public interface PurgeOldToDosInputBoundary
        extends InputBoundary<PurgeOldToDosRequest, PurgeToDosResponse, PurgeToDoOutputBoundary> {
}
