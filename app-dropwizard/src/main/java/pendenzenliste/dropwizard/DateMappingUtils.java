package pendenzenliste.dropwizard;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * A utility class that can be used to map date types.
 */
public final class DateMappingUtils
{
  /**
   * Private constructor to prevent instantiation.
   */
  private DateMappingUtils()
  {
  }

  /**
   * Maps the given local date time to a date.
   *
   * @param localDateTime The local date time.
   * @return The date.
   */
  public static Date toDate(final LocalDateTime localDateTime)
  {
    if (localDateTime == null)
    {
      return null;
    } else
    {
      return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
  }
}
