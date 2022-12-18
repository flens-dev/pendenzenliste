package pendenzenliste.boundary.out;

/**
 * The common interface for objects that represent the response to a request.
 */
public interface Response<OUT extends OutputBoundary<OUT, RES>, RES extends Response<OUT, RES>>
{
  /**
   * Applies the response to the given output boundary.
   *
   * @param out The output port.
   */
  void applyTo(OUT out);
}
