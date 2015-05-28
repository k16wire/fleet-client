package k16wire.fleet.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import java.util.concurrent.TimeUnit;

/**
 * User: 1001923
 * Date: 15. 3. 18.
 * Time: 오후 1:58
 */
public class WS2 extends WS{
    public static void get(String url) throws RequestException {
        url(url).get();
    }

    public static ResponseResult get(String url,
                                     long timeout)
            throws RequestException {
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .get()
                .map(new WSResponseResult());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult post(String url, long timeout, JsonNode body)
            throws RequestException {
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .post(body)
                .map(new WSResponseResult());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult put(String url, long timeout, JsonNode body)
            throws RequestException {
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .put(body)
                .map(new WSResponseResult());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult delete(String url,
                                        long timeout)
            throws RequestException {
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .delete()
                .map(new WSResponseResult());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult options(String url, long timeout)
            throws RequestException {
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .options()
                .map(new WSResponseResult());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    private static class WSResponseResult implements F.Function<WSResponse, ResponseResult> {
        @Override
        public ResponseResult apply(WSResponse wsResponse) throws Throwable {
            ResponseResult responseResult = new ResponseResult();
            responseResult.statusCode = wsResponse.getStatus();
            responseResult.statusText = wsResponse.getStatusText();

            Logger.info(wsResponse.getStatus() + ":" + wsResponse.getStatusText());

            if(!Strings.isNullOrEmpty(wsResponse.getBody())){
                Logger.debug(wsResponse.getBody());
                responseResult.body = wsResponse.getBody();
            }
            return responseResult;
        }
    }

    public static class Json2 {
        private ObjectNode objectNode;

        public JsonNode jsonNode(){
            return this.objectNode;
        }

        public Json2 put(String key, String value){
            this.objectNode.put(key, value);
            return this;
        }

        public static Json2 body(){
            Json2 json2 = new Json2();
            json2.objectNode = Json.newObject();
            return json2;
        }
    }
}
