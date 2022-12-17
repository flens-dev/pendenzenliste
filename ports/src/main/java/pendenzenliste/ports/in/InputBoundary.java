package pendenzenliste.ports.in;

import pendenzenliste.ports.out.OutputBoundary;
import pendenzenliste.ports.out.Response;

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

  /**
   * Executes the given request.
   *
   * @param request The request that should be executed.
   * @return The response to the request.
   */
  RES executeRequest(REQ request);
}
