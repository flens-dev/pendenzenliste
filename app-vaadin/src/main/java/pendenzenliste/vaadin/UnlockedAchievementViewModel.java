package pendenzenliste.vaadin;

import pendenzenliste.vaadin.util.BindingProperty;
import pendenzenliste.vaadin.util.StringBindingProperty;

public final class UnlockedAchievementViewModel {

    public final BindingProperty<String> title = new StringBindingProperty();

    public final BindingProperty<String> description = new StringBindingProperty();
}
