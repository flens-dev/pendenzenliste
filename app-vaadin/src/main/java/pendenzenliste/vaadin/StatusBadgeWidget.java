package pendenzenliste.vaadin;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;

/**
 * A status badge component.
 */
public class StatusBadgeWidget extends Composite<Span>
{
  private final String status;

  /**
   * Creates a new instance.
   *
   * @param status The status.
   */
  public StatusBadgeWidget(final String status)
  {
    super();

    this.status = status;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Span initContent()
  {
    final var badge = super.initContent();

    badge.setText(status);
    badge.getElement().getThemeList().add("badge");

    if ("DONE".equals(status))
    {
      badge.getElement().getThemeList().add("success");
    }

    return badge;
  }
}
