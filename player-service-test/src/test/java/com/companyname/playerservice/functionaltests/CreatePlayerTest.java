package com.companyname.playerservice.functionaltests;

import com.companyname.enums.PlayerEditors;
import com.companyname.factories.PlayerCreateRequestDTOFactory;
import com.companyname.models.playerserviceapi.PlayerCreateResponseDTO;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Feature("Create Player")
@Story("Admin or Supervisor is able to create Player")
public class CreatePlayerTest extends PlayerServiceTestSpec {

    private static Long createdPlayerId;

    @DataProvider(name = "roleProvider")
    public Object[][] roleProvider() {
        return new Object[][]{
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
    @Description("As admin or supervisor I want to be able to create a player")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("PS-1234")
    @Issue("PS-1235")
    @TmsLink("PS-321")
    @Tags(@Tag("smoke"))
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

    @Step("Cleanup: delete user with id: {createdPlayerId}")
    @AfterTest
    void cleanup() {
        this.playerControllerEndpoint.deletePlayer(PlayerEditors.SUPERVISOR.getValue(), createdPlayerId)
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
