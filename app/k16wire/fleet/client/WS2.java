package k16wire.fleet.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * User: 1001923
 * Date: 15. 3. 18.
 * Time: 오후 1:58
 */
public class WS2 extends WS{
    public static ResponseResult get(String url,
                                     long timeout)
            throws FleetRequestException {
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .get()
                .map(new WSResponseResponseResultFunction());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult post(String url, long timeout, JsonNode body)
            throws FleetRequestException{
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .post(body)
                .map(new WSResponseResponseResultFunction());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult put(String url, long timeout, JsonNode body)
            throws FleetRequestException{
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .put(body)
                .map(new WSResponseResponseResultFunction());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult delete(String url,
                                        long timeout)
            throws FleetRequestException{
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .delete()
                .map(new WSResponseResponseResultFunction());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    private static class WSResponseResponseResultFunction implements F.Function<WSResponse, ResponseResult> {
        @Override
        public ResponseResult apply(WSResponse wsResponse) throws Throwable {
            Logger.debug(wsResponse.getStatus()+":"+wsResponse.getStatusText());
            Logger.debug(wsResponse.getBody());
            ResponseResult responseResult = new ResponseResult();
            responseResult.statusCode = wsResponse.getStatus();
            responseResult.statusText = wsResponse.getStatusText();
            responseResult.body = wsResponse.getBody();
            return responseResult;
        }
    }

    public static class ResponseResult {
        public int statusCode;
        public String statusText;
        public String body;

        public JsonNode body(){
            return Json.parse(body);
        }

        public ObjectNode asJson(){
            ObjectNode objectNode = Json.newObject();
            objectNode.put("statusCode",statusCode);
            objectNode.put("statusText",statusText);
            return objectNode;
        }
    }
}
