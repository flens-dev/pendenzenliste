package pendenzenliste.javafx;

import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A widget that can be used to edit a todo.
 */
public class ToDoEditorWidget extends VBox
{
  private final Collection<Runnable> clearListeners = new ArrayList<>();

  private final Collection<Runnable> saveListeners = new ArrayList<>();

  private final Label headlineLabel = new Label("Headline");
  private final TextField headlineField = new TextField();

  private final Label descriptionLabel = new Label("Description");
  private final TextArea descriptionField = new TextArea();

  private final Label errorMessage = new Label("");
  private final Button clearButton = new Button("Clear");
  private final Button saveButton = new Button("Save");

  /**
   * Creates a new instance.
   */
  public ToDoEditorWidget()
  {
    super();

    setSpacing(10);

    final var actions = new HBox(clearButton, saveButton);

    clearButton.setOnAction(event -> clearListeners.forEach(Runnable::run));
    saveButton.setOnAction(event -> saveListeners.forEach(Runnable::run));

    actions.setSpacing(10);

    getChildren().addAll(headlineLabel, headlineField, descriptionLabel, descriptionField,
        errorMessage, actions);
  }

  /**
   * The headline label.
   *
   * @return The headline label.
   */
  public Label getHeadlineLabel()
  {
    return headlineLabel;
  }

  /**
   * The headline field.
   *
   * @return The headline field.
   */
  public TextField getHeadlineField()
  {
    return headlineField;
  }

  /**
   * The description label.
   *
   * @return The description label.
   */
  public Label getDescriptionLabel()
  {
    return descriptionLabel;
  }

  /**
   * The description field.
   *
   * @return The description field.
   */
  public TextArea getDescriptionField()
  {
    return descriptionField;
  }

  /**
   * The error message label.
   *
   * @return The error message label.
   */
  public Label getErrorMessageLabel()
  {
    return errorMessage;
  }

  /**
   * The clear button.
   *
   * @return The clear button
   */
  public Button getClearButton()
  {
    return clearButton;
  }

  /**
   * The save button.
   *
   * @return The save button.
   */
  public Button getSaveButton()
  {
    return saveButton;
  }

  /**
   * Adds a clear listener.
   *
   * @param listener The listener.
   */
  public void addClearListener(final Runnable listener)
  {
    clearListeners.add(listener);
  }

  /**
   * Adds a save listener.
   *
   * @param listener The listener.
   */
  public void addSaveListener(final Runnable listener)
  {
    saveListeners.add(listener);
  }
}
