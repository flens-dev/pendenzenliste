package pendenzenliste.vaadin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

import com.vaadin.flow.component.HasValue;

/**
 * A property that can be used to perform bindings in vaadin applications.
 *
 * @param <T> The type of the value represented by the binding.
 */
public class BindingProperty<T>
{
  private final Collection<Consumer<T>> bindings = new ArrayList<>();

  private T value;

  /**
   * Creates a new instance.
   */
  public BindingProperty()
  {
  }

  /**
   * Creates a new instance.
   *
   * @param initialValue The initial value of the property.
   */
  public BindingProperty(final T initialValue)
  {
    super();

    this.value = initialValue;
  }

  /**
   * Retrieves the currently stored value of the property.
   *
   * @return The value.
   */
  public T get()
  {
    return value;
  }

  /**
   * Sets the given value to the property.
   *
   * @param value The value.
   */
  public void set(final T value)
  {
    final T oldValue = this.value;

    if (!Objects.equals(value, oldValue))
    {
      this.value = value;
      bindings.forEach(binding -> binding.accept(this.value));
    }
  }

  /**
   * Binds the given binding to the property.
   *
   * @param binding The binding.
   */
  public void bind(final Consumer<T> binding)
  {
    bindings.add(binding);
    binding.accept(value);
  }

  /**
   * Two-way binds the given component to the property.
   *
   * @param component The component.
   */
  public void bindTwoWay(final HasValue<?, T> component)
  {
    bind(component::setValue);
    component.addValueChangeListener(l -> set(l.getValue()));
  }

  /**
   * Checks whether the current value of the property is null.
   *
   * @return True if the current value is null, otherwise false.
   */
  public boolean isNull()
  {
    return value == null;
  }
}
