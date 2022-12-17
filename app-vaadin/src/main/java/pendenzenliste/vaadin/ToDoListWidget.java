package pendenzenliste.vaadin;

import java.util.Collection;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;

/**
 * A widget that can be used to render a list of todos.
 */
public class ToDoListWidget extends Composite<Grid<ToDoListItemViewModel>>
{
  /**
   * Sets the items.
   *
   * @param items The items.
   */
  public void setItems(final Collection<ToDoListItemViewModel> items)
  {
    getContent().setItems(items);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Grid<ToDoListItemViewModel> initContent()
  {
    final var todoList = super.initContent();

    todoList.addColumn(ToDoListItemViewModel::headline)
        .setHeader("Headline")
        .setSortable(true);

    todoList.addColumn(ToDoListItemViewModel::state)
        .setHeader("State")
        .setSortable(true);

    todoList.addColumn(ToDoListItemViewModel::created)
        .setHeader("Created")
        .setSortable(true);

    final var lastModifiedColumn =
        todoList.addColumn(ToDoListItemViewModel::lastModified)
            .setHeader("Last modified")
            .setSortable(true);

    todoList.sort(GridSortOrder.desc(lastModifiedColumn).build());

    todoList.setHeightFull();

    return todoList;
  }
}
