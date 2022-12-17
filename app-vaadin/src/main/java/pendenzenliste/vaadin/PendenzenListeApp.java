package pendenzenliste.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The spring boot application used to run the app.
 */
@SpringBootApplication
public class PendenzenListeApp
{

  /**
   * The entrypoint for the application.
   *
   * @param args The command line arguments.
   */
  public static void main(final String[] args)
  {
    SpringApplication.run(PendenzenListeApp.class, args);
  }

}
