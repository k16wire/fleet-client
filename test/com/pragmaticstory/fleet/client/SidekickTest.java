package com.pragmaticstory.fleet.client;

import com.google.common.collect.Lists;
import com.pragmaticstory.fleet.client.messages.UnitEntity;
import com.pragmaticstory.helpers.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static com.pragmaticstory.fleet.client.UnitConfig.*;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Date: 15. 4. 27.
 * Time: 오후 3:06
 */
public class SidekickTest extends AbstractTest {
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
        super.after();
        // Remove units
        for(String unitName:units){
//            sut.destroyUnit(unitName);
        }
    }

    @Ignore
    public void createApacheUnit() throws Exception{
        // given
        String mainContainer = "apache1";
        String imageName = "coreos/apache";
        UnitEntity mainUnitEntity = builder()
                .withDescription(mainContainer)
                .withAfter(DOCKER_SERVICE)
                .withRequires(DOCKER_SERVICE)
                .withDesiredState(LOADED)
                .withExecStartPre(kill(mainContainer))
                .withExecStartPre(rm(mainContainer))
                .withExecStartPre(pull(imageName))
                .withExecStart(run(mainContainer, imageName))
                .withTimeoutStartSec(0)
                .build();
        String mainUnit = mainContainer+".service";
        units.add(mainUnit);
        // when
        WS2.ResponseResult result = sut.createUnit(mainUnitEntity, mainUnit);
        //Then
        assertThat(result.statusCode).isEqualTo(201);

        // given
//        String sidekickContainer = "apache1-discovery";
//        String sidekickUnit = sidekickContainer+".service";
//        UnitEntity sidekickUnitEntity = builder()
//                .withBindsTo(mainUnit)
//                .withAfter(mainUnit)
//                .withDesiredState(INACTIVE)
//                .withExecStart("/usr/bin/etcdctl set /services/website/apache1 '{ \"test\" }'")
////                .withExecStart("/bin/sh -c \"while true; do etcdctl set /services/website/apache1 '{\\\"host\\\":\\\"%H\\\",\\\"port\\\":80}' --ttl 60; sleep 45; done")
////                .withExecStop("/usr/bin/etcdctl rm /services/website/apache1")
//                .withMachineOf(mainUnit)
//                .build();
//        // when
//        units.add(mainUnit);
//        WS2.ResponseResult result2 = sut.createUnit(sidekickUnitEntity, sidekickUnit);
//        //Then
//        assertThat(result2.statusCode).isEqualTo(201);

        // when
        WS2.ResponseResult modifyResult = sut.modifyUnit(mainUnit, LAUNCHED);
        assertThat(modifyResult.statusCode).isEqualTo(204);

        // when
//        WS2.ResponseResult modifyResult2 = sut.modifyUnit(sidekickUnit, LAUNCHED);
//        assertThat(modifyResult2.statusCode).isEqualTo(204);
    }

    @Test
    public void busybox() throws Exception{
        String imageName = "busybox";
        String mainContainer = "hello1";
        String sidekickContainer = mainContainer+"-discovery";
        String mainUnit = mainContainer+".service";
        String sidekickUnit = sidekickContainer+".service";

        // main unit
        UnitEntity mainUnitEntity = mainUnitEntity(mainContainer, imageName);
        createBusyboxUnit(mainUnitEntity, mainUnit);

        // sidekick unit
        UnitEntity sidekickUnitEntity = sidekickUnitEntity(sidekickContainer, mainUnit);
        createSidekickUnit(sidekickUnitEntity, sidekickUnit);

        startBusyboxUnit(mainContainer);
        startSidekickUnit(sidekickUnit);
    }

    private void createSidekickUnit(UnitEntity sidekickUnitEntity, String sidekickUnit) throws FleetException {
        // when
        units.add(sidekickUnit);
        WS2.ResponseResult result = sut.createUnit(sidekickUnitEntity, sidekickUnit);
        //Then
        assertThat(result.statusCode).isEqualTo(201);
    }

    private UnitEntity sidekickUnitEntity(String sidekickContainer, String mainUnit) {
        return builder()
                .withBindsTo(mainUnit)
                .withAfter(mainUnit)
                .withDesiredState(LOADED)
                .withExecStartPre(kill(sidekickContainer))
//                .withExecStart("/usr/bin/etcdctl set /services/website/apache1 '{ \"test\" }'")
//                .withExecStart("/bin/sh -c \"while true; do etcdctl set /services/website/apache1 '{\\\"host\\\":\\\"%H\\\",\\\"port\\\":80}' --ttl 60; sleep 45; done")
//                .withExecStop("/usr/bin/etcdctl rm /services/website/apache1")
                .withMachineOf(mainUnit)
                .build();
    }

    private UnitEntity mainUnitEntity(String mainContainer, String imageName) {
        return builder()
                .withDescription(mainContainer)
                .withAfter(DOCKER_SERVICE)
                .withRequires(DOCKER_SERVICE)
                .withDesiredState(LOADED)
                .withTimeoutStartSec(0)
                .withExecStartPre(kill(mainContainer))
                .withExecStartPre(rm(mainContainer))
                .withExecStartPre(pull(imageName))
                .withExecStart("/usr/bin/docker run --name helloservice busybox echo hello world")
                .build();
    }

    public void createBusyboxUnit(UnitEntity mainUnitEntity, String mainUnit) throws Exception{
        // given
        units.add(mainUnit);
        // when
        WS2.ResponseResult result = sut.createUnit(mainUnitEntity, mainUnit);
        //Then
        assertThat(result.statusCode).isEqualTo(201);
    }

    public void startBusyboxUnit(String mainContainer) throws Exception{
        // when
        String mainUnit = mainContainer+".service";
        WS2.ResponseResult modifyResult = sut.modifyUnit(mainUnit, LAUNCHED);
        assertThat(modifyResult.statusCode).isEqualTo(204);
    }

    public void startSidekickUnit(String sidekickUnit) throws Exception{
        // when
        WS2.ResponseResult modifyResult2 = sut.modifyUnit(sidekickUnit, LAUNCHED);
        assertThat(modifyResult2.statusCode).isEqualTo(204);
    }

//    public void createBusyboxSidekickUnit() throws Exception{
//        given
//        String mainContainer = "hello1";
//        String mainUnit = mainContainer+".service";
//        String sidekickContainer = "hello1-discovery";
//        String sidekickUnit = sidekickContainer+".service";
//        UnitEntity sidekickUnitEntity = sidekickUnitEntity(sidekickContainer, mainUnit);
        // when
//        units.add(sidekickUnit);
//        WS2.ResponseResult result = sut.createUnit(sidekickUnitEntity, sidekickUnit);
        //Then
//        assertThat(result.statusCode).isEqualTo(201);
//    }
}
