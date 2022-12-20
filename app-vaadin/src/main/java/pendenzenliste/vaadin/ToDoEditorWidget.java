package pendenzenliste.vaadin;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 * A widget that can be used to edit a todo.
 */
public class ToDoEditorWidget extends Composite<Div>
{
  private final TextField headlineField = new TextField("Headline");
  private final TextArea descriptionField = new TextArea("Description");

  private final Button saveButton = new Button("Save");

  private final Button clearButton = new Button("Clear");

  /**
   * The headline field
   *
   * @return The headline field.
   */
  public TextField getHeadlineField()
  {
    return headlineField;
  }

  /**
   * The description field.
   *
   * @return The description.
   */
  public TextArea getDescriptionField()
  {
    return descriptionField;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Div initContent()
  {
    final var editorLayout = super.initContent();

    editorLayout.getStyle().set("display", "flex");
    editorLayout.getStyle().set("flex-direction", "column");
    editorLayout.getStyle().set("padding", "var(--lumo-space-m)");
    editorLayout.getStyle().set("height", "calc(100% - 2 * var(--lumo-space-m)");

    headlineField.setWidthFull();
    descriptionField.setWidthFull();
    descriptionField.getStyle().set("flex-grow", "1");

    final var actions = new Div(clearButton, saveButton);

    actions.getStyle().set("display", "flex");
    actions.getStyle().set("grid-gap", "var(--lumo-space-m)");
    actions.getStyle().set("justify-content", "flex-end");

    saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    editorLayout.add(headlineField, descriptionField, actions);

    return editorLayout;
  }

  /**
   * Adds a save listener.
   *
   * @param listener The listener.
   */
  public void addSaveListener(final ComponentEventListener<ClickEvent<Button>> listener)
  {
    saveButton.addClickListener(listener);
  }

  /**
   * Adds a clear listener.
   *
   * @param listener The listener.
   */
  public void addClearListener(final ComponentEventListener<ClickEvent<Button>> listener)
  {
    clearButton.addClickListener(listener);
  }
}
