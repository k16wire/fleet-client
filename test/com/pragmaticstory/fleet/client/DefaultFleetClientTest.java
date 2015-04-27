package com.pragmaticstory.fleet.client;

import com.google.common.collect.Lists;
import com.pragmaticstory.fleet.client.messages.MachineEntity;
import com.pragmaticstory.fleet.client.messages.UnitEntity;
import com.pragmaticstory.fleet.client.messages.UnitEntityInfo;
import com.pragmaticstory.helpers.AbstractTest;
import com.pragmaticstory.helpers.RWG;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: 1001923
 * Date: 15. 4. 23.
 * Time: 오후 2:07
 */
public class DefaultFleetClientTest extends AbstractTest{
    private DefaultFleetClient sut;
    private List<String> units = Lists.newArrayList();

    @Before
    public void before() throws Exception{
        super.before();
        final DefaultFleetClient.Builder builder = DefaultFleetClient.fromEnv();
        sut = builder.build();
    }

    @After
    public void after() throws Exception{
        // Remove units
        for(String unitName:units){
            sut.destroyUnit(unitName);
        }
    }

    @Test
    public void createHelloService() throws Exception{
        //Given
        UnitEntity unitEntity = UnitConfig.builder()
                .withDesiredState("launched")
                .withExecStartPre("/usr/bin/docker pull busybox")
                .withExecStart("/usr/bin/docker run --name helloservice busybox echo hello world")
                .withExecStopPre("/usr/bin/docker kill helloservice")
                .withExecStop("/usr/bin/docker rm helloservice")
                .withTimeoutStartSec(0)
                .build();
        String unitName = RWG.randomWord(6)+".service";
        units.add(unitName);
        //When
        WS2.ResponseResult result = sut.createUnit(unitEntity, unitName);
        //Then
        assertThat(result.statusCode).isEqualTo(201);
    }

    @Test(expected = FleetRequestException.class)
    public void connection() throws Exception{
        // given
        DefaultFleetClient client = new DefaultFleetClient("127.0.0.1");
        // when
        client.listMachines();
    }

    @Test
    public void listMachines() throws Exception {
        // when
        List<MachineEntity> machineEntities = sut.listMachines();
        // then
        assertThat(machineEntities.size()).isEqualTo(3);
    }

    @Test
    public void listUnits() throws Exception{
        // when
        List<UnitEntityInfo> unitEntityInfoList = sut.listUnits();
        // then
        assertThat(unitEntityInfoList).isNotEmpty();
    }
}
