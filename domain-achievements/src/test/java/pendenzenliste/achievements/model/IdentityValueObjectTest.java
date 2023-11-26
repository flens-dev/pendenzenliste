package pendenzenliste.achievements.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IdentityValueObjectTest {

    @Test
    public void valueMayNotBeNull() {
        assertThatThrownBy(() -> new IdentityValueObject(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The value may not be null");
    }

    @Test
    public void valueMayNotBeEmpty() {
        assertThatThrownBy(() -> new IdentityValueObject(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The value may not be empty");
    }
}