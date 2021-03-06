package k16wire.fleet.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import play.Logger;
import play.libs.F;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import java.util.concurrent.TimeUnit;

/**
 * User: 1001923
 * Date: 15. 3. 18.
 * Time: 오후 1:58
 */
public class WS2 extends WS{
    public static void get(WSClient client, String url) throws RequestException {
        client.url(url).get();
    }

    public static ResponseResult get(String url, long timeout)
            throws RequestException {
        return get(client(), url, timeout);
    }

    public static ResponseResult get(WSClient client,String url,
                                     long timeout)
            throws RequestException {
        F.Promise<ResponseResult> responseResultPromise = client.url(url)
                .get()
                .map(new WSResponseResult());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult post(String url, long timeout, JsonNode body)
            throws RequestException{
        return post(client(), url, timeout, body);
    }

    public static ResponseResult post(WSClient client,String url, long timeout, JsonNode body)
            throws RequestException {
        F.Promise<ResponseResult> responseResultPromise = client.url(url)
                .post(body)
                .map(new WSResponseResult());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult put(String url, long timeout, JsonNode body)
            throws RequestException {
        return put(client(), url, timeout, body);
    }

    public static ResponseResult put(WSClient client,String url, long timeout, JsonNode body)
            throws RequestException {
        F.Promise<ResponseResult> responseResultPromise = client.url(url)
                .put(body)
                .map(new WSResponseResult());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult delete(String url, long timeout)
            throws RequestException {
        return delete(client(), url, timeout);
    }

    public static ResponseResult delete(WSClient client,String url,
                                        long timeout)
            throws RequestException {
        F.Promise<ResponseResult> responseResultPromise = client.url(url)
                .delete()
                .map(new WSResponseResult());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult options(String url, long timeout)
            throws RequestException {
        return options(client(), url, timeout);
    }

    public static ResponseResult options(WSClient client,String url, long timeout)
            throws RequestException {
        F.Promise<ResponseResult> responseResultPromise = client.url(url)
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
}
