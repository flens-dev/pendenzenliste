package pendenzenliste.domain.util;

/**
 * The common interface for objects that represent entities.
 *
 * @param <I> The type of the entities' identity.
 */
public interface Entity<I> {

    /**
     * Retrieves the identity of the entity.
     *
     * @return The identity.
     */
    I identity();
}
