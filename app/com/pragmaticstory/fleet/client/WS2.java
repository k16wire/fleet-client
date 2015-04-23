package com.pragmaticstory.fleet.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * User: 1001923
 * Date: 15. 3. 18.
 * Time: 오후 1:58
 */
public class WS2 extends WS{
    private static final long DEFAULT_READ_TIMEOUT_MILLIS = SECONDS.toMillis(30);

    public static ResponseResult delete(String url){
        Logger.debug("delete:"+url);
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .delete()
                .map(new WSResponseResponseResultFunction());
        return responseResultPromise.get(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult put(JsonNode body, String url){
        Logger.debug("put:"+url);
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .put(body)
                .map(new WSResponseResponseResultFunction());
        return responseResultPromise.get(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult get(String url){
        Logger.debug("get:"+url);
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .get()
                .map(new WSResponseResponseResultFunction());
        return responseResultPromise.get(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult post(JsonNode body, String url, long timeout){
        Logger.debug("post:"+url);
        F.Promise<ResponseResult> responseResultPromise = url(url)
                .post(body)
                .map(new WSResponseResponseResultFunction());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult post(JsonNode body, String url){
        return post(body, url, DEFAULT_READ_TIMEOUT_MILLIS);
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

    public static class Resource {
        private URI uri;

        public Resource(URI uri){
            this.uri = uri;
        }

        public Resource path(String path){
            this.uri = URI.create(uri.toString() + "/" + path);
            return this;
        }
    }

    public static class ResponseResult {
        public int statusCode;
        public String statusText;
        public String body;

        ResponseResult(){
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
}
