package com.pragmaticstory.fleet.client;

import com.google.common.collect.Lists;
import com.pragmaticstory.fleet.client.messages.UnitEntity;
import com.pragmaticstory.helpers.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.pragmaticstory.fleet.client.UnitConfig.*;
import static org.fest.assertions.Assertions.assertThat;

/**
 * User: 1001923
 * Date: 15. 4. 24.
 * Time: 오후 6:16
 */
public class TemplateUnitTest extends AbstractTest {
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

    @Test
    public void createApacheUnit() throws Exception{
        //Given
        String containerName = "apache1";
        String imageName = "coreos/apache";
        UnitEntity unitEntity = builder()
                .withDescription(containerName)
                .withAfter(DOCKER_SERVICE)
                .withRequires(DOCKER_SERVICE)
                .withDesiredState(LOADED)
                .withExecStartPre(kill(containerName))
                .withExecStartPre(rm(containerName))
                .withExecStartPre(pull(imageName))
                .withExecStart(run(containerName, imageName))
                .withXConflicts("apache@*.service")
                .withTimeoutStartSec(0)
                .build();
        String unitName = containerName+".service";
        units.add(unitName);
        //When
        WS2.ResponseResult result = sut.createUnit(unitEntity, unitName);
        //Then
        assertThat(result.statusCode).isEqualTo(201);
    }
}
