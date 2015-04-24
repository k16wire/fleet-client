package com.pragmaticstory.fleet.client;

import com.pragmaticstory.fleet.client.messages.MachineEntity;
import com.pragmaticstory.fleet.client.messages.UnitEntity;

import java.util.List;

/**
 * User: 1001923
 * Date: 15. 4. 23.
 * Time: 오후 1:56
 */
public interface FleetClient {
    public List<MachineEntity> listMachines() throws FleetException;
    public WS2.ResponseResult createUnit(UnitEntity unitEntity, String name) throws FleetException;
    public WS2.ResponseResult destroyUnit(String name) throws FleetException;
}
