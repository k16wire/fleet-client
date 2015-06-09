package k16wire.fleet.client;

import play.utils.UriEncoding;

import java.net.URI;
import java.nio.charset.Charset;

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

    public Resource path(String path, Charset charsets){
        return path(URIencode(path, charsets));
    }

    public Resource path(String path){
        this.uri = URI.create(uri.toString() + "/" + path);
        return this;
    }

    public Resource query(String key, String value, Charset charsets){
        return query(key, URIencode(value,charsets));
    }

    public Resource query(String key, String value){
        this.uri = URI.create(uri.toString() + "?"+key+"="+value);
        return this;
    }

    public Resource query(String key, boolean value){
        this.uri = URI.create(uri.toString() + "?"+key+"="+value);
        return this;
    }

    public Resource param(String key, int value){
        this.uri = URI.create(uri.toString() + "&"+key+"="+value);
        return this;
    }
    public Resource param(String key, String value, Charset charsets){
        return param(key, URIencode(value,charsets));
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

    public static String URIencode (String s, Charset charsets){
        return UriEncoding.encodePathSegment(s, charsets.name());
    }
}
