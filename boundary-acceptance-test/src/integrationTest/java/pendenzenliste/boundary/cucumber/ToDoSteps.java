package pendenzenliste.boundary.cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.boundary.in.FetchTodoListRequest;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactory;
import pendenzenliste.todos.boundary.out.*;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.model.*;
import pendenzenliste.todos.usecases.ToDoUseCaseFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The steps used to execute the ToDo acceptance tests.
 */
public class ToDoSteps {
    private ToDoGateway gateway;

    private final CapturingOutputBoundaryFactory outputBoundaryFactory = new CapturingOutputBoundaryFactory();

    private final EventBus eventPublisher = mock(EventBus.class);

    private final ToDoGatewayFactory gatewayFactory = new ToDoGatewayFactory(eventPublisher);

    private final ToDoInputBoundaryFactory factory = new ToDoUseCaseFactory(() -> gateway, outputBoundaryFactory, eventPublisher);

    private final ToDoRequestBuilder builder = new ToDoRequestBuilder();


    @After
    public void tearDown() {
        gatewayFactory.tearDown();
    }

    @Given("that I do not enter an ID")
    public void givenThatIDoNotEnterAnID() {
    }


    @Given("that I enter the ID {string}")
    public void givenThatIEnterTheID(String id) {
        builder.id(id);
    }


    @Given("that the ToDo does not exist")
    public void givenThatTheToDoDoesNotExist() {
        gateway.fetchAll().forEach(todo -> gateway.delete(todo.aggregateRoot().identity()));
    }


    @Given("that the following ToDo exists:")
    public void givenThatTheFollowingToDoExists(DataTable data) {
        data.asMaps().stream().map(this::parseToDoFrom).forEach(gateway::store);
    }

    @Given("that I enter the headline {string}")
    public void givenThatIEnterTheHeadline(String headline) {
        builder.headline(headline);
    }

    @Given("that I enter the description {string}")
    public void givenThatIEnterTheDescription(String description) {
        builder.description(description);
    }

    @When("I try to fetch the ToDo")
    public void whenITryToFetchTheToDo() {
        factory.fetch().execute(builder.fetchToDoRequest());
    }


    @When("I try to delete the ToDo")
    public void whenITryToDeleteTheToDo() {
        factory.delete().execute(builder.buildDeleteRequest());
    }

    @When("I try to complete the ToDo")
    public void whenITryToCompleteTheToDo() {
        factory.complete().execute(builder.buildCompleteRequest());
    }


    @When("I try to reset the ToDo")
    public void whenITryToResetTheToDo() {
        factory.reopen().execute(builder.buildReopenRequest());
    }

    @When("I try to update the ToDo")
    public void whenITryToUpdateTheToDo() {
        factory.update().execute(builder.buildUpdateRequest());
    }

    @When("I try to purge the todos")
    public void whenITryToPurgeTheTodos() {
        factory.purgeOldToDos().execute(builder.buildPurgeRequest());
    }

    @Then("purging the todos should have succeeded")
    public void thenPurgingTheTodosShouldHaveSucceeded() {
        outputBoundaryFactory.purgeResponse.applyTo(response ->
                assertThat(response.deletedToDoIds()).isNotEmpty());
    }

    @Then("fetching the ToDo should have failed with the message: {string}")
    public void thenFetchingTheToDoShouldHaveFailedWithTheMessage(final String expectedMessage) {
        outputBoundaryFactory.fetchResponse.applyTo(new FetchToDoOutputBoundary() {
            @Override
            public void handleFailedResponse(final FetchToDoFailedResponse response) {
                assertThat(response.reason()).isEqualTo(expectedMessage);
            }

            @Override
            public void handleSuccessfulResponse(final ToDoFetchedResponse response) {
                fail("The request should have failed");
            }
        });
    }

