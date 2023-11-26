package pendenzenliste.achievements.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class AchievementEventEntityTest {

    @Test
    public void withIdentity() {
        final var event =
                new AchievementUnlockedEvent(LocalDateTime.now(),
                        AchievementValueType.ACHIEVEMENT_HUNTER);

        final var entity =
                new AchievementEventEntity(IdentityValueObject.random(), event);

        final var copy = entity.withIdentity(new IdentityValueObject("copy"));

        final var assertions = new SoftAssertions();

        assertions.assertThat(copy.event()).isEqualTo(event);
        assertions.assertThat(copy.identity()).isEqualTo(new IdentityValueObject("copy"));

        assertions.assertAll();
    }
}