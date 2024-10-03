package com.companyname.playerservice.functionaltests;

import com.companyname.assertions.TestAssertions;
import com.companyname.config.PropertiesHandler;
import com.companyname.enums.PlayerEditors;
import com.companyname.factories.PlayerCreateRequestDTOFactory;
import com.companyname.models.playerserviceapi.PlayerCreateResponseDTO;
import com.companyname.models.playerserviceapi.PlayerGetByPlayerIdResponseDTO;
import com.companyname.testutils.SchemaPath;
import com.companyname.testutils.TestGroups;
import io.qameta.allure.Allure;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

public class PropertiesTest extends PlayerServiceTestSpec{

    @Test
    public void testProperties(){
    }
    @Test(
//            dataProvider = "editorProvider",
            groups = {TestGroups.SMOKE, TestGroups.BVT})
    public void verifyThatUserCanRetrievePlayerById() {
        var editor = PlayerEditors.SUPERVISOR.getValue();
        var expectedPlayer = PlayerCreateRequestDTOFactory.createDefaultPlayer();
        var expectedPlayerId = this.playerControllerEndpoint.createPlayer(editor, expectedPlayer)
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PlayerCreateResponseDTO.class)
                .getId();

        var playerByIdResponse = this.playerControllerEndpoint.getPlayerById(expectedPlayerId);
        TestAssertions.assertStatusCodeIs(playerByIdResponse, HttpStatus.SC_OK);
        TestAssertions.assertResponseMatchesJsonSchema(playerByIdResponse, SchemaPath.GET_PLAYER_BY_ID_RESPONSE_SCHEMA);

        var actualPlayer = playerByIdResponse.extract().body().as(PlayerGetByPlayerIdResponseDTO.class);

        Allure.step("Verify GET player by id call returns correct player information", () ->{
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

        this.playerControllerEndpoint.deletePlayer(editor, expectedPlayerId)
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
