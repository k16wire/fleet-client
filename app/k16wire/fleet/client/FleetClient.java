package k16wire.fleet.client;

import k16wire.fleet.client.messages.MachineEntity;
import k16wire.fleet.client.messages.UnitEntity;
import k16wire.fleet.client.messages.UnitEntityInfo;

import java.util.List;

/**
 * User: 1001923
 * Date: 15. 4. 23.
 * Time: 오후 1:56
 */
public interface FleetClient {
    public List<MachineEntity> listMachines() throws FleetException;
    public ResponseResult createUnit(UnitEntity unitEntity, String name) throws FleetException;
    public ResponseResult destroyUnit(String name) throws FleetException;
    public ResponseResult modifyUnit(String name, String state) throws FleetException;
    public List<UnitEntityInfo> listUnits() throws FleetException;
}
