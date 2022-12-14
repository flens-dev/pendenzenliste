package pendenzenliste.ports.in;

import pendenzenliste.ports.out.OutputBoundary;
import pendenzenliste.ports.out.Response;

/**
 * The common interface for objects that act as an input boundary.
 */
public interface InputBoundary<REQ, RES extends Response<OUT, RES>, OUT extends OutputBoundary<OUT, RES>>
{
  /**
   * Executes the given request and applies the result to the given output boundary.
   *
   * @param request        The request that should be executed.
   * @param outputBoundary The output boundary that hte result should be applied to.
   */
  default void execute(REQ request, OUT outputBoundary)
  {
    execute(request).applyTo(outputBoundary);
  }

  /**
   * Executes the given request.
   *
   * @param request The request that should be executed.
   * @return The response to the request.
   */
  RES execute(REQ request);
}
