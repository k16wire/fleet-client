package k16wire.fleet.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import k16wire.fleet.client.messages.ObjectMapperProvider;
import play.libs.Json;

/**
 * Created by 1001923 on 15. 6. 9..
 */
public class Json2 extends Json {
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

    public static JsonNode toJson(Object var0) {
        setObjectMapper(ObjectMapperProvider.objectMapper());
        return Json.toJson(var0);
    }

    public static <A> A fromJson(JsonNode var0, Class<A> var1) {
        setObjectMapper(ObjectMapperProvider.objectMapper());
        return Json.fromJson(var0, var1);
    }

    public static <A> A fromJson(JsonNode var0, TypeReference valueTypeRef) {
        setObjectMapper(ObjectMapperProvider.objectMapper());
        try {
            return mapper().readValue(var0.toString(), valueTypeRef);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }
}
