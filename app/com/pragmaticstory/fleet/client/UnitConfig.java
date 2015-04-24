package com.pragmaticstory.fleet.client;

import com.google.common.collect.Lists;
import com.pragmaticstory.fleet.client.messages.UnitEntity;
import com.pragmaticstory.fleet.client.messages.UnitOption;

import java.util.List;

/**
 * Date: 15. 3. 31.
 * Time: 오후 1:34
 */
public class UnitConfig {
    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String desiredState;
        private List<UnitOption> options = Lists.newArrayList();

        public Builder withDesiredState(final String desiredState){
            this.desiredState = desiredState;
            return this;
        }

        public Builder withDescription(String value){
            options.add(new UnitOption("Unit","Description",value));
            return this;
        }

        public Builder withAfter(String value){
            options.add(new UnitOption("Unit","After",value));
            return this;
        }

        public Builder withRequires(String value){
            options.add(new UnitOption("Unit","Requires",value));
            return this;
        }

        public Builder withBindsTo(String value){
            options.add(new UnitOption("Unit","BindsTo",value));
            return this;
        }

        public Builder withExecStartPre(String value){
            options.add(new UnitOption("Service","ExecStartPre",value));
            return this;
        }

        public Builder withExecStart(String value){
            options.add(new UnitOption("Service","ExecStart",value));
            return this;
        }

        public Builder withExecStopPre(String value){
            options.add(new UnitOption("Service","ExecStopPre",value));
            return this;
        }

        public Builder withExecStop(String value){
            options.add(new UnitOption("Service","ExecStop",value));
            return this;
        }

        public Builder withTimeoutStartSec(int value){
            options.add(new UnitOption("Service","TimeoutStartSec",String.valueOf(value)));
            return this;
        }

        public Builder withRestart(String value){
            options.add(new UnitOption("Service","Restart",value));
            return this;
        }

        public Builder withRestartSec(int value){
            options.add(new UnitOption("Service","Restart",String.valueOf(value)));
            return this;
        }

        public Builder withXConflicts(String value){
            options.add(new UnitOption("X-Fleet","X-Conflicts",value));
            return this;
        }

        public Builder withMachineOf(String value){
            options.add(new UnitOption("X-Fleet","MachineOf",value));
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
