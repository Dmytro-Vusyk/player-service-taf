package com.companyname.playerservice.functionaltests.delete;

import com.companyname.testutils.TestGroups;
import com.companyname.factories.PlayerCreateRequestDTOFactory;
import com.companyname.models.playerserviceapi.PlayerCreateResponseDTO;
import com.companyname.models.playerserviceapi.PlayerItemDTO;
import com.companyname.models.playerserviceapi.PlayersDTO;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

@Feature("Delete Player")
@Story("Admin or Supervisor is able to delete Player")
public class DeletePlayerTests extends PlayerServiceTestSpec {

    @Description("User with different roles are able to CREATE Players")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("PS-326") //Test case id in TMS
    @Tags(@Tag(TestGroups.SMOKE))
    @Test(dataProvider = "roleProvider", groups = {TestGroups.SMOKE, TestGroups.BVT})
    public void test(String role) {
        var expectedPlayer = PlayerCreateRequestDTOFactory.createPlayerWithRequiredFieldsOnly();
        var actualPlayerId = this.playerControllerEndpoint.createPlayer(role, expectedPlayer)
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PlayerCreateResponseDTO.class)
                .getId();

        var deleteResponse = this.playerControllerEndpoint.deletePlayer(role, actualPlayerId);

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(deleteResponse.extract().statusCode())
                    .as("Verify that status code is 204 NO_CONTENT")
                    .isEqualTo(HttpStatus.SC_NO_CONTENT);
            assertions.assertThat(deleteResponse.extract().response().getBody().asString())
                    .as("Verify that body is null")
                    .isEmpty();
        });

        var actualPlayers = this.playerControllerEndpoint.getAllPlayers()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(PlayersDTO.class)
                .getPlayers();

        Assertions.assertThat(actualPlayers)
                .extracting(PlayerItemDTO::getId)
                .as("Verify that player is deleted from database")
                .doesNotContain(actualPlayerId);
    }
}
