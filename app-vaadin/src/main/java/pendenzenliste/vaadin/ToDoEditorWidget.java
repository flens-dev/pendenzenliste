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
   * Clears the editor.
   */
  public void clear()
  {
    headlineField.clear();
    descriptionField.clear();
  }

  /**
   * Retrieves the headline.
   *
   * @return The headline.
   */
  public String getHeadline()
  {
    return headlineField.getValue();
  }

  /**
   * Retrieves the description.
   *
   * @return The description.
   */
  public String getDescription()
  {
    return descriptionField.getValue();
  }

  /**
   * Sets the headline.
   *
   * @param headline The headline.
   */
  public void setHeadline(final String headline)
  {
    this.headlineField.setValue(headline);
  }

  /**
   * Sets the description.
   *
   * @param description The description.
   */
  public void setDescription(final String description)
  {
    this.descriptionField.setValue(description);
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
