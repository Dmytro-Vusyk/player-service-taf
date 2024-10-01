package com.companyname.playerservice.functionaltests.create;

import com.companyname.TestGroups;
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

    @Description("User with not specified role is not able to CREATE a Player")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("PS-323") //Test case id in TMS
    @Tags(@Tag(TestGroups.NEGATIVE))
    @Test(groups = {TestGroups.NEGATIVE, TestGroups.BVT})
    public void verifyThat() {
        var expectedPlayer = PlayerCreateRequestDTOFactory.createDefaultPlayer();
        var role = "GOD";
        this.playerControllerEndpoint.createPlayer(
                role,
                expectedPlayer,
                HttpStatus.SC_FORBIDDEN);
    }
}
