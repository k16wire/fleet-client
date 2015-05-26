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

    protected static final long NO_TIMEOUT = 0;
    protected static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = SECONDS.toMillis(5);
    protected static final long DEFAULT_READ_TIMEOUT_MILLIS = SECONDS.toMillis(30);

    protected URI uri;

    protected static class Resource {
        private URI uri;

        public Resource(URI uri){
            this.uri = uri;
        }

        public URI uri(){
            return this.uri;
        }

        public Resource path(String path){
            this.uri = URI.create(uri.toString() + "/" + path);
            return this;
        }

        public Resource query(String key, String value){
            this.uri = URI.create(uri.toString() + "?"+key+"="+value);
            return this;
        }

        public Resource param(String key, String value){
            this.uri = URI.create(uri.toString() + "&"+key+"="+value);
            return this;
        }

        public String url(){
            return this.uri.toString();
        }
    }
}
