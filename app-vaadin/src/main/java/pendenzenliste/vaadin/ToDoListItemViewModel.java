package pendenzenliste.vaadin;

import pendenzenliste.vaadin.binding.BindingProperty;
import pendenzenliste.vaadin.binding.BooleanBindingProperty;
import pendenzenliste.vaadin.binding.StringBindingProperty;

import java.time.LocalDateTime;

/**
 * A view model that can be used to represent a todo in a list view.
 */
public class ToDoListItemViewModel {
    public final StringBindingProperty identity = new StringBindingProperty();

    public final StringBindingProperty headline = new StringBindingProperty();

    public final BindingProperty<LocalDateTime> created = new BindingProperty<>();

    public final BindingProperty<LocalDateTime> lastModified = new BindingProperty<>();

    public final BindingProperty<LocalDateTime> completed = new BindingProperty<>();

    public final StringBindingProperty state = new StringBindingProperty();

    public final BooleanBindingProperty deletable = new BooleanBindingProperty(Boolean.FALSE);

    public final BooleanBindingProperty editable = new BooleanBindingProperty(Boolean.FALSE);

    public final BooleanBindingProperty completable = new BooleanBindingProperty(Boolean.FALSE);

    public final BooleanBindingProperty reopenable = new BooleanBindingProperty(Boolean.FALSE);
}
