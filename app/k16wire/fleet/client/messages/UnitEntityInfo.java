package k16wire.fleet.client.messages;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * User: 1001923
 * Date: 15. 4. 27.
 * Time: 오후 2:27
 */
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class UnitEntityInfo {
    @JsonProperty("currentState") private String currentState;
    @JsonProperty("desiredState") private String desiredState;
    @JsonProperty("machineID") private String machineID;
    @JsonProperty("name") private String name;
    @JsonProperty("options") private List<UnitOption> options;

    public String currentState(){
        return this.currentState;
    }

    public String desiredState(){
        return this.desiredState;
    }

    public String machineID(){
        return this.machineID;
    }

    public String name(){
        return this.name;
    }

    public List<UnitOption> options(){
        return options;
    }
}
