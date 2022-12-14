package pendenzenliste.ports.in;

import pendenzenliste.ports.out.OutputBoundary;

/**
 * The common interface for objects that act as an input boundary.
 */
public interface InputBoundary<REQ, OUT extends OutputBoundary>
{
  /**
   * Executes the given request and applies the result to the given output boundary.
   *
   * @param request        The request that should be executed.
   * @param outputBoundary The output boundary that hte result should be applied to.
   */
  void execute(REQ request, OUT outputBoundary);
}