    @Then("the fetched Todo should have the following values:")
    public void thenTheFetchedTodoShouldHaveTheFollowingValues(final DataTable data) {
        outputBoundaryFactory.fetchResponse.applyTo(new FetchToDoOutputBoundary() {
            @Override
            public void handleFailedResponse(final FetchToDoFailedResponse response) {
                fail(response.reason());
            }

            @Override
            public void handleSuccessfulResponse(final ToDoFetchedResponse response) {
                final var todo = parseToDoFrom(data.asMaps().stream().findFirst().get());

                final SoftAssertions assertions = new SoftAssertions();

                assertions.assertThat(response.identity()).isEqualTo(todo.aggregateRoot().identity().value());
                assertions.assertThat(response.headline()).isEqualTo(todo.aggregateRoot().headline().value());
                assertions.assertThat(response.description()).isEqualTo(todo.aggregateRoot().description().value());
                assertions.assertThat(response.created()).isEqualTo(todo.aggregateRoot().created().value());
                assertions.assertThat(response.lastModified()).isEqualTo(todo.aggregateRoot().lastModified().value());
                assertions.assertThat(response.state()).isEqualTo(todo.aggregateRoot().state().name());
                assertions.assertThat(response.completed()).isEqualTo(response.completed());

                assertions.assertAll();
            }
        });
    }

    @Then("the todo update should have failed with the message: {string}")
    public void thenTheTodoUpdateShouldHaveFailedWithTheMessage(String expectedMessage) {
        outputBoundaryFactory.updateResponse.applyTo(new UpdateToDoOutputBoundary() {
            @Override
            public void handleSuccessfulResponse(ToDoUpdatedResponse response) {
                fail("The request should have failed");
            }

            @Override
            public void handleFailedResponse(ToDoUpdateFailedResponse response) {
                assertThat(response.reason()).isEqualTo(expectedMessage);
            }
        });
    }

    @Then("the todo update should have been successful")
    public void theTodoUpdateShouldHaveBeenSuccessful() {
        outputBoundaryFactory.updateResponse.applyTo(new UpdateToDoOutputBoundary() {
            @Override
            public void handleSuccessfulResponse(final ToDoUpdatedResponse response) {
                assertThat(response).isNotNull();
            }

            @Override
            public void handleFailedResponse(final ToDoUpdateFailedResponse response) {
                fail(response.reason());
            }
        });
    }


    /**
     * Parses a ToDo from the given row.
     *
     * @param row The row that should be parsed.
     * @return The parsed entity.
     */
    private ToDoAggregate parseToDoFrom(final Map<String, String> row) {
        return new ToDoAggregate(new ToDoEntity(
                new IdentityValueObject(row.get("identity")),
                new HeadlineValueObject(row.get("headline")),
                new DescriptionValueObject(row.get("description")),
                new CreatedTimestampValueObject(LocalDateTime.parse(row.get("created"))),
                new LastModifiedTimestampValueObject(LocalDateTime.parse(row.get("last modified"))),
                new CompletedTimestampValueObject(
                        Optional.ofNullable(row.getOrDefault("completed", null))
                                .map(LocalDateTime::parse)
                                .orElse(null)),
                ToDoStateValueObject.valueOf(row.get("state"))
        ), gateway, eventPublisher);
    }

    @Then("a {string} should have been published")
    public void thenAnEventEventShouldHaveBeenPublished(final String type) {
        final Map<String, ? extends Class<? extends ToDoEvent>> typeMap = List.of(ToDoCompletedEvent.class, ToDoCreatedEvent.class, ToDoDeletedEvent.class, ToDoReopenedEvent.class, ToDoUpdatedEvent.class).stream().collect(Collectors.toMap(Class::getSimpleName, clazz -> clazz));

        final Class<? extends ToDoEvent> expectedEventType = typeMap.get(type);

        verify(eventPublisher, times(1)).publish(any(expectedEventType));
    }

    @Given("that I do not enter a headline")
    public void givenThatIDoNotEnterAHeadline() {
    }

    @Given("that I do not enter a description")
    public void givenThatIDoNotEnterADescription() {
    }

