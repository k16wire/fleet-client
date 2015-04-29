package k16wire.fleet.client;

import com.google.common.collect.Lists;
import k16wire.fleet.client.messages.UnitEntity;
import k16wire.fleet.client.messages.UnitOption;

import java.util.List;

/**
 * Date: 15. 3. 31.
 * Time: 오후 1:34
 */
public class UnitConfig {
    public static final String DOCKER_SERVICE = "docker.service";

    // state
    public static final String INACTIVE = "inactive";
    public static final String LOADED = "loaded";
    public static final String LAUNCHED = "launched";

    // section
    private static final String UNIT = "Unit";
    private static final String SERVICE = "Service";
    private static final String XFLEET = "X-Fleet";

    private static final String DOCKER_CMD = "-/usr/bin/docker";

    public static Builder builder(){
        return new Builder();
    }

    public static String kill(String containerName){
        return DOCKER_CMD + " kill "+containerName;
    }

    public static String rm(String containerName){
        return DOCKER_CMD + " rm "+containerName;
    }

    public static String pull(String imageName){
        return DOCKER_CMD + " pull "+imageName;
    }

    public static String run(String containerName, String imageName){
        return DOCKER_CMD + " run --name "+containerName + " " + imageName;
    }

    public static class Builder{
        private String desiredState;
        private List<UnitOption> options = Lists.newArrayList();

        public Builder withDesiredState(final String desiredState){
            this.desiredState = desiredState;
            return this;
        }

        public Builder withDescription(String value){
            options.add(new UnitOption(UNIT,"Description",value));
            return this;
        }

        public Builder withAfter(String value){
            options.add(new UnitOption(UNIT,"After",value));
            return this;
        }

        public Builder withRequires(String value){
            options.add(new UnitOption(UNIT,"Requires",value));
            return this;
        }

        public Builder withBindsTo(String value){
            options.add(new UnitOption(UNIT,"BindsTo",value));
            return this;
        }

        public Builder withExecStartPre(String value){
            options.add(new UnitOption(SERVICE,"ExecStartPre",value));
            return this;
        }

        public Builder withExecStart(String value){
            options.add(new UnitOption(SERVICE,"ExecStart",value));
            return this;
        }

        public Builder withExecStopPre(String value){
            options.add(new UnitOption(SERVICE,"ExecStopPre",value));
            return this;
        }

        public Builder withExecStop(String value){
            options.add(new UnitOption(SERVICE,"ExecStop",value));
            return this;
        }

        public Builder withTimeoutStartSec(int value){
            options.add(new UnitOption(SERVICE,"TimeoutStartSec",String.valueOf(value)));
            return this;
        }

        public Builder withRestart(String value){
            options.add(new UnitOption(SERVICE,"Restart",value));
            return this;
        }

        public Builder withRestartSec(int value){
            options.add(new UnitOption(SERVICE,"Restart",String.valueOf(value)));
            return this;
        }

        public Builder withXConflicts(String value){
            options.add(new UnitOption(XFLEET,"X-Conflicts",value));
            return this;
        }

        public Builder withMachineOf(String value){
            options.add(new UnitOption(XFLEET,"MachineOf",value));
            return this;
        }

        public UnitEntity build(){
            UnitEntity unitEntity = new UnitEntity();
            unitEntity.setDesiredState(this.desiredState);
            unitEntity.setOptions(this.options);
            return unitEntity;
        }
    }
}
