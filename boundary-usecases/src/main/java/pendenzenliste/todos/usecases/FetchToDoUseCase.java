package pendenzenliste.todos.usecases;

import jakarta.inject.Inject;
import pendenzenliste.todos.boundary.in.FetchToDoInputBoundary;
import pendenzenliste.todos.boundary.in.FetchToDoRequest;
import pendenzenliste.todos.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoResponse;
import pendenzenliste.todos.boundary.out.ToDoFetchedResponse;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.model.CompletedTimestampValueObject;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoAggregate;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * A use case that can be used to fetch a ToDo.
 */
public class FetchToDoUseCase implements FetchToDoInputBoundary {
    private final ToDoGateway gateway;
    private final FetchToDoOutputBoundary outputBoundary;

    /**
     * Creates a new instance.
     *
     * @param gateway The gateway that should be used by this instance.
     */
    @Inject
    public FetchToDoUseCase(final ToDoGateway gateway,
                            final FetchToDoOutputBoundary outputBoundary) {
        this.gateway = requireNonNull(gateway, "The gateway may not be null");
        this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final FetchToDoRequest request) {
        executeRequest(request).applyTo(outputBoundary);
    }

    /**
     * Executes the request.
     *
     * @param request The request that should be executed.
     * @return The response.
     */
    public FetchToDoResponse executeRequest(final FetchToDoRequest request) {
        try {
            final var id = new IdentityValueObject(request.id());

            final var todo = gateway.findById(id);

            return todo.map(mapToResponse())
                    .orElse(new FetchToDoFailedResponse("The ToDo does not exist"));
        } catch (final IllegalArgumentException e) {
            return new FetchToDoFailedResponse(e.getMessage());
        }

    }

    /**
     * Maps the given entity to a response.
     *
     * @return The mapped response.
     */
    private Function<ToDoAggregate, FetchToDoResponse> mapToResponse() {
        return todo -> new ToDoFetchedResponse(todo.aggregateRoot().identity().value(),
                todo.aggregateRoot().headline().value(),
                todo.aggregateRoot().description().value(),
                todo.aggregateRoot().created().value(),
                todo.aggregateRoot().lastModified().value(),
                Optional.ofNullable(todo.aggregateRoot().completed())
                        .map(CompletedTimestampValueObject::value)
                        .orElse(null),
                todo.aggregateRoot().state().name(),
                todo.capabilities().stream().map(Enum::name).toList());
    }
}
