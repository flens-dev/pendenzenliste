package pendenzenliste.todos.boundary.out;

import pendenzenliste.boundary.out.OutputBoundary;

/**
 * An output boundary that can be used to handle the results of a purge todo use case.
 */
public interface PurgeToDoOutputBoundary extends OutputBoundary<PurgeToDoOutputBoundary, PurgeToDosResponse> {
    /**
     * Handles a successful response.
     *
     * @param response The response.
     */
    void handleSuccessfulResponse(PurgedToDosResponse response);
}
