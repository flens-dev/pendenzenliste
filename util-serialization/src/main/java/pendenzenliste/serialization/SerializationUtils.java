package pendenzenliste.serialization;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class defines various utilities used to serialize/deserialize java objects.
 */
public final class SerializationUtils {
    /**
     * Private constructor to prevent instantiation.
     */
    private SerializationUtils() {
    }

    /**
     * Serializes the given object.
     *
     * @param object The object.
     * @return The serialized object.
     */
    public static byte[] serializeObject(final Object object) {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (final ObjectOutputStream os = new ObjectOutputStream(out)) {
                os.writeObject(object);
            }

            return out.toByteArray();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes the data into the requested type.
     *
     * @param data The data.
     * @param type The type.
     * @param <T>  The type of the deserialized object.
     * @return The deserialized object
     */
    public static <T> T deserializeObject(final byte[] data, final Class<T> type) {
        if (data != null) {
            try (final ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data))) {
                return type.cast(in.readObject());
            } catch (final IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    /**
     * Deserializes the map from the given data.
     *
     * @param data      The data.
     * @param keyType   The key type.
     * @param valueType The value type.
     * @param <K>       The key type.
     * @param <V>       The value type.
     * @return The deserialized map.
     */
    public static <K, V> Map<K, V> deserializeMap(final byte[] data, final Class<K> keyType,
                                                  final Class<V> valueType) {
        if (data != null) {
            try (final ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data))) {
                return (Map<K, V>) in.readObject();
            } catch (final IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return new ConcurrentHashMap<>();
    }
}
