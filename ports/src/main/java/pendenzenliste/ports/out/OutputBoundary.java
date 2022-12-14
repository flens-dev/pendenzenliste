package pendenzenliste.ports.out;

/**
 * The common interface for objects that act as an output boundary.
 */
public interface OutputBoundary<OUT extends OutputBoundary<OUT, RES>, RES extends Response<OUT, RES>>
{
}
