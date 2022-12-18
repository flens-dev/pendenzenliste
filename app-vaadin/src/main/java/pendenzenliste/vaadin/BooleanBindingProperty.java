package pendenzenliste.vaadin;

/**
 * A boolean specific binding property.
 */
public class BooleanBindingProperty extends BindingProperty<Boolean>
{
  /**
   * Creates a new instance.
   */
  public BooleanBindingProperty()
  {
    this(Boolean.FALSE);
  }

  /**
   * Creates a new instance.
   *
   * @param initialValue The initial value.
   */
  public BooleanBindingProperty(final Boolean initialValue)
  {
    super(initialValue);
  }
}
