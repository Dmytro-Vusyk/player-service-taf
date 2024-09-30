package com.companyname.playerservice.functionaltests;

import com.companyname.enums.PlayerEditors;
import com.companyname.factories.PlayerCreateRequestDTOFactory;
import com.companyname.models.playerserviceapi.PlayerCreateResponseDTO;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreatePlayerTest extends PlayerServiceTestSpec {

    private static Long createdPlayerId;

    @DataProvider(name = "roleProvider")
    public Object[][] roleProvider() {
        return new Object[][] {
                {PlayerEditors.SUPERVISOR.getValue()},
                {PlayerEditors.ADMIN.getValue()}
        };
    }

    /**
     * As admin or supervisor I want to be able to create a player
     * This test case reproduces following steps:
     * 1. Make GET call to create player
     * 2. Assert that status code is 200
     * 3. Assert that response schema is as expected
     */
    @Test(dataProvider = "roleProvider")
    public void verifyThatPlayerCanBeCreated(String role) {

        var expectedPlayer = PlayerCreateRequestDTOFactory.createDefaultPlayer();

        var actualResponse = this.playerControllerEndpoint.createPlayer(
                role,
                expectedPlayer,
                HttpStatus.SC_OK);
        var actualPlayer = actualResponse.extract().as(PlayerCreateResponseDTO.class);
        createdPlayerId = actualPlayer.getId();
        actualResponse.assertThat()
                .body(matchesJsonSchemaInClasspath("playerservice/schemas/create-player-response.json"));
    }

    @AfterTest
    void cleanup(){
        this.playerControllerEndpoint.deletePlayer(PlayerEditors.SUPERVISOR.getValue(),createdPlayerId)
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
