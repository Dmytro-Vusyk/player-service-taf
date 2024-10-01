package com.companyname.playerservice.functionaltests.get;

import com.companyname.TestGroups;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetAllPlayersTests extends PlayerServiceTestSpec {

    @Description("GET All players call returns data as expected")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("PS-12350") //Issue: "Role is missing in response for get all players request"
    @TmsLink("PS-320")
    @Tags(@Tag(TestGroups.SMOKE))
    @Test(groups = {TestGroups.SMOKE, TestGroups.BVT, TestGroups.BUG})
    public void testGetAllPlayersRequest() {
        this.playerControllerEndpoint.getAllPlayers()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("playerservice/schemas/get-all-players-response.json"));;
    }
}
