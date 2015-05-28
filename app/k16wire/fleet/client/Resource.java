package k16wire.fleet.client;

import java.net.URI;

/**
 * Created by 1001923 on 15. 5. 28..
 */
public class Resource {
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

    public Resource query(String key, boolean value){
        this.uri = URI.create(uri.toString() + "?"+key+"="+value);
        return this;
    }

    public Resource param(String key, String value){
        this.uri = URI.create(uri.toString() + "&"+key+"="+value);
        return this;
    }

    public Resource param(String key, boolean value){
        this.uri = URI.create(uri.toString() + "&"+key+"="+value);
        return this;
    }

    public String url(){
        return this.uri.toString();
    }
}
