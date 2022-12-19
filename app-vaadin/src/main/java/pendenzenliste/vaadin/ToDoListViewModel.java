package pendenzenliste.vaadin;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An experimental view model that provides bindings for the view.
 */
public class ToDoListViewModel
{
  public final BindingProperty<Collection<ToDoListItemViewModel>> todos =
      new BindingProperty<>(new ArrayList<>());

  public final StringBindingProperty identity = new StringBindingProperty();

  public final StringBindingProperty headline = new StringBindingProperty();

  public final StringBindingProperty description = new StringBindingProperty();

  public final StringBindingProperty errorMessage = new StringBindingProperty();

  /**
   * Clears the editor input values.
   */
  public void clearEditor()
  {
    identity.set(null);
    headline.clear();
    description.clear();
  }
}
