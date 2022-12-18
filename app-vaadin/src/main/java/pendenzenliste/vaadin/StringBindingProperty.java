package pendenzenliste.vaadin;

/**
 * A string binding property.
 */
public class StringBindingProperty extends BindingProperty<String>
{
  /**
   * Creates a new instance.
   */
  public StringBindingProperty()
  {
    this("");
  }

  /**
   * Creates a new instance.
   *
   * @param initialValue The initial value.
   */
  public StringBindingProperty(final String initialValue)
  {
    super(initialValue);
  }

  /**
   * Checks whether the property is empty or null.
   *
   * @return True if the property is empty or null, otherwise false.
   */
  public boolean isEmpty()
  {
    return isNull() || get().isEmpty();
  }
}
