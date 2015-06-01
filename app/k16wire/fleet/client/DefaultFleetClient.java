package k16wire.fleet.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import k16wire.fleet.client.messages.MachineEntity;
import k16wire.fleet.client.messages.ObjectMapperProvider;
import k16wire.fleet.client.messages.UnitEntity;
import k16wire.fleet.client.messages.UnitEntityInfo;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WSClient;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Strings.isNullOrEmpty;
import static k16wire.fleet.client.WS2.*;
import static java.lang.System.getenv;
import static k16wire.fleet.client.WS2.Json2.*;

/**
 * User: 1001923
 * Date: 15. 4. 23.
 * Time: 오후 2:05
 */
public class DefaultFleetClient extends RestClient implements FleetClient {
    private static final String ENV_FLEET_HOST = "FLEET_HOST";

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 49153;
    private static final String VERSION = "v1";

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
    public List<MachineEntity> listMachines()
            throws FleetException {
        ResponseResult result = request(GET,
            resource().path("machines"),
            DEFAULT_READ_TIMEOUT_MILLIS);

        Json.setObjectMapper(ObjectMapperProvider.objectMapper());
        List<MachineEntity> machineEntityList = Lists.newArrayList();
        Iterator<JsonNode> machineNodes =
                result.body().get("machines").elements();
        while(machineNodes.hasNext()){
            JsonNode machine = machineNodes.next();
            machineEntityList.add(Json.fromJson(machine, MachineEntity.class));
        }
        return machineEntityList;
    }

    @Override
    public ResponseResult createUnit(UnitEntity unitEntity, String name)
            throws FleetException{
        ResponseResult result = request(PUT,
            resource().path("units").path(name),
            DEFAULT_READ_TIMEOUT_MILLIS,
            Json.toJson(unitEntity));
        switch(result.statusCode){
            case 201:
                result.statusText = "Created";
                break;
            case 204:
                result.statusText = "Existed";
                break;
        }
        return result;
    }

    @Override
    public ResponseResult destroyUnit(String name)
            throws FleetException {
        ResponseResult result = request(DELETE,
                resource().path("units").path(name),
                DEFAULT_READ_TIMEOUT_MILLIS);
        switch(result.statusCode){
            case 204:
                result.statusText = "DELETED";
                break;
        }
        return result;
    }

    @Override
    public ResponseResult modifyUnit(String name, String state) throws FleetException {
        return request(PUT,
                resource().path("units").path(name),
                DEFAULT_READ_TIMEOUT_MILLIS,
                body()
                        .put("desiredState", state)
                        .jsonNode());
    }

    @Override
    public List<UnitEntityInfo> listUnits() throws FleetException {
        ResponseResult result = request(GET,
                resource().path("units"),
                DEFAULT_READ_TIMEOUT_MILLIS);

        JsonNode units = result.body().get("units");

        List<UnitEntityInfo> unitEntityInfoList = Lists.newArrayList();
        Iterator<JsonNode> it = units.iterator();
        while(it.hasNext()){
            JsonNode unit = it.next();
            unitEntityInfoList.add(Json.fromJson(unit,UnitEntityInfo.class));
        }
        return unitEntityInfoList;
    }

    public ResponseResult request(String method, Resource resource, long timeout)
            throws FleetException {
        return request(method, resource, timeout, null);
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
    public ResponseResult request(String method, Resource resource, long timeout, JsonNode body)
            throws FleetException {
        Logger.debug(method + ":" + resource.uri());
        WSClient client = WS2.client();

        try{
            client.url(resource.url()).get();
        }catch (Exception e){
            client
            throw new RequestException(method, resource().uri(),
                400, "Fleet server is not available", e);
        }

        ResponseResult result = null;
        try{
            switch (method){
                case GET:
                    result = get(resource.url(), timeout);
                    break;
                case POST:
                    result = post(resource.url(), timeout, body);
                    break;
                case PUT:
                    result = put(resource.url(), timeout, body);
                    break;
                case DELETE:
                    result = delete(resource.url(), timeout);
                    break;
            }
        }catch(Exception e){
            if(result==null)
                throw new RequestException(method, resource.uri(), e);
            else
                throw new RequestException(method, resource.uri(),
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
        final String endpoint = fromNullable(getenv(ENV_FLEET_HOST)).or(defaultEndPoint());
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
