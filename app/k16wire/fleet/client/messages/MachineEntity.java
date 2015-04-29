package k16wire.fleet.client.messages;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * Date: 15. 4. 23.
 * Time: 오후 2:03
 */
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class MachineEntity {
    @JsonProperty("id") private String id;
    @JsonProperty("primaryIP") private String primaryIP;

    public String primaryIP(){
        return this.primaryIP;
    }
}
