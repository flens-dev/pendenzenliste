package pendenzenliste.boundary.cucumber;

import pendenzenliste.todos.boundary.in.*;

public class ToDoRequestBuilder {

    private String id;

    private String headline;

    private String description;

    public ToDoRequestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public ToDoRequestBuilder headline(final String headline) {
        this.headline = headline;
        return this;
    }

    public ToDoRequestBuilder description(final String description) {
        this.description = description;
        return this;
    }

    public CompleteToDoRequest buildCompleteRequest() {
        return new CompleteToDoRequest(id);
    }

    public CreateToDoRequest buildCreateRequest() {
        return new CreateToDoRequest(headline, description);
    }

    public DeleteToDoRequest buildDeleteRequest() {
        return new DeleteToDoRequest(id);
    }

    public FetchToDoRequest fetchToDoRequest() {
        return new FetchToDoRequest(id);
    }

    public FetchTodoListRequest buildFetchListRequest() {
        return new FetchTodoListRequest();
    }

    public UpdateToDoRequest buildUpdateRequest() {
        return new UpdateToDoRequest(id, headline, description);
    }

    public ReopenToDoRequest buildReopenRequest() {
        return new ReopenToDoRequest(id);
    }
}
