package com.companyname.playerservice.functionaltests;

import com.companyname.PlayerServiceAPI;
import com.companyname.endpoints.PlayerControllerEndpoint;
import com.companyname.enums.PlayerEditor;
import com.companyname.models.playerserviceapi.PlayerCreateRequestDTO;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SomeTestStub extends PlayerServiceTestSpec {

    private PlayerControllerEndpoint playerControllerEndpoint;

    @BeforeClass
    public void setupClass() {
        this.playerControllerEndpoint = PlayerServiceAPI.getAPI().getPlayerControllerEndpoint();
    }

    @Test
    public void test() {

        var expectedPlayer = PlayerCreateRequestDTO.builder()
                .age("21")
                .editor(PlayerEditor.SUPERVISOR.getValue())
                .gender("mechanic")
                .login("123")
                .password("123")
                .role("user")
                .screenName("screenName")
                .build();

        var actualResponse = playerControllerEndpoint.createPlayer(
                PlayerEditor.SUPERVISOR.getValue(),
                expectedPlayer,
                200);
        System.out.println(actualResponse);
    }
}
