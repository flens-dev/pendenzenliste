package pendenzenliste.vaadin;

import pendenzenliste.vaadin.binding.BindingProperty;
import pendenzenliste.vaadin.binding.StringBindingProperty;

public final class UnlockedAchievementViewModel {

    public final BindingProperty<String> title = new StringBindingProperty();

    public final BindingProperty<String> description = new StringBindingProperty();
}
