package com.companyname.playerservice.functionaltests.get;

import com.companyname.TestGroups;
import com.companyname.factories.PlayerCreateRequestDTOFactory;
import com.companyname.models.playerserviceapi.PlayerCreateResponseDTO;
import com.companyname.models.playerserviceapi.PlayerGetByPlayerIdResponseDTO;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetPlayerByIdTests extends PlayerServiceTestSpec {

    private static Long createdPlayerId;

    @Description("Editor is able to GET Player by id")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("PS-311")
    @Tags(@Tag(TestGroups.SMOKE))
    @Test(dataProvider = "roleProvider", groups = {TestGroups.SMOKE, TestGroups.BVT})
    public void verifyThatUserCanRetrievePlayerById(String role) {

        var expectedPlayer = PlayerCreateRequestDTOFactory.createDefaultPlayer();
        var expectedPlayerId = this.playerControllerEndpoint.createPlayer(role, expectedPlayer)
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PlayerCreateResponseDTO.class)
                .getId();

        createdPlayerId = expectedPlayerId;

        var playerByIdResponse = this.playerControllerEndpoint.getPlayerById(expectedPlayerId);
        Assertions.assertThat(playerByIdResponse.extract().statusCode())
                .as("Verify that API responded with status code 200")
                .isEqualTo(HttpStatus.SC_OK);
        playerByIdResponse.assertThat()
                .body(matchesJsonSchemaInClasspath("playerservice/schemas/get-player-by-id-response.json"));

        var actualPlayer = playerByIdResponse.extract().body().as(PlayerGetByPlayerIdResponseDTO.class);

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(actualPlayer.getAge().toString()).isEqualTo(expectedPlayer.getAge());
            assertions.assertThat(actualPlayer.getGender()).isEqualTo(expectedPlayer.getGender());
            assertions.assertThat(actualPlayer.getId()).isEqualTo(expectedPlayerId);
            assertions.assertThat(actualPlayer.getLogin()).isEqualTo(expectedPlayer.getLogin());
            assertions.assertThat(actualPlayer.getPassword()).isEqualTo(expectedPlayer.getPassword());
            assertions.assertThat(actualPlayer.getRole()).isEqualTo(expectedPlayer.getRole());
            assertions.assertThat(actualPlayer.getScreenName()).isEqualTo(expectedPlayer.getScreenName());
        });
    }

    @AfterMethod()
    void cleanupTest(Object[] args) {
        this.playerControllerEndpoint.deletePlayer(args[0].toString(), createdPlayerId)
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
