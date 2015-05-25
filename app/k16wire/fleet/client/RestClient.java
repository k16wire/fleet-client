package k16wire.fleet.client;

import java.net.URI;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created with IntelliJ IDEA.
 * User: k16wire
 * Date: 15. 5. 26.
 * Time: 오전 12:12
 * To change this template use File | Settings | File Templates.
 */
public class RestClient {
    protected static final long NO_TIMEOUT = 0;
    protected static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = SECONDS.toMillis(5);
    protected static final long DEFAULT_READ_TIMEOUT_MILLIS = SECONDS.toMillis(30);

    protected static final String GET = "get";
    protected static final String POST = "post";
    protected static final String PUT = "put";
    protected static final String DELETE = "delete";

    protected URI uri;

    public static class Resource {
        private URI uri;

        public Resource(URI uri){
            this.uri = uri;
        }

        public URI uri(){
            return this.uri;
        }

        public String url(){
            return uri.toString();
        }

        public Resource path(String path){
            this.uri = URI.create(uri.toString() + "/" + path);
            return this;
        }
    }
}
