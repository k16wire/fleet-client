package k16wire.fleet.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.io.File;

/**
 * Created by 1001923 on 15. 5. 26..
 */
public class ResponseResult {
    public int statusCode;
    public String statusText;
    public String body;
    public File file;

    public ResponseResult(){
    }

    public ResponseResult(int statusCode, String statusText){
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public JsonNode body(){
        return Json.parse(body);
    }

    public ObjectNode status(){
        ObjectNode objectNode = Json.newObject();
        objectNode.put("statusCode",statusCode);
        objectNode.put("statusText",statusText);
        return objectNode;
    }
}
