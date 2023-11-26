package pendenzenliste.achievements.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AchievementFactoryTest {

    @Test
    public void initialAchievementsShouldBeLocked() {
        assertThat(AchievementFactory.initialAchievements())
                .allMatch(AchievementAggregate::isLocked);
    }
}