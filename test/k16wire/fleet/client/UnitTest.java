package k16wire.fleet.client;

import com.google.common.collect.Lists;
import k16wire.fleet.client.messages.UnitEntity;
import k16wire.helpers.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static k16wire.fleet.client.UnitConfig.*;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Date: 15. 4. 24.
 * Time: 오후 5:24
 */
public class UnitTest extends AbstractTest{
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
//        super.after();
        // Remove units
        for(String unitName:units){
//            sut.destroyUnit(unitName);
        }
    }

    @Ignore
    public void inactiveNginxUnit() throws Exception{
        // when
        sut.modifyUnit("nginx1.service", INACTIVE);
    }

    @Test
    public void launchNginxUnit() throws Exception{
        // when
        sut.modifyUnit("nginx1.service", LAUNCHED);
    }

    @Ignore
    public void createNginxUnit() throws Exception{
        //Given
        String containerName = "nginx1";
        String imageName = "nginx";
        UnitEntity unitEntity = builder()
                .withDescription(containerName)
                .withAfter(DOCKER_SERVICE)
                .withRequires(DOCKER_SERVICE)
                .withDesiredState(LOADED)
                .withExecStartPre(kill(containerName))
                .withExecStartPre(rm(containerName))
                .withExecStartPre(pull(imageName))
                .withExecStart(run(containerName, imageName))
                .withTimeoutStartSec(0)
                .build();
        String unitName = containerName+".service";
        units.add(unitName);
        //When
        ResponseResult result = sut.createUnit(unitEntity, unitName);
        //Then
        assertThat(result.statusCode).isEqualTo(201);
    }
}
