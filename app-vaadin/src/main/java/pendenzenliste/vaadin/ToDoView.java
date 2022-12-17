package pendenzenliste.vaadin;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationListener;
import com.vaadin.flow.router.Route;
import pendenzenliste.ports.in.FetchTodoListRequest;
import pendenzenliste.ports.in.ToDoInputBoundaryFactory;
import pendenzenliste.ports.in.ToDoInputBoundaryFactoryProvider;

/**
 * A view that can be used to display a list of todos and interact with them.
 */
@Route("")
public class ToDoView extends Div implements AfterNavigationListener
{
  private final ToDoListWidget todoList = new ToDoListWidget();

  private final ToDoInputBoundaryFactory inputBoundaryFactory;

  /**
   * Creates a new instance.
   */
  public ToDoView()
  {
    //TODO: Instance should be injected from somewhere I guess...
    this(ToDoInputBoundaryFactoryProvider.defaultProvider());
  }

  /**
   * Creates a new instance.
   */
  public ToDoView(final ToDoInputBoundaryFactoryProvider inputBoundaryFactoryProvider)
  {
    super();

    this.inputBoundaryFactory = requireNonNull(inputBoundaryFactoryProvider,
        "The input boundary factory provider may not be null")
        .getInstance(new ToDoPresenterFactory(this));

    final var container = new Div();

    container.setHeightFull();
    container.getStyle().set("display", "grid");
    container.getStyle().set("grid-template-columns", "2fr 1fr");

    final var mainContainer = new Div();

    mainContainer.add(todoList);

    mainContainer.getStyle().set("padding", "var(--lumo-space-m)");

    container.add(mainContainer);

    final var editorLayout = new Div();

    editorLayout.getStyle().set("display", "flex");
    editorLayout.getStyle().set("flex-direction", "column");
    editorLayout.getStyle().set("padding", "var(--lumo-space-m)");

    final var headlineField = new TextField("Headline");
    final var descriptionField = new TextArea("Description");
    final var saveButton = new Button("Save");

    headlineField.setWidthFull();
    descriptionField.setWidthFull();
    descriptionField.getStyle().set("flex-grow", "1");

    saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    editorLayout.add(headlineField, descriptionField, saveButton);

    container.add(editorLayout);
    add(container);

    setHeightFull();
  }

  /**
   * Loads the todos.
   */
  public void loadToDos()
  {
    var request = new FetchTodoListRequest();

    inputBoundaryFactory.list().execute(request);
  }

  /**
   * Sets the todos.
   *
   * @param todos The todos.
   */
  public void setToDos(final Collection<ToDoListItemViewModel> todos)
  {
    todoList.setItems(todos);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void afterNavigation(final AfterNavigationEvent event)
  {
    loadToDos();
  }

  /**
   * Shows a generic error message.
   *
   * @param message The message that should be shown.
   */
  public void showGenericErrorMessage(final String message)
  {
    Notification.show(message).addThemeVariants(NotificationVariant.LUMO_ERROR);
  }
}
