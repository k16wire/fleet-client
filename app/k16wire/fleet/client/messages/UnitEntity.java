package k16wire.fleet.client.messages;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * Created with IntelliJ IDEA.
 * User: 1001923
 * Date: 15. 4. 23.
 * Time: 오후 2:04
 */
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class UnitEntity {
    @JsonProperty("desiredState") private String desiredState;
    @JsonProperty("options") private List<UnitOption> options;

    public UnitEntity(){
    }

    public String getDesiredState(){
        return this.desiredState;
    }

    public void setDesiredState(String desiredState){
        this.desiredState = desiredState;
    }

    public void setOptions(List<UnitOption> options){
        this.options = options;
    }
}
