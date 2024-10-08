package com.companyname.playerservice.functionaltests.get;

import com.companyname.assertions.TestAssertions;
import com.companyname.factories.PlayerFactory;
import com.companyname.models.playerserviceapi.PlayerGetByPlayerIdResponseDTO;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import com.companyname.testutils.SchemaPath;
import com.companyname.testutils.TestGroups;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Feature("Get Player")
@Story("API consumer is able to get Player")
public class GetPlayerByIdTests extends PlayerServiceTestSpec {

    private ThreadLocal<Long> createdPlayerId = new ThreadLocal<>();

    @Description("Verify that Editor is able to GET Player by id")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("PS-311")
    @Tags(@Tag(TestGroups.SMOKE))
    @Test(dataProvider = "editorProvider", groups = {TestGroups.SMOKE, TestGroups.BVT})
    public void verifyThatUserCanRetrievePlayerById(String editor) {

        var expectedPlayer = PlayerFactory.getInstance()
                .buildCreatePayload()
                .createDefaultPlayer();
        var expectedPlayerId = this.playerServiceActions.createPlayer(editor, expectedPlayer, HttpStatus.SC_OK).getId();
        createdPlayerId.set(expectedPlayerId);

        var playerByIdResponse = this.playerServiceActions.getPlayerById(expectedPlayerId);
        TestAssertions.assertStatusCodeIs(playerByIdResponse, HttpStatus.SC_OK);
        TestAssertions.assertResponseMatchesJsonSchema(playerByIdResponse, SchemaPath.GET_PLAYER_BY_ID_RESPONSE_SCHEMA);

        var actualPlayer = playerByIdResponse.extract().body().as(PlayerGetByPlayerIdResponseDTO.class);

        Allure.step("Verify GET player by id call returns correct player information", () -> {
            SoftAssertions.assertSoftly(assertions -> {
                assertions.assertThat(actualPlayer.getAge().toString()).isEqualTo(expectedPlayer.getAge());
                assertions.assertThat(actualPlayer.getGender()).isEqualTo(expectedPlayer.getGender());
                assertions.assertThat(actualPlayer.getId()).isEqualTo(expectedPlayerId);
                assertions.assertThat(actualPlayer.getLogin()).isEqualTo(expectedPlayer.getLogin());
                assertions.assertThat(actualPlayer.getPassword()).isEqualTo(expectedPlayer.getPassword());
                assertions.assertThat(actualPlayer.getRole()).isEqualTo(expectedPlayer.getRole());
                assertions.assertThat(actualPlayer.getScreenName()).isEqualTo(expectedPlayer.getScreenName());
            });
        });
    }

    @AfterMethod()
    void cleanupTest(Object[] args) {
        this.playerServiceActions.deletePlayer(args[0].toString(), createdPlayerId.get())
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
