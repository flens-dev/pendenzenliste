package pendenzenliste.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.function.ValueProvider;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * A widget that can be used to render a list of todos.
 */
public class ToDoListWidget extends Composite<Grid<ToDoListItemViewModel>> {
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Collection<Consumer<ToDoListItemViewModel>> deleteListeners = new ArrayList<>();
    private final Collection<Consumer<ToDoListItemViewModel>> editListeners = new ArrayList<>();
    private final Collection<Consumer<ToDoListItemViewModel>> completeListeners = new ArrayList<>();

    private final Collection<Consumer<ToDoListItemViewModel>> reopenListeners = new ArrayList<>();

    /**
     * Sets the items.
     *
     * @param items The items.
     */
    public void setItems(final Collection<ToDoListItemViewModel> items) {
        getContent().setItems(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Grid<ToDoListItemViewModel> initContent() {
        final var todoList = super.initContent();

        todoList.addComponentColumn(renderStateBadge()).setHeader("State")
                .setFlexGrow(0);

        todoList.addColumn(todo -> todo.headline.get()).setHeader("Headline")
                .setFlexGrow(1)
                .setSortable(true);

        todoList.addColumn(todo -> todo.created.get().format(DATE_TIME_FORMAT)).setHeader("Created")
                .setSortable(true);

        final var lastModifiedColumn =
                todoList.addColumn(todo -> todo.lastModified.get().format(DATE_TIME_FORMAT))
                        .setHeader("Last modified").setSortable(true);

        todoList.addComponentColumn(renderActionColumn()).setFlexGrow(0);

        todoList.addSelectionListener(l -> editListeners.forEach(
                editListener -> editListener.accept(l.getFirstSelectedItem().orElse(null))));

        todoList.sort(GridSortOrder.desc(lastModifiedColumn).build());
        todoList.setHeightFull();

        return todoList;
    }

    /**
     * Renders a state badge.
     *
     * @return The provider.
     */
    private ValueProvider<ToDoListItemViewModel, Component> renderStateBadge() {
        return l -> new StateBadgeWidget(l.state.get());
    }

    /**
     * Renders the action column
     *
     * @return The provider.
     */
    private ValueProvider<ToDoListItemViewModel, MenuBar> renderActionColumn() {
        return todo -> {
            final var bar = new MenuBar();

            bar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);

            final var menuItem = bar.addItem("•••");

            final SubMenu menu = menuItem.getSubMenu();

            final var editItem =
                    menu.addItem("Edit", event -> editListeners.forEach(l -> l.accept(todo)));

            final var completeItem =
                    menu.addItem("Complete", event -> completeListeners.forEach(l -> l.accept(todo)));

            final var deleteItem =
                    menu.addItem("Delete", event -> deleteListeners.forEach(l -> l.accept(todo)));

            final var reopenItem =
                    menu.addItem("Reopen", event -> reopenListeners.forEach(l -> l.accept(todo)));

            todo.editable.bind(editItem::setEnabled);
            todo.completable.bind(completeItem::setEnabled);
            todo.deletable.bind(deleteItem::setEnabled);
            todo.reopenable.bind(reopenItem::setEnabled);

            return bar;
        };
    }

    /**
     * Adds an edit listener.
     *
     * @param listener The listener.
     */
    public void addEditListener(final Consumer<ToDoListItemViewModel> listener) {
        editListeners.add(listener);
    }

    /**
     * Adds a complete listener.
     *
     * @param listener The listener.
     */
    public void addCompleteListener(final Consumer<ToDoListItemViewModel> listener) {
        completeListeners.add(listener);
    }

    /**
     * Adds a delete listener.
     *
     * @param listener The listener.
     */
    public void addDeleteListener(final Consumer<ToDoListItemViewModel> listener) {
        deleteListeners.add(listener);
    }

    /**
     * Adds a reopen listener.
     *
     * @param listener The listener.
     */
    public void addReopenListener(final Consumer<ToDoListItemViewModel> listener) {
        reopenListeners.add(listener);
    }
}