    @When("I try to create the todo")
    public void whenITryToCreateTheTodo() {
        factory.create().execute(builder.buildCreateRequest());
    }

    @Then("creating the todo should have failed with the message {string}")
    public void creatingTheTodoShouldHaveFailedWithTheMessage(final String expectedMessage) {
        outputBoundaryFactory.createResponse.applyTo(new CreateToDoOutputBoundary() {
            @Override
            public void handleSuccessfulResponse(ToDoCreatedResponse response) {
                fail("The request should have failed");
            }

            @Override
            public void handleFailedResponse(ToDoCreationFailedResponse response) {
                assertThat(response.reason()).isEqualTo(expectedMessage);
            }
        });
    }

    @Then("creating the todo should have succeeded")
    public void creatingTheTodoShouldHaveSucceeded() {
        outputBoundaryFactory.createResponse.applyTo(new CreateToDoOutputBoundary() {
            @Override
            public void handleSuccessfulResponse(ToDoCreatedResponse response) {
                assertThat(response).isNotNull();
            }

            @Override
            public void handleFailedResponse(ToDoCreationFailedResponse response) {
                fail(response.reason());
            }
        });
    }

    @Given("that I configure the application to use the {string} todo gateway")
    public void thatIConfigureTheApplicationToUseTheBackendTodoGateway(final String backend) {
        this.gateway = gatewayFactory.create(backend);
    }

    @Given("that no ToDos exist")
    public void thatNoToDosExist() {
    }

    @When("I try to fetch the ToDo list")
    public void iTryToFetchTheToDoList() {
        final var request = new FetchTodoListRequest();

        factory.list().execute(request);
    }

    @Then("the ToDo list should be empty")
    public void theToDoListShouldBeEmpty() {
        outputBoundaryFactory.fetchListResponse.applyTo(new FetchToDoListOutputBoundary() {
            @Override
            public void handleFailedResponse(FetchToDoListFailedResponse response) {
                fail(response.reason());
            }

            @Override
            public void handleSuccessfulResponse(FetchedToDoListResponse response) {
                assertThat(response.todos()).isEmpty();
            }

            @Override
            public boolean isDetached() {
                return false;
            }
        });
    }

    @Then("the ToDo list should contain the following ToDos")
    public void theToDoListShouldContainTheFollowingToDos(final DataTable data) {
        outputBoundaryFactory.fetchListResponse.applyTo(new FetchToDoListOutputBoundary() {
            @Override
            public void handleFailedResponse(FetchToDoListFailedResponse response) {
                fail(response.reason());
            }

            @Override
            public void handleSuccessfulResponse(FetchedToDoListResponse response) {
                final var expectedToDos = data.asMaps().stream().map(map -> parseToDoFrom(map)).toList();

                final SoftAssertions assertions = new SoftAssertions();

                for (final ToDoAggregate expected : expectedToDos) {
                    final var actual = response.todos().stream().filter(model -> Objects.equals(model.identity(), expected.aggregateRoot().identity().value())).findFirst();

                    if (actual.isEmpty()) {
                        assertions.fail("Missing item with ID" + expected.aggregateRoot().identity().value());
                    } else {
                        assertions.assertThat(actual.get().identity()).isEqualTo(expected.aggregateRoot().identity().value());
                        assertions.assertThat(actual.get().headline()).isEqualTo(expected.aggregateRoot().headline().value());
                        assertions.assertThat(actual.get().description()).isEqualTo(expected.aggregateRoot().description().value());
                        assertions.assertThat(actual.get().created()).isEqualTo(expected.aggregateRoot().created().value());
                        assertions.assertThat(actual.get().lastModified()).isEqualTo(expected.aggregateRoot().lastModified().value());
                        assertions.assertThat(actual.get().state()).isEqualTo(expected.aggregateRoot().state().name());
                    }
                }

                assertions.assertAll();
            }

            @Override
            public boolean isDetached() {
                return false;
            }
        });
    }
}
