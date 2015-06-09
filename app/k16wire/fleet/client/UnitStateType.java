package k16wire.fleet.client;

import com.google.common.collect.Maps;

import java.util.TreeMap;

/**
 * User: k16wire
 * Date: 15. 4. 2.
 * Time: 오전 1:40
 */
public enum UnitStateType {
    INACTIVE("inactive"),
    LOADED("loaded"),
    LAUNCHED("launched");

    private String typeValue;
    public static final TreeMap<String, String> messages = Maps.newTreeMap();

    static {
        messages.put(INACTIVE.typeValue, "InActive");
        messages.put(LOADED.typeValue, "Loaded");
        messages.put(LAUNCHED.typeValue, "Launched");
    }

    UnitStateType(String typeValue){
        this.typeValue = typeValue;
    }

    public String typeValue(){
        return this.typeValue;
    }

    public static String message(String typeValue) {
        return messages.get(typeValue);
    }
}
