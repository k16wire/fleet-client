package com.pragmaticstory.fleet.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import com.pragmaticstory.fleet.client.messages.MachineEntity;
import com.pragmaticstory.fleet.client.messages.ObjectMapperProvider;
import com.pragmaticstory.fleet.client.messages.UnitEntity;
import play.Logger;
import play.libs.Json;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.pragmaticstory.fleet.client.WS2.*;
import static java.lang.System.getenv;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * User: 1001923
 * Date: 15. 4. 23.
 * Time: 오후 2:05
 */
public class DefaultFleetClient implements FleetClient{
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 49153;

    private static final String VERSION = "v1";

    public static final long NO_TIMEOUT = 0;
    private static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = SECONDS.toMillis(5);
    private static final long DEFAULT_READ_TIMEOUT_MILLIS = SECONDS.toMillis(30);

    private static final String GET = "get";

    private final URI uri;

    public DefaultFleetClient(final String uri){
        this(URI.create(uri));
    }

    public DefaultFleetClient(final URI uri){
        this(new Builder().uri(uri));
    }

    protected DefaultFleetClient(final Builder builder){
        this.uri = builder.uri;
    }

    @Override
    public List<MachineEntity> listMachines() throws FleetException{
        Json.setObjectMapper(ObjectMapperProvider.objectMapper());
        List<MachineEntity> machineEntityList = Lists.newArrayList();

        ResponseResult result = null;
        result = request(GET,
            resource().path("machines"),
            DEFAULT_READ_TIMEOUT_MILLIS);

        Iterator<JsonNode> machineNodes =
                result.body().get("machines").elements();
        while(machineNodes.hasNext()){
            JsonNode machine = machineNodes.next();
            machineEntityList.add(Json.fromJson(machine, MachineEntity.class));
        }
        return machineEntityList;
    }

    @Override
    public void createUnit(UnitEntity unitEntity, String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Before send a request, it check connection is available.
     *
     * @param method
     * @param resource
     * @param timeout
     * @return
     * @throws FleetException
     */
    public ResponseResult request(String method, Resource resource, long timeout) throws FleetException {
        Logger.debug(method + ":" + resource.uri());

        try{
            get(resource(), DEFAULT_CONNECT_TIMEOUT_MILLIS);
        }catch (Exception e){
            throw new FleetRequestException(method, resource().uri(),
                400, "Fleet server is not available", e);
        }

        ResponseResult result = null;
        try{
            switch (method){
                case "get":
                    result = get(resource, timeout);
                    break;
            }
        }catch(Exception e){
            if(result==null)
                throw new FleetRequestException(method, resource.uri(), e);
            else
                throw new FleetRequestException(method, resource.uri(),
                        result.statusCode, result.statusText, e);
        }

        switch (result.statusCode){
            case 404:
            case 405:
                throw new FleetException(result.statusText);
        }

        return result;
    }

    private Resource resource(){
        return new Resource(uri).path("fleet").path(VERSION);
    }

    private static String defaultEndPoint(){
        return DEFAULT_HOST + ":" + DEFAULT_PORT;
    }

    public static Builder fromEnv() {
        final String endpoint = fromNullable(getenv("FLEET_HOST")).or(defaultEndPoint());
        final String stripped = endpoint.replaceAll(".*://", "");
        final HostAndPort hostAndPort = HostAndPort.fromString(stripped);
        final String hostText = hostAndPort.getHostText();
        final int port = hostAndPort.getPortOrDefault(DEFAULT_PORT);
        final String address = isNullOrEmpty(hostText) ? DEFAULT_HOST : hostText;

        final Builder builder = new Builder();
        builder.uri("http" + "://" + address + ":" + port);

        return builder;
    }

    public static class Builder {
        private URI uri;

        public Builder uri(final URI uri){
            this.uri = uri;
            return this;
        }

        public Builder uri(final String uri){
            return uri(URI.create(uri));
        }

        public DefaultFleetClient build() {
            return new DefaultFleetClient(this);
        }
    }
}
