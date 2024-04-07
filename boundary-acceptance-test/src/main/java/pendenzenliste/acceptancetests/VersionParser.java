package pendenzenliste.acceptancetests;

import java.util.Optional;

/**
 * A utility class that can be used to parse the version from a string.
 * <p>
 * This supports the format of e.g. postgres:16.2. Where
 */
public class VersionParser {

    /**
     * Parses the given value.
     *
     * @param value The value that should be parsed.
     * @return The parsed version number.
     */
    public static ToolVersion parse(final String value) {
        final String[] token = value.split(":", 2);

        if (token.length == 2) {
            return new ToolVersion(token[0], Optional.of(token[1]));
        } else {
            return new ToolVersion(value, Optional.empty());
        }
    }
}
