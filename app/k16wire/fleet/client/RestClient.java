package k16wire.fleet.client;

import java.net.URI;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by 1001923 on 15. 5. 22..
 */
public class RestClient {
    protected static final String GET = "get";
    protected static final String POST = "post";
    protected static final String PUT = "put";
    protected static final String DELETE = "delete";
    protected static final String OPTIONS = "options";

    protected static final long NO_TIMEOUT = SECONDS.toMillis(1000);
    protected static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = SECONDS.toMillis(5);
    protected static final long DEFAULT_READ_TIMEOUT_MILLIS = SECONDS.toMillis(30);
    protected static final long DEFAULT_WRITE_TIMEOUT_MILLIS = SECONDS.toMillis(120);

    protected URI uri;

    protected Resource resource(){
        return new Resource(uri);
    }
}
