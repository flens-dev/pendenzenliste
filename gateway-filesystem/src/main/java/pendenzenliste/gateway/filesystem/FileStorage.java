package pendenzenliste.gateway.filesystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * A helper class that abstracts away accessing and writing serializable data to/from files.
 *
 * @param <T> The type of the stored data.
 */
public class FileStorage<T>
{
  private FileTime lastModified = null;

  private final String path;

  private T cachedData;

  /**
   * Creates a new instance.
   *
   * @param path The path.
   */
  public FileStorage(final String path)
  {
    this.path = requireNonNull(path, "The path may not be null");
  }

  /**
   * Retrieves the stored data from the storage.
   *
   * @param emptyState The empty state that should be returned when no data has been stored.
   * @return The stored data.
   */
  public T getOr(final T emptyState)
  {
    loadIfNecessary();

    return Optional.ofNullable(cachedData).orElse(emptyState);
  }

  /**
   * Flushes the store to the disk.
   */
  public void flushToDisk(final T data)
  {
    final Path path = Path.of(this.path);

    if (!Files.exists(path))
    {
      try
      {
        Files.createFile(path);
      } catch (final IOException e)
      {
        throw new RuntimeException(e);
      }
    }

    try (final ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(this.path)))
    {
      os.writeObject(data);
    } catch (final IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Loads the contents of the file if necessary.
   */
  private void loadIfNecessary()
  {
    boolean hasBeenModified = false;

    try
    {
      final var currentTimestamp = Files.getLastModifiedTime(Path.of(path));

      hasBeenModified = !Objects.equals(currentTimestamp, lastModified);
    } catch (final IOException e)
    {
      lastModified = null;
    }

    if (hasBeenModified)
    {
      try (final ObjectInputStream in = new ObjectInputStream(new FileInputStream(path)))
      {
        cachedData = (T) in.readObject();
      } catch (final FileNotFoundException e)
      {
        e.printStackTrace();
      } catch (final IOException | ClassNotFoundException e)
      {
        throw new RuntimeException(e);
      }
    }
  }
}
