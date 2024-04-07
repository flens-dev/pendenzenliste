package pendenzenliste.acceptancetests;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class VersionParserTest {

    @Test
    public void parse_withToolAndVersion() {
        ToolVersion toolVersion = VersionParser.parse("tool:version");

        final var assertions = new SoftAssertions();

        assertions.assertThat(toolVersion.tool()).isEqualTo("tool");
        assertions.assertThat(toolVersion.version()).hasValue("version");

        assertions.assertAll();
    }

    @Test
    public void parse_withToolAndNoVersion() {
        ToolVersion toolVersion = VersionParser.parse("tool");

        final var assertions = new SoftAssertions();

        assertions.assertThat(toolVersion.tool()).isEqualTo("tool");
        assertions.assertThat(toolVersion.version()).isEmpty();

        assertions.assertAll();
    }
}