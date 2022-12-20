package pendenzenliste.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

/**
 * A status badge component.
 */
@JsModule("./src/state-badge.ts")
@Tag("state-badge")
public class StateBadgeWidget extends Component
{
  /**
   * Creates a new instance.
   *
   * @param status The status.
   */
  public StateBadgeWidget(final String status)
  {
    super();

    getElement().setProperty("state", status);
  }
}
