package com.pragmaticstory.fleet.client.messages;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
/**
 * Date: 15. 3. 30.
 * Time: 오후 5:46
 */
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class UnitOption {
    @JsonProperty("section") private String section;
    @JsonProperty("name") private String name;
    @JsonProperty("value") private String value;

    public UnitOption(){
    }

    public UnitOption(String section, String name, String value){
        this.section = section;
        this.name = name;
        this.value = value;
    }
}
