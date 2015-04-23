package com.pragmaticstory.fleet.client;

import com.pragmaticstory.fleet.client.messages.MachineEntity;
import com.pragmaticstory.helpers.AbstractTest;
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
public class DefaultFleetClientTest extends AbstractTest {
    private DefaultFleetClient sut;

    @Before
    public void setup() throws Exception{
        final DefaultFleetClient.Builder builder = DefaultFleetClient.fromEnv();
        sut = builder.build();
    }

    @After
    public void tearDown() throws Exception{
        // Remove units
    }

    @Test
    public void listMachines() throws Exception {
        // when
        List<MachineEntity> machineEntities = sut.listMachines();
        // then
        assertThat(machineEntities.size()).isEqualTo(3);
    }

}
