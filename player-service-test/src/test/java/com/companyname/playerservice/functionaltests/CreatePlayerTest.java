package com.companyname.playerservice.functionaltests;

import com.companyname.enums.PlayerEditors;
import com.companyname.factories.PlayerCreateRequestDTOFactory;
import org.testng.annotations.Test;

public class CreatePlayerTest extends PlayerServiceTestSpec {

    @Test
    public void verifyThatPlayerCanBeCreated() {

        var expectedPlayer = PlayerCreateRequestDTOFactory.createDefaultPlayer();

        var actualResponse = this.playerControllerEndpoint.createPlayer(
                PlayerEditors.SUPERVISOR.getValue(),
                expectedPlayer,
                200);
        //assert response schema
        System.out.println(actualResponse);
    }
}
