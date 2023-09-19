package pendenzenliste.vaadin;

import pendenzenliste.vaadin.util.BindingProperty;
import pendenzenliste.vaadin.util.StringBindingProperty;

import java.time.LocalDateTime;

/**
 * A view model that can be used to represent an achievement.
 */
public class AchievementViewModel {
    public final StringBindingProperty title = new StringBindingProperty();

    public final StringBindingProperty description = new StringBindingProperty();

    public final BindingProperty<LocalDateTime> unlocked = new BindingProperty<>();

    public final StringBindingProperty state = new StringBindingProperty();
}
