package pendenzenliste.vaadin;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.function.ValueProvider;

/**
 * A widget that can be used to render a list of todos.
 */
public class ToDoListWidget extends Composite<Grid<ToDoListItemViewModel>>
{
  private static final DateTimeFormatter DATE_TIME_FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private final Collection<Consumer<ToDoListItemViewModel>> deleteListeners = new ArrayList<>();
  private final Collection<Consumer<ToDoListItemViewModel>> editListeners = new ArrayList<>();
  private final Collection<Consumer<ToDoListItemViewModel>> completeListeners = new ArrayList<>();

  private final Collection<Consumer<ToDoListItemViewModel>> resetListeners = new ArrayList<>();

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

    todoList.addColumn(ToDoListItemViewModel::headline).setHeader("Headline").setSortable(true);

    todoList.addColumn(ToDoListItemViewModel::state).setHeader("State").setSortable(true);

    todoList.addColumn(todo -> todo.created().format(DATE_TIME_FORMAT))
        .setHeader("Created").setSortable(true);

    final var lastModifiedColumn =
        todoList.addColumn(todo -> todo.lastModified().format(DATE_TIME_FORMAT))
            .setHeader("Last modified")
            .setSortable(true);

    todoList.addComponentColumn(renderActionColumn()).setFlexGrow(0);

    todoList.addSelectionListener(l -> editListeners.forEach(
        editListener -> editListener.accept(l.getFirstSelectedItem().orElse(null))));

    todoList.sort(GridSortOrder.desc(lastModifiedColumn).build());
    todoList.setHeightFull();

    return todoList;
  }

  /**
   * Renders the action column
   *
   * @return The provider.
   */
  private ValueProvider<ToDoListItemViewModel, MenuBar> renderActionColumn()
  {
    return todo -> {
      final var bar = new MenuBar();

      bar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);

      final var menuItem = bar.addItem("•••");

      final SubMenu menu = menuItem.getSubMenu();

      menu.addItem("Edit", event -> editListeners.forEach(l -> l.accept(todo)))
          .setEnabled(todo.editable());

      menu.addItem("Complete", event -> completeListeners.forEach(l -> l.accept(todo)))
          .setEnabled(todo.completable());

      menu.addItem("Delete", event -> deleteListeners.forEach(l -> l.accept(todo)))
          .setEnabled(todo.deletable());

      menu.addItem("Reset", event -> resetListeners.forEach(l -> l.accept(todo)))
          .setEnabled(todo.resettable());

      return bar;
    };
  }

  /**
   * Clears the selection.
   */
  public void clearSelection()
  {
    getContent().select(null);
  }

  /**
   * Selects the given item.
   *
   * @param item The item that should be selected.
   */
  public void select(final ToDoListItemViewModel item)
  {
    getContent().select(item);
  }

  /**
   * Adds an edit listener.
   *
   * @param listener The listener.
   */
  public void addEditListener(final Consumer<ToDoListItemViewModel> listener)
  {
    editListeners.add(listener);
  }

  /**
   * Adds a complete listener.
   *
   * @param listener The listener.
   */
  public void addCompleteListener(final Consumer<ToDoListItemViewModel> listener)
  {
    completeListeners.add(listener);
  }

  /**
   * Adds a delete listener.
   *
   * @param listener The listener.
   */
  public void addDeleteListener(final Consumer<ToDoListItemViewModel> listener)
  {
    deleteListeners.add(listener);
  }

  /**
   * Adds a reset listener.
   *
   * @param listener The listener.
   */
  public void addResetListener(final Consumer<ToDoListItemViewModel> listener)
  {
    resetListeners.add(listener);
  }
}
