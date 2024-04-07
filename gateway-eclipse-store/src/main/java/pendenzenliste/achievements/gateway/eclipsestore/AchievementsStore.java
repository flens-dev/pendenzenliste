package pendenzenliste.achievements.gateway.eclipsestore;

import pendenzenliste.achievements.model.AchievementAggregate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The achievements store used for integration with eclipse store.
 */
public class AchievementsStore {

    private final Collection<AchievementAggregate> achievements =
            new ArrayList<>();

    /**
     * Retrieves the achievements.
     *
     * @return The achievements.
     */
    public List<AchievementAggregate> getAchievements() {
        return new ArrayList<>(achievements);
    }

    /**
     * Sets the achievements.
     *
     * @param achievements The achievements.
     */
    public void setAchievements(final Collection<AchievementAggregate> achievements) {
        this.achievements.clear();
        this.achievements.addAll(achievements);
    }
}
