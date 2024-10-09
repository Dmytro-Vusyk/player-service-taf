package com.companyname.playerservice.functionaltests;

import com.companyname.BaseTestSpec;
import com.companyname.PlayerServiceAPI;
import com.companyname.endpoints.PlayerControllerEndpoint;
import com.companyname.enums.PlayerEditors;
import com.companyname.playerserviceapi.actions.PlayerServiceApiActions;
import com.companyname.utils.Faker;
import io.qameta.allure.Epic;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@Epic("Player service implementation")
public class PlayerServiceTestSpec extends BaseTestSpec {

    protected PlayerServiceApiActions playerServiceActions;
    protected Faker faker;

    @DataProvider(name = "editorProvider")
    public Object[][] editorProvider() {
        return new Object[][]{
                {PlayerEditors.SUPERVISOR.getValue()},
                {PlayerEditors.ADMIN.getValue()}
        };
    }
    @BeforeClass(alwaysRun = true)
    public void setupClass() {
        this.playerServiceActions = new PlayerServiceApiActions();
        this.faker = new Faker();
    }
}
