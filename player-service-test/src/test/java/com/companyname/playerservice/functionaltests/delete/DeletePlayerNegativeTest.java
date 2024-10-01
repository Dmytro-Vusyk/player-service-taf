package com.companyname.playerservice.functionaltests.delete;

import com.companyname.testutils.TestGroups;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import com.companyname.utils.Faker;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

@Feature("Delete Player")
@Story("Admin or Supervisor is able to delete Player")
public class DeletePlayerNegativeTest extends PlayerServiceTestSpec {

    @Description("Player controller api returns NOT_FOUND when DELETE operation is performed on non existent user")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("PS-327")
    @Tags(@Tag(TestGroups.NEGATIVE))
    @Test(dataProvider = "roleProvider", groups = {TestGroups.NEGATIVE})
    public void test(String role) {
        var randomPlayerId = Faker.instance().random().nextLong(Long.MAX_VALUE);
        this.playerControllerEndpoint.deletePlayer(role, randomPlayerId)
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
