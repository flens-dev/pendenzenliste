package pendenzenliste.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * An application that runs the pendenzenliste app.
 */
public class PendenzenlisteApp extends Application
{

  /**
   * The main entry point for the application.
   *
   * @param args The command line arguments.
   */
  public static void main(final String[] args)
  {
    launch(args);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(final Stage window)
  {
    window.setTitle("Pendenzenliste");
    window.show();
    window.setMaximized(true);

    window.setScene(new Scene(new VBox()));
  }
}
