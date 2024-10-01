package com.companyname.playerservice.functionaltests.create;

import com.companyname.TestGroups;
import com.companyname.enums.PlayerEditors;
import com.companyname.factories.PlayerCreateRequestDTOFactory;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

@Feature("Create Player")
@Story("Admin or Supervisor is able to create Player")
public class CreatePlayerNegativeTest extends PlayerServiceTestSpec {

    @Description("Invalid editor is not able to CREATE a Player")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("PS-323") //Test case id in TMS
    @Tags(@Tag(TestGroups.NEGATIVE))
    @Test(groups = {TestGroups.NEGATIVE, TestGroups.BVT})
    public void verifyThatInvalidEditorCanNotCreatePlayer() {
        var expectedPlayer = PlayerCreateRequestDTOFactory.createDefaultPlayer();
        var role = "GOD";
        this.playerControllerEndpoint
                .createPlayer(role, expectedPlayer)
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Description("Verify that Player older than 60 years can be created")
    @Severity(SeverityLevel.CRITICAL) //severity is critical because most reach people are older than 60
    @Issue("PS-12345")
    @TmsLink("PS-324")
    @Tags(@Tag(TestGroups.BUG))
    @Test(groups = {TestGroups.BUG, TestGroups.REG})
    public void verifyThatEditorCanCreatePlayerOlderThan(){
        var expectedPlayer = PlayerCreateRequestDTOFactory.createDefaultPlayer();
        expectedPlayer.setAge("61");
        this.playerControllerEndpoint
                .createPlayer(PlayerEditors.SUPERVISOR.getValue(), expectedPlayer)
                .statusCode(HttpStatus.SC_OK);
    }
}
