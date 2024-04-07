package pendenzenliste.acceptancetests;

import java.util.Optional;

/**
 * A record that can be used to represent a tool and its version.
 *
 * @param tool    The name of the tool that should be used.
 * @param version The version of the tool that should be used.
 */
public record ToolVersion(String tool, Optional<String> version) {
}
