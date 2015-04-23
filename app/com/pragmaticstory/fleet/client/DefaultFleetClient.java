package com.pragmaticstory.fleet.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import com.pragmaticstory.fleet.client.messages.MachineEntity;
import com.pragmaticstory.fleet.client.messages.ObjectMapperProvider;
import com.pragmaticstory.fleet.client.messages.UnitEntity;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSClient;

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


    private final WSClient client;
    private final URI uri;

    protected DefaultFleetClient(final Builder builder){
        this.client = WS.client();
        this.uri = builder.uri;
    }

    @Override
    public List<MachineEntity> listMachines() throws FleetException{
        Json.setObjectMapper(ObjectMapperProvider.objectMapper());
        List<MachineEntity> machineEntityList = Lists.newArrayList();

        ResponseResult result =
            request(GET, resource().path("machines"), DEFAULT_READ_TIMEOUT_MILLIS);
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

        public URI uri(){
            return uri;
        }

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
