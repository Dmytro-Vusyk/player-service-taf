package com.companyname.playerservice.functionaltests.get;

import com.companyname.assertions.TestAssertions;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import com.companyname.testutils.TestGroups;
import com.companyname.utils.Faker;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

@Feature("Get Player")
@Story("API consumer is able to get Player")
public class GetPlayerByIdNegativeTests extends PlayerServiceTestSpec {

    @Description("Verify that player controller api returns NOT_FOUND when GET operation is performed on non existent player")
    @Severity(SeverityLevel.NORMAL)
    @Issue("PS-12347") //Issue id for "Status code 200 in get by id response for nonexistent Player"
    @TmsLink("PS-318")
    @Tags(@Tag(TestGroups.NEGATIVE))
    @Test(groups = {TestGroups.NEGATIVE, TestGroups.REG})
    public void testGetNonExistentPlayerById() {
        var randomPlayerId = Faker.instance().random().nextLong(Long.MAX_VALUE);
        var response = this.playerServiceActions.getPlayerById(randomPlayerId);
        TestAssertions.assertStatusCodeIs(response, HttpStatus.SC_NOT_FOUND);
    }
}
