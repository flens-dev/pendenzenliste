package pendenzenliste.vaadin;

import com.vaadin.flow.component.textfield.TextField;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.vaadin.util.StringBindingProperty;

import static org.assertj.core.api.Assertions.assertThat;

class StringBindingPropertyTest {
    @Test
    public void bindTwoWay_textFieldUpdate() {
        final var textField = new TextField();

        final var property = new StringBindingProperty();

        property.bindTwoWay(textField);

        textField.setValue("Test");

        final var assertions = new SoftAssertions();

        assertions.assertThat(property.get()).isEqualTo("Test");
        assertions.assertThat(textField.getValue()).isEqualTo("Test");

        assertions.assertAll();
    }

    @Test
    public void bindTwoWay_propertyUpdate() {
        final var textField = new TextField();

        final var property = new StringBindingProperty();

        property.bindTwoWay(textField);

        property.set("Test");

        final var assertions = new SoftAssertions();

        assertions.assertThat(property.get()).isEqualTo("Test");
        assertions.assertThat(textField.getValue()).isEqualTo("Test");

        assertions.assertAll();
    }

    @Test
    public void bind_propertyShouldNotBeUpdatedOnFieldUpdate() {
        final var textField = new TextField();

        final var property = new StringBindingProperty("initial value");

        property.bind(textField::setValue);

        textField.setValue("Test");

        assertThat(property.get()).isEqualTo("initial value");
    }

    @Test
    public void bind_textFieldShouldBeUpdatedOnPropertyUpdate() {
        final var textField = new TextField();

        final var property = new StringBindingProperty("initial value");

        property.bind(textField::setValue);

        property.set("Test");

        assertThat(property.get()).isEqualTo("Test");
    }
}