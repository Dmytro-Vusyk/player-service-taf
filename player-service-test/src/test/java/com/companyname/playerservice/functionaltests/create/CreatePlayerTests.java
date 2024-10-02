package com.companyname.playerservice.functionaltests.create;

import com.companyname.testutils.SchemaPath;
import com.companyname.testutils.TestGroups;
import com.companyname.factories.PlayerCreateRequestDTOFactory;
import com.companyname.models.playerserviceapi.PlayerCreateResponseDTO;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Feature("Create Player")
@Story("Admin or Supervisor is able to create Player")
public class CreatePlayerTests extends PlayerServiceTestSpec {

    private static Long createdPlayerId;

    @Description("Verify that user with different roles are able to CREATE Players")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("PS-1234")   //Issue id of bug "Admin has no rights to create player"
    @Issue("PS-1235")   //Issue id of bug "Fields with null values are returned in response"
    @TmsLink("PS-321") //Test case id in TMS
    @Tags(@Tag(TestGroups.SMOKE))
    @Test(dataProvider = "editorProvider", groups = {TestGroups.SMOKE, TestGroups.BVT})
    public void testPlayerWithAllFieldsCanBeCreated(String editor) {
        var expectedPlayer = PlayerCreateRequestDTOFactory.createDefaultPlayer();
        var actualResponse = this.playerControllerEndpoint.createPlayer(editor, expectedPlayer);
        actualResponse.statusCode(HttpStatus.SC_OK);
        createdPlayerId = actualResponse.extract()
                .as(PlayerCreateResponseDTO.class)
                .getId();
        actualResponse.assertThat()
                .body(matchesJsonSchemaInClasspath(SchemaPath.CREATE_PLAYER_RESPONSE_SCHEMA));
    }

    @Description("Verify That user with different roles are able to CREATE Player with required fields only")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("PS-1234")   //Issue id of bug "Admin has no rights to create player"
    @TmsLink("PS-322") //Test case id in TMS
    @Tags(@Tag(TestGroups.SMOKE))
    @Test(dataProvider = "editorProvider", groups = {TestGroups.SMOKE, TestGroups.BVT})
    public void testPlayerCanBeCreatedWithRequiredFieldsOnly(String editor) {
        var expectedPlayer = PlayerCreateRequestDTOFactory.createPlayerWithRequiredFieldsOnly();
        var actualResponse = this.playerControllerEndpoint.createPlayer(editor, expectedPlayer);
        actualResponse.statusCode(HttpStatus.SC_OK);
        createdPlayerId = actualResponse.extract()
                .as(PlayerCreateResponseDTO.class)
                .getId();
        actualResponse.assertThat()
                .body(matchesJsonSchemaInClasspath(SchemaPath.CREATE_PLAYER_RESPONSE_SCHEMA));
    }

    @AfterMethod()
    void cleanupTest(Object[] args) {
        this.playerControllerEndpoint.deletePlayer(args[0].toString(), createdPlayerId)
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
