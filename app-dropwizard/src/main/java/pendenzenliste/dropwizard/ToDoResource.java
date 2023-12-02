package pendenzenliste.dropwizard;

import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactoryProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static java.util.Objects.requireNonNull;

/**
 * A resource that can be used to access the todos.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("todos")
public class ToDoResource {
    private final ToDoInputBoundaryFactoryProvider provider;

    /**
     * Creates a new instance.
     *
     * @param provider The provider.
     */
    public ToDoResource(final ToDoInputBoundaryFactoryProvider provider) {

        this.provider = requireNonNull(provider, "The provider may not be null");
    }

    /**
     * Lists the todos.
     *
     * @return The response.
     */
    @GET
    public Response list() {
        final var viewModel = new HttpResponseViewModel();
        final var outputBoundaryFactory = new JsonToDoPresenterFactory(viewModel);
        final var inputBoundaryFactory = provider.getInstance(outputBoundaryFactory);

        final var controller = new ToDoController(inputBoundaryFactory);

        controller.list();

        return viewModel.response;
    }

    /**
     * Fetches a specific todo by its ID.
     *
     * @param id The ID of the todo.
     * @return The response.
     */
    @GET
    @Path("{id}")
    public Response fetch(@PathParam("id") final String id) {
        final var viewModel = new HttpResponseViewModel();
        final var outputBoundaryFactory = new JsonToDoPresenterFactory(viewModel);
        final var inputBoundaryFactory = provider.getInstance(outputBoundaryFactory);

        final var controller = new ToDoController(inputBoundaryFactory);

        controller.fetch(id);

        return viewModel.response;
    }

    /**
     * Creates a new todo.
     *
     * @param data The data used to create the todo.
     * @return The response.
     */
    @POST
    public Response create(JsonCreateToDoData data) {
        final var viewModel = new HttpResponseViewModel();
        final var outputBoundaryFactory = new JsonToDoPresenterFactory(viewModel);
        final var inputBoundaryFactory = provider.getInstance(outputBoundaryFactory);

        final var controller = new ToDoController(inputBoundaryFactory);

        controller.create(data);

        return viewModel.response;
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") final String id) {
      final var viewModel = new HttpResponseViewModel();
      final var outputBoundaryFactory = new JsonToDoPresenterFactory(viewModel);
      final var inputBoundaryFactory = provider.getInstance(outputBoundaryFactory);

      final var controller = new ToDoController(inputBoundaryFactory);

      controller.delete(id);

      return viewModel.response;
    }
}
