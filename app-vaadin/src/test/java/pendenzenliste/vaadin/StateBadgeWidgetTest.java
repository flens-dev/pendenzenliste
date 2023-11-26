package pendenzenliste.vaadin;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StateBadgeWidgetTest {

    @Test
    public void shouldBeInstantiatable() {
        final var badge = new StateBadgeWidget("TEST");

        assertThat(badge).isNotNull();
    }
}