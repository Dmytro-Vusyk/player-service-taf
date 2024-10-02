package com.companyname.playerservice.functionaltests;

import com.companyname.BaseTestSpec;
import com.companyname.PlayerServiceAPI;
import com.companyname.endpoints.PlayerControllerEndpoint;
import com.companyname.enums.PlayerEditors;
import com.companyname.utils.Faker;
import io.qameta.allure.Epic;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@Epic("Player service implementation")
public class PlayerServiceTestSpec extends BaseTestSpec {

    protected PlayerControllerEndpoint playerControllerEndpoint;
    protected Faker faker;

    @DataProvider(name = "editorProvider", parallel = true)
    public Object[][] editorProvider() {
        return new Object[][]{
                {PlayerEditors.SUPERVISOR.getValue()},
                {PlayerEditors.ADMIN.getValue()}
        };
    }
    @BeforeClass
    public void setupClass() {
        this.playerControllerEndpoint = PlayerServiceAPI.getAPI().getPlayerControllerEndpoint();
        this.faker = new Faker();
    }
}
