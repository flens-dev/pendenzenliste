package pendenzenliste.vaadin.binding;

import com.vaadin.flow.component.checkbox.Checkbox;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class BooleanBindingPropertyTest {

    @Test
    public void bindTwoWay_checkboxUpdate() {
        final var checkbox = new Checkbox();
        final var property = new BooleanBindingProperty();

        property.bindTwoWay(checkbox);

        checkbox.setValue(true);

        final var assertions = new SoftAssertions();

        assertions.assertThat(checkbox.getValue()).isTrue();
        assertions.assertThat(property.get()).isTrue();

        assertions.assertAll();
    }

    @Test
    public void bindTwoWay_propertyUpdate() {
        final var checkbox = new Checkbox();
        final var property = new BooleanBindingProperty();

        property.bindTwoWay(checkbox);

        property.set(true);

        final var assertions = new SoftAssertions();

        assertions.assertThat(checkbox.getValue()).isTrue();
        assertions.assertThat(property.get()).isTrue();

        assertions.assertAll();
    }
}