package k16wire.fleet.client;

/**
 * Date: 15. 4. 23.
 * Time: 오후 4:21
 */
public class FleetException extends Exception{
    public FleetException(final String message) {
        super(message);
    }

    public FleetException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FleetException(final Throwable cause) {
        super(cause);
    }
}
