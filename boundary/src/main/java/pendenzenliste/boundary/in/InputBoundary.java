package pendenzenliste.boundary.in;

import pendenzenliste.boundary.out.OutputBoundary;
import pendenzenliste.boundary.out.Response;

/**
 * The common interface for objects that act as an input boundary.
 */
public interface InputBoundary<REQ, RES extends Response<OUT, RES>, OUT extends OutputBoundary<OUT, RES>>
{
  /**
   * Executes the given request.
   *
   * @param request The request
   */
  void execute(REQ request);
}
