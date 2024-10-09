package com.companyname.playerservice.functionaltests.get;

import com.companyname.assertions.TestAssertions;
import com.companyname.enums.PlayerEditors;
import com.companyname.models.playerserviceapi.PlayerItemDTO;
import com.companyname.models.playerserviceapi.PlayersDTO;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import com.companyname.testutils.SchemaPath;
import com.companyname.testutils.TestGroups;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.stream.Collectors;

@Feature("Get Player")
@Story("API consumer is able to get all Players")
public class GetAllPlayersTests extends PlayerServiceTestSpec {

    @Description("Verify that GET All players call returns data as expected")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("PS-12350") //Issue: "Role is missing in response for get all players request"
    @TmsLink("PS-320")
    @Tags({@Tag(TestGroups.SMOKE), @Tag(TestGroups.BUG)})
    @Test(groups = {TestGroups.SMOKE, TestGroups.BVT, TestGroups.BUG})
    public void testGetAllPlayersRequest() {
        var response = this.playerServiceActions.getAllPlayers();
        TestAssertions.assertStatusCodeIs(response, HttpStatus.SC_OK);
        TestAssertions.assertResponseMatchesJsonSchema(response, SchemaPath.GET_ALL_PLAYERS_RESPONSE_SCHEMA);
    }

    @Test
    public void test(){
        var response = this.playerServiceActions.getAllPlayers()
                .extract()
                .body()
                .as(PlayersDTO.class);
        response.getPlayers()
                .parallelStream()
                .map(PlayerItemDTO::getId)
                .forEach(id->{
                    this.playerServiceActions.deletePlayer(PlayerEditors.SUPERVISOR.getValue(), id);
                });
    }
}
