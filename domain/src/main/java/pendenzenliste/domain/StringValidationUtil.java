package pendenzenliste.domain;

/**
 * A utility class that can be used to perform various validations on string values.
 */
public class StringValidationUtil
{
  /**
   * Private constructor to prevent instantiation.
   */
  private StringValidationUtil()
  {
  }

  /**
   * Expects the given string to be not null.
   *
   * @param value The value that should be checked.
   * @throws IllegalArgumentException Will be thrown in case that the given value is null.
   */
  public static void expectNotNull(final String value)
  {
    if (value == null)
    {
      throw new IllegalArgumentException("The value may not be null");
    }
  }

  /**
   * Expects the given string to be not empty.
   *
   * @param value The value that should be checked.
   * @throws IllegalArgumentException Will be thrown in case that the given value is empty.
   */
  public static void expectNotEmpty(final String value)
  {
    if (value.isEmpty())
    {
      throw new IllegalArgumentException("The value may not be empty");
    }
  }

  /**
   * Expects the given string to be shorter or equal to the given max length.
   *
   * @param value     The value that should be checked.
   * @param maxLength The maximum length of the string.
   * @throws IllegalArgumentException Will be thrown in case that the given value is longer than the max length parameter.
   */
  public static void expectMaxLength(final String value, final Integer maxLength)
  {
    if (value.length() > maxLength)
    {
      throw new IllegalArgumentException(
          String.format(
              "The value may not be longer than %d characters. (The given value was %d characters long)",
              maxLength,
              value.length())
      );
    }
  }

  /**
   * Expects the given string to not contain any line breaks.
   *
   * @param value The value that should be checked.
   * @throws IllegalArgumentException Will be thrown in case that the given value contains one or more line breaks.
   */
  public static void expectNoLineBreaks(final String value)
  {
    if (value.contains("\n"))
    {
      throw new IllegalArgumentException("The value may not contain line breaks");
    }
  }
}
