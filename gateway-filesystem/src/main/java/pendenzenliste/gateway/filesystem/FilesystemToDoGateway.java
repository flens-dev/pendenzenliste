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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import pendenzenliste.domain.IdentityValueObject;
import pendenzenliste.domain.ToDoEntity;
import pendenzenliste.gateway.ToDoGateway;

/**
 * A gateway that provides access to todos stored in the filesystem.
 */
public class FilesystemToDoGateway implements ToDoGateway
{
  private FileTime lastModified = null;

  private final String path;

  private final Map<IdentityValueObject, ToDoEntity> cache = new ConcurrentHashMap<>();

  /**
   * Creates a new instance.
   *
   * @param path The path.
   */
  public FilesystemToDoGateway(final String path)
  {
    this.path = path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ToDoEntity> findById(final IdentityValueObject id)
  {
    loadIfNecessary();

    return Optional.ofNullable(cache.getOrDefault(id, null));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean delete(final IdentityValueObject id)
  {
    final var removed = cache.remove(id);

    if (removed != null)
    {
      flushToDisk();
    }

    return removed != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void store(final ToDoEntity todo)
  {
    cache.put(todo.identity(), todo);

    flushToDisk();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Stream<ToDoEntity> fetchAll()
  {
    loadIfNecessary();

    return cache.values().stream();
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
        cache.putAll((Map<IdentityValueObject, ToDoEntity>) in.readObject());
      } catch (final FileNotFoundException e)
      {
        e.printStackTrace();
      } catch (final IOException | ClassNotFoundException e)
      {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Flushes the store to the disk.
   */
  private void flushToDisk()
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
      os.writeObject(cache);
    } catch (final IOException e)
    {
      throw new RuntimeException(e);
    }
  }
}
