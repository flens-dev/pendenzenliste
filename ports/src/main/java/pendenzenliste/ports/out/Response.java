package pendenzenliste.ports.out;

/**
 * The common interface for objects that represent the response to a request.
 */
public interface Response
{
  /**
   * Applies the response to the given output boundary.
   *
   * @param out The output port.
   */
  void applyTo(OutputBoundary out);
}
