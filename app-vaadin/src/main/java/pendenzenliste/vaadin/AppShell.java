package pendenzenliste.vaadin;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

/**
 * The app shell used to run the application.
 */
@Push
@Theme(value = "pendenzenliste")
@PWA(name = "Pendenzenliste", shortName = "Pendenzenliste")
public class AppShell implements AppShellConfigurator
{
}
