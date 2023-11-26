package pendenzenliste.domain.util;

import java.util.Collection;

/**
 * An interface that can be used for objects that have capabilities that describe which actions may
 * be invoked in the present instance.
 *
 * @param <C> The type of the capability.
 */
public interface HasCapabilities<C extends Enum<C>>
{
  /**
   * The capabilities of the object.
   *
   * @return The capabilities.
   */
  Collection<C> capabilities();

  /**
   * Checks whether the object has the given capability.
   *
   * @param capability The capability that should be checked.
   * @return True if the object has the capability otherwise false.
   */
  default boolean has(final C capability)
  {
    return capabilities().contains(capability);
  }

  /**
   * Checks whether the object does not have the given capability.
   *
   * @param capability The capability that should be checked.
   * @return True if the object does not have the capability otherwise false.
   */
  default boolean doesNotHave(final C capability)
  {
    return !has(capability);
  }
}
