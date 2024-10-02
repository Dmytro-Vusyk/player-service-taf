package com.companyname.playerservice.functionaltests.update;

import com.companyname.factories.PlayerCreateRequestDTOFactory;
import com.companyname.models.playerserviceapi.PlayerCreateResponseDTO;
import com.companyname.models.playerserviceapi.PlayerGetByPlayerIdResponseDTO;
import com.companyname.models.playerserviceapi.PlayerUpdateRequestDTO;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import com.companyname.testutils.SchemaPath;
import com.companyname.testutils.TestGroups;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Feature("Update Player")
@Story("API consumer is able to update Player")
public class UpdatePlayerTests extends PlayerServiceTestSpec {

    private static Long createdPlayerId;

    @Description("Verify that editor is able to UPDATE Player")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("PS-322")
    @Tags(@Tag(TestGroups.SMOKE))
    @Test(dataProvider = "editorProvider", groups = {TestGroups.SMOKE, TestGroups.BVT})
    public void testEditorCanUpdatePlayer(String editor) {
        var defaultPlayer = PlayerCreateRequestDTOFactory.createDefaultPlayer();
        var createdPlayer = this.playerControllerEndpoint.createPlayer(editor, defaultPlayer)
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(PlayerCreateResponseDTO.class);

        createdPlayerId = createdPlayer.getId();

        var expectedAge = 22;
        var expectedGender = "mechanic";

        var playerToUpdate = PlayerUpdateRequestDTO.builder()
                .age(expectedAge)
                .gender(expectedGender)
                .login(defaultPlayer.getLogin())
                .password(defaultPlayer.getPassword())
                .role(defaultPlayer.getRole())
                .screenName(defaultPlayer.getScreenName())
                .build();

        var playerUpdateResponse = this.playerControllerEndpoint.updatePlayer(editor, createdPlayer.getId(), playerToUpdate);

        Assertions.assertThat(playerUpdateResponse.extract().statusCode())
                .as("Verify Status Code is 200")
                .isEqualTo(HttpStatus.SC_OK);
        playerUpdateResponse.assertThat()
                .body(matchesJsonSchemaInClasspath(SchemaPath.UPDATE_PLAYER_RESPONSE_SCHEMA));

        var actualPlayer = this.playerControllerEndpoint.getPlayerById(createdPlayer.getId())
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(PlayerGetByPlayerIdResponseDTO.class);

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(actualPlayer.getAge())
                    .as("Verify that age is changed")
                    .isEqualTo(expectedAge);
            assertions.assertThat(actualPlayer.getGender())
                    .as("Verify that gender is changed")
                    .isEqualTo(expectedGender);
        });
    }

    @AfterMethod()
    void cleanupTest(Object[] args) {
        this.playerControllerEndpoint.deletePlayer(args[0].toString(), createdPlayerId)
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
