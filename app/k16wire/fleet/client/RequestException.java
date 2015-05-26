package k16wire.fleet.client;

import java.net.URI;

/**
 * Date: 15. 4. 23.
 * Time: 오후 7:15
 */
public class RequestException extends FleetException {
    private final String method;
    private final URI uri;
    private final int status;
    private final String message;

    public RequestException(final String method, final URI uri, final Throwable cause) {
        this(method, uri, 0, null, cause);
    }

    public RequestException(final String method, final URI uri,
                            final int status,
                            final Throwable cause) {
        this(method, uri, status, null, cause);
    }

    public RequestException(final String method, final URI uri) {
        this(method, uri, 0, null, null);
    }

    public RequestException(final String method, final URI uri,
                            final int status) {
        this(method, uri, status, null, null);
    }

    public RequestException(final String method, final URI uri,
                            final int status, final String message) {
        this(method, uri, status, message, null);
    }

    public RequestException(final String method, final URI uri,
                            final int status, final String message,
                            final Throwable cause) {
        super("Request error: " + method + " " + uri + ": " + status, cause);
        this.method = method;
        this.uri = uri;
        this.status = status;
        this.message = message;
    }

    public int status() {
        return status;
    }
}
