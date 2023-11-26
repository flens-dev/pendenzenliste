package pendenzenliste.dropwizard;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pendenzenliste.todos.boundary.in.*;
import pendenzenliste.todos.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.todos.boundary.out.FetchedToDoListResponse;
import pendenzenliste.todos.boundary.out.ToDoCreationFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoOutputBoundaryFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ToDoResourceTest {


    final ToDoInputBoundaryFactoryProvider provider =
            mock(ToDoInputBoundaryFactoryProvider.class);

    final ToDoInputBoundaryFactory inputFactory = mock(ToDoInputBoundaryFactory.class);


    final Collection<ToDoOutputBoundaryFactory> outputBoundaryFactories = new ArrayList<>();

    @BeforeEach
    public void setup() {
        when(provider.getInstance(any())).thenAnswer(in -> {
            outputBoundaryFactories.add(in.getArgument(0));
            return inputFactory;
        });
    }

    @Test
    public void list() {
        final FetchToDoListInputBoundary list = mock(FetchToDoListInputBoundary.class);
        when(inputFactory.list()).thenReturn(list);
        doAnswer(in -> {
            for (final ToDoOutputBoundaryFactory factory : outputBoundaryFactories) {
                factory.list().handleSuccessfulResponse(new FetchedToDoListResponse(Collections.emptyList()));
            }
            return null;
        }).when(list).execute(any());

        final var resource = new ToDoResource(provider);

        final var response = resource.list();

        final var assertions = new SoftAssertions();

        assertions.assertThat(response.getStatus()).isEqualTo(200);

        assertions.assertAll();
    }


    @Test
    public void fetch() {
        final FetchToDoInputBoundary fetch = mock(FetchToDoInputBoundary.class);
        when(inputFactory.fetch()).thenReturn(fetch);
        doAnswer(in -> {
            for (final ToDoOutputBoundaryFactory factory : outputBoundaryFactories) {
                factory.fetch()
                        .handleFailedResponse(new FetchToDoFailedResponse("Something bad happened"));
            }
            return null;
        }).when(fetch).execute(any());

        final var resource = new ToDoResource(provider);

        final var response = resource.fetch("42");

        final var assertions = new SoftAssertions();

        assertions.assertThat(response.getStatus()).isEqualTo(404);

        assertions.assertAll();
    }

    @Test
    public void create() {
        final CreateToDoInputBoundary create = mock(CreateToDoInputBoundary.class);
        when(inputFactory.create()).thenReturn(create);
        doAnswer(in -> {
            for (final ToDoOutputBoundaryFactory factory : outputBoundaryFactories) {
                factory.create()
                        .handleFailedResponse(new ToDoCreationFailedResponse("Something bad happened"));
            }
            return null;
        }).when(create).execute(any());

        final var resource = new ToDoResource(provider);

        final var response =
                resource.create(new JsonCreateToDoData("Headline", "Description"));

        final var assertions = new SoftAssertions();

        assertions.assertThat(response.getStatus()).isEqualTo(400);

        assertions.assertAll();
    }
}