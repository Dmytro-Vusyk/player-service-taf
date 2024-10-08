package com.companyname.playerservice.functionaltests.create;

import com.companyname.assertions.TestAssertions;
import com.companyname.enums.PlayerEditors;
import com.companyname.factories.PlayerFactory;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import com.companyname.testutils.TestGroups;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

@Feature("Create Player")
@Story("Admin or Supervisor is able to create Player")
public class CreatePlayerNegativeTests extends PlayerServiceTestSpec {

    @Description("Verify that invalid editor is not able to CREATE a Player")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("PS-323") //Test case id in TMS
    @Tags(@Tag(TestGroups.NEGATIVE))
    @Test(groups = {TestGroups.NEGATIVE, TestGroups.BVT})
    public void testInvalidEditorCanNotCreatePlayer() {
        var expectedPlayer = PlayerFactory.getInstance()
                .buildCreatePayload()
                .createDefaultPlayer();
        var role = this.faker.getRandomString(10);
        var response = this.playerServiceActions.createPlayer(role, expectedPlayer);
        TestAssertions.assertStatusCodeIs(response, HttpStatus.SC_FORBIDDEN);
    }

    @Description("Verify that Player older than 60 years can be created")
    @Severity(SeverityLevel.CRITICAL) //severity is critical because most reach people are older than 60
    @Issue("PS-12345")
    @TmsLink("PS-324")
    @Tags(@Tag(TestGroups.BUG))
    @Test(groups = {TestGroups.BUG, TestGroups.REG})
    public void testEditorCanCreatePlayerOlderThan() {
        var expectedPlayer = PlayerFactory.getInstance()
                .buildCreatePayload()
                .createDefaultPlayer();
        expectedPlayer.setAge(String.valueOf(this.faker.getRandomInt(61, 100)));
        var response = this.playerServiceActions.createPlayer(PlayerEditors.SUPERVISOR.getValue(), expectedPlayer);
        TestAssertions.assertStatusCodeIs(response, HttpStatus.SC_OK);
    }
}
