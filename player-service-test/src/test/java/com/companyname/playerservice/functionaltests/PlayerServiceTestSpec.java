package com.companyname.playerservice.functionaltests;

import com.companyname.BaseTestSpec;
import com.companyname.PlayerServiceAPI;
import com.companyname.endpoints.PlayerControllerEndpoint;
import org.testng.annotations.BeforeClass;

public class PlayerServiceTestSpec extends BaseTestSpec {

    protected PlayerControllerEndpoint playerControllerEndpoint;

    @BeforeClass
    public void setupClass() {
        this.playerControllerEndpoint = PlayerServiceAPI.getAPI().getPlayerControllerEndpoint();
    }
}
