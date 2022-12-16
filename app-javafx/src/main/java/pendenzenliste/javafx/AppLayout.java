package pendenzenliste.javafx;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * An app layout.
 */
public class AppLayout extends BorderPane
{
  private final VBox mainContainer = new VBox();
  private final VBox detailContainer = new VBox();

  /**
   * Creates a new instance.
   */
  public AppLayout()
  {
    super();

    mainContainer.setPadding(new Insets(20));
    detailContainer.setPadding(new Insets(20));

    detailContainer.setSpacing(5);

    setCenter(mainContainer);
    setRight(detailContainer);

    setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

  }

  /**
   * Adds the given nodes to a main container.
   *
   * @param nodes The nodes.
   */
  public void addToMain(final Node... nodes)
  {
    mainContainer.getChildren().addAll(nodes);
  }

  /**
   * Adds the given nodes to a detail container
   *
   * @param nodes The nodes.
   */
  public void addToDetail(final Node... nodes)
  {
    detailContainer.getChildren().addAll(nodes);
  }
}
