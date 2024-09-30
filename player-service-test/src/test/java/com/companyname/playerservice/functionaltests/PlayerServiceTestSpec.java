package com.companyname.playerservice.functionaltests;

import com.companyname.BaseTestSpec;
import com.companyname.PlayerServiceAPI;
import com.companyname.endpoints.PlayerControllerEndpoint;
import io.qameta.allure.Epic;
import org.testng.annotations.BeforeClass;

@Epic("Player service implementation")
public class PlayerServiceTestSpec extends BaseTestSpec {

    protected PlayerControllerEndpoint playerControllerEndpoint;

    @BeforeClass
    public void setupClass() {
        this.playerControllerEndpoint = PlayerServiceAPI.getAPI().getPlayerControllerEndpoint();
    }
}
