package pendenzenliste.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

/**
 * A widget that can be used to represent an achievement.
 */
@JsModule("./src/achievement-item.ts")
@Tag("achievement-item")
public class AchievementItemWidget extends Component {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance.
     */
    public AchievementItemWidget(final AchievementViewModel viewModel) {
        super();

        getElement().setProperty("headline", viewModel.title.get());
        getElement().setProperty("description", viewModel.description.get());
        getElement().setProperty("state", viewModel.state.get());
    }
}
