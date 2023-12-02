package pendenzenliste.cli;

/**
 * The result of a command line command.
 */
public class CommandLineResult {

    /**
     * Writes a message to the command line.
     *
     * @param message The message.
     */
    public void write(final String message) {
        System.out.println(message);
    }

    /**
     * Writes a newline character.
     */
    public void writeNewLine() {
        System.out.println();
    }

    /**
     * Exits the program with a non-error code.
     */
    public void exitSuccessful() {
        System.exit(0);
    }

    /**
     * Exits the programm with an exit code indicating a general failure.
     */
    public void exitGeneralFailure() {
        System.exit(1);
    }
}
