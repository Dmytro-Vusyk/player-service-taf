package com.companyname.playerservice.functionaltests.update;

import com.companyname.assertions.TestAssertions;
import com.companyname.factories.PlayerCreateRequestDTOFactory;
import com.companyname.models.playerserviceapi.PlayerUpdateRequestDTO;
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

@Feature("Update Player")
@Story("API consumer is able to update Player")
public class UpdatePlayerTests extends PlayerServiceTestSpec {

    private ThreadLocal<Long> createdPlayerId = new ThreadLocal<>();

    @Description("Verify that editor is able to UPDATE Player")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("PS-322")
    @Tags(@Tag(TestGroups.SMOKE))
    @Test(dataProvider = "editorProvider", groups = {TestGroups.SMOKE, TestGroups.BVT})
    public void testEditorCanUpdatePlayer(String editor) {
        var defaultPlayer = PlayerCreateRequestDTOFactory.createDefaultPlayer();
        var createdPlayer = this.playerServiceActions.createPlayer(editor, defaultPlayer, HttpStatus.SC_OK);
        createdPlayerId.set(createdPlayer.getId());

        var expectedAge = this.faker.getRandomInt(20, 60);
        var expectedGender = this.faker.ancient().god();

        var playerToUpdate = PlayerUpdateRequestDTO.builder()
                .age(expectedAge)
                .gender(expectedGender)
                .login(defaultPlayer.getLogin())
                .password(defaultPlayer.getPassword())
                .role(defaultPlayer.getRole())
                .screenName(defaultPlayer.getScreenName())
                .build();

        var playerUpdateResponse = this.playerServiceActions.updatePlayer(editor, createdPlayer.getId(), playerToUpdate);
        TestAssertions.assertStatusCodeIs(playerUpdateResponse, HttpStatus.SC_OK);
        TestAssertions.assertResponseMatchesJsonSchema(playerUpdateResponse, SchemaPath.UPDATE_PLAYER_RESPONSE_SCHEMA);

        var actualPlayer = this.playerServiceActions.getPlayerById(createdPlayer.getId(), HttpStatus.SC_OK);

        Allure.step("Verify that fields are changed", () -> {
            SoftAssertions.assertSoftly(assertions -> {
                assertions.assertThat(actualPlayer.getAge())
                        .as("Verify that age is changed")
                        .isEqualTo(expectedAge);
                assertions.assertThat(actualPlayer.getGender())
                        .as("Verify that gender is changed")
                        .isEqualTo(expectedGender);
            });
        });
    }

    @AfterMethod()
    void cleanupTest(Object[] args) {
        this.playerServiceActions.deletePlayer(args[0].toString(), createdPlayerId.get())
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
