package pendenzenliste.todos.usecases;

import pendenzenliste.todos.boundary.in.PurgeOldToDosInputBoundary;
import pendenzenliste.todos.boundary.in.PurgeOldToDosRequest;
import pendenzenliste.todos.boundary.out.PurgeToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.PurgedToDosResponse;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoCapabilityValueObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * A use case that can be used to purge old todos.
 */
public class PurgeOldToDosUseCase implements PurgeOldToDosInputBoundary {

    private final ToDoGateway gateway;
    private final PurgeToDoOutputBoundary outputBoundary;

    /**
     * Creates a new instance.
     *
     * @param gateway        The gateway that should be used by this instance.
     * @param outputBoundary The output boundary that should be used by this instance.
     */
    public PurgeOldToDosUseCase(final ToDoGateway gateway,
                                final PurgeToDoOutputBoundary outputBoundary) {

        this.gateway = requireNonNull(gateway, "The gateway may not be null");
        this.outputBoundary = requireNonNull(outputBoundary, "The output boundary may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final PurgeOldToDosRequest request) {
        final var threshold = LocalDateTime.now().minusMonths(3);

        final Collection<IdentityValueObject> deletedTodos = new ArrayList<>();

        gateway.fetchAllCompletedBefore(threshold)
                .forEach(todo -> {
                    if (todo.has(ToDoCapabilityValueObject.DELETE)) {
                        final boolean deleted = gateway.delete(todo.aggregateRoot().identity());

                        if (deleted) {
                            deletedTodos.add(todo.aggregateRoot().identity());
                        }
                    }
                });

        final var response =
                new PurgedToDosResponse(deletedTodos.stream()
                        .map(IdentityValueObject::value)
                        .toList());

        response.applyTo(outputBoundary);
    }
}
