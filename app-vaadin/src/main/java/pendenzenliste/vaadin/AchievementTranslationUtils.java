package pendenzenliste.vaadin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A utility class that provides means to translate achievements.
 */
public final class AchievementTranslationUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private AchievementTranslationUtils() {
    }

    private static final Map<String, String> TITLES = new ConcurrentHashMap<>();
    private static final Map<String, String> DESCRIPTION = new ConcurrentHashMap<>();

    static {
        TITLES.put("JOURNEY_BEGINS", "The journey of a thousand miles begins with one step");
        DESCRIPTION.put("JOURNEY_BEGINS", "Create your first todo");

        TITLES.put("DONEZO", "Donezo!");
        DESCRIPTION.put("DONEZO", "Complete your first todo");

        TITLES.put("NEW_YEAR_NEW_ME", "New year, new me!");
        DESCRIPTION.put("NEW_YEAR_NEW_ME", "Create a todo on new year");

        TITLES.put("IT_BURNS", "Oh, it burns!");
        DESCRIPTION.put("IT_BURNS", "Complete ten todos in one week");

        TITLES.put("THIRD_TIMES_THE_CHARM", "Third time's the charm");
        DESCRIPTION.put("THIRD_TIMES_THE_CHARM", "Reopen a todo for the third time");

        TITLES.put("ACHIEVEMENT_HUNTER", "Achievement hunter");
        DESCRIPTION.put("ACHIEVEMENT_HUNTER", "Collect three achievements");

        TITLES.put("ALL_DONE", "All done!");
        DESCRIPTION.put("ALL_DONE", "You did it! You completed all of your todos!");

        TITLES.put("LAZY_DOG", "You lazy dog!");
        DESCRIPTION.put("LAZY_DOG", "Leave one of your todos open for three months");
    }

    /**
     * Retrieves the title of the given achievement type.
     *
     * @param achievementType The achievement type.
     * @return The title of the achievement.
     */
    public static String getTitleOf(final String achievementType) {
        return TITLES.getOrDefault(achievementType, achievementType);
    }

    /**
     * Retrieves the description of the given achievement type.
     *
     * @param achievementType The achievement type.
     * @return The description of the achievement.
     */
    public static String getDescriptionOf(final String achievementType) {
        return DESCRIPTION.getOrDefault(achievementType, achievementType);
    }

    /**
     * The locked title.
     *
     * @return The locked title.
     */
    public static String getLockedTitle() {
        return "Hidden";
    }

    /**
     * Retrieves the locked description.
     *
     * @return The locked description.
     */
    public static String getLockedDescription() {
        return "This achievement is hidden";
    }
}
