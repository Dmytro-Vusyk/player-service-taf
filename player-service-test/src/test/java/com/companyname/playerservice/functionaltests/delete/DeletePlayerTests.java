package com.companyname.playerservice.functionaltests.delete;

import com.companyname.assertions.TestAssertions;
import com.companyname.factories.PlayerFactory;
import com.companyname.models.playerserviceapi.PlayerItemDTO;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import com.companyname.testutils.TestGroups;
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

    @Description("Verify that user with different roles are able to CREATE Players")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("PS-326") //Test case id in TMS
    @Tags(@Tag(TestGroups.SMOKE))
    @Test(dataProvider = "editorProvider", groups = {TestGroups.SMOKE, TestGroups.BVT})
    public void testDeletePlayerById(String editor) {
        var expectedPlayer = PlayerFactory.getInstance()
                .buildCreatePayload()
                .createPlayerWithRequiredFieldsOnly();
        var actualPlayerId = this.playerServiceActions.createPlayer(editor, expectedPlayer, HttpStatus.SC_OK).getId();
        var deleteResponse = this.playerServiceActions.deletePlayer(editor, actualPlayerId);

        SoftAssertions.assertSoftly(assertions -> {
            TestAssertions.assertStatusCodeIs(deleteResponse, HttpStatus.SC_NO_CONTENT);
            Allure.step("Verify that body is null", () -> {
                assertions.assertThat(deleteResponse.extract().response().getBody().asString())
                        .as("Verify that body is null")
                        .isEmpty();
            });
        });

        var actualPlayers = this.playerServiceActions.getAllPlayers(HttpStatus.SC_OK).getPlayers();

        Assertions.assertThat(actualPlayers)
                .extracting(PlayerItemDTO::getId)
                .as("Verify that player is deleted from database")
                .doesNotContain(actualPlayerId);
    }
}
