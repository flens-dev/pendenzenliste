package pendenzenliste.boundary.cucumber;

import pendenzenliste.todos.boundary.out.*;

public class CapturingOutputBoundaryFactory implements ToDoOutputBoundaryFactory {

    public FetchToDoResponse fetchResponse;

    public UpdateToDoResponse updateResponse;

    public CreateToDoResponse createResponse;

    public FetchToDoListResponse fetchListResponse;

    @Override
    public CreateToDoOutputBoundary create() {
        return new CreateToDoOutputBoundary() {
            @Override
            public void handleSuccessfulResponse(final ToDoCreatedResponse response) {
                createResponse = response;
            }

            @Override
            public void handleFailedResponse(final ToDoCreationFailedResponse response) {
                createResponse = response;
            }
        };
    }

    @Override
    public FetchToDoListOutputBoundary list() {
        return new FetchToDoListOutputBoundary() {
            @Override
            public void handleFailedResponse(FetchToDoListFailedResponse response) {
                fetchListResponse = response;
            }

            @Override
            public void handleSuccessfulResponse(FetchedToDoListResponse response) {
                fetchListResponse = response;
            }

            @Override
            public boolean isDetached() {
                return false;
            }
        };
    }

    @Override
    public FetchToDoOutputBoundary fetch() {
        return new FetchToDoOutputBoundary() {
            @Override
            public void handleFailedResponse(final FetchToDoFailedResponse response) {
                fetchResponse = response;
            }

            @Override
            public void handleSuccessfulResponse(final ToDoFetchedResponse response) {
                fetchResponse = response;
            }
        };
    }

    @Override
    public UpdateToDoOutputBoundary update() {
        return new UpdateToDoOutputBoundary() {
            @Override
            public void handleSuccessfulResponse(final ToDoUpdatedResponse response) {
                updateResponse = response;
            }

            @Override
            public void handleFailedResponse(final ToDoUpdateFailedResponse response) {
                updateResponse = response;
            }
        };
    }
}
