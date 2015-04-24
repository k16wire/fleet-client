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

/**
 * User: 1001923
 * Date: 15. 3. 18.
 * Time: 오후 1:58
 */
public class WS2 extends WS{
    public static ResponseResult get(Resource resource,
                                     long timeout)
            throws FleetRequestException{
        F.Promise<ResponseResult> responseResultPromise = url(resource.uri().toString())
                .get()
                .map(new WSResponseResponseResultFunction());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult put(Resource resource, long timeout, JsonNode body)
            throws FleetRequestException{
        F.Promise<ResponseResult> responseResultPromise = url(resource.uri().toString())
                .put(body)
                .map(new WSResponseResponseResultFunction());
        return responseResultPromise.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static ResponseResult delete(Resource resource,
                                        long timeout)
            throws FleetRequestException{
        F.Promise<ResponseResult> responseResultPromise = url(resource.uri().toString())
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

    public static class Resource {
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
