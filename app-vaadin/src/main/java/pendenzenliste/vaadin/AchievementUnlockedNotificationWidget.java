package pendenzenliste.vaadin;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * A widget that can be used to display a notification when an achievement has been unlocked.
 */
public class AchievementUnlockedNotificationWidget extends Notification {

    /**
     * Creates a new instance.
     *
     * @param achievement The data of the achievement.
     */
    public AchievementUnlockedNotificationWidget(final UnlockedAchievementViewModel achievement) {
        final var icon = VaadinIcon.CHECK_CIRCLE.create();
        icon.setColor("var(--lumo-success-color)");

        final var uploadSuccessful = new Div(new Text(achievement.title.get()));
        uploadSuccessful.getStyle().set("font-weight", "600").set("color", "var(--lumo-success-text-color)");

        final var info = new Div(uploadSuccessful, new Div(new Text(achievement.description.get())));
        info.getStyle().set("font-size", "var(--lumo-font-size-s)").set("color", "var(--lumo-secondary-text-color)");

        HorizontalLayout layout = new HorizontalLayout(icon, info);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(layout);
        setDuration(10000);
    }
}
