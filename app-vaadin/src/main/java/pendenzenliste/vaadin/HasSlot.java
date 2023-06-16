package pendenzenliste.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;

/**
 * A component mixin that can be used to ease access to a web components slots.
 */
public interface HasSlot extends HasComponents {

    /**
     * Adds the given component to the given slot.
     *
     * @param slot      The slot.
     * @param component The component.
     */
    default void addToSlot(final String slot, final Component component) {
        component.getElement().setProperty("slot", slot);
        add(component);
    }
}
