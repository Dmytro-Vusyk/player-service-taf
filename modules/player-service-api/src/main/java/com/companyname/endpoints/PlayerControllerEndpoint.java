package com.companyname.endpoints;

import com.companyname.models.playerserviceapi.PlayerCreateRequestDTO;
import com.companyname.models.playerserviceapi.PlayerDeleteRequestDTO;
import com.companyname.models.playerserviceapi.PlayerGetByPlayerIdRequestDTO;
import com.companyname.models.playerserviceapi.PlayerUpdateRequestDTO;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class describes realization of the type Player controller endpoint.
 */
public class PlayerControllerEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(PlayerControllerEndpoint.class);
    private final String CREATE_PLAYER_URL = "/player/create/%s";
    private final String DELETE_PLAYER_URL = "/player/delete/%s";
    private final String GET_PLAYER_URL = "/player/get";
    private final String GET_ALL_PLAYERS_URL = "/player/get/all";
    private final String UPDATE_PLAYER_URL = "/player/update/%s/%s";
    private RequestSpecification requestSpecification;


    /**
     * Instantiates a new Player controller endpoint.
     *
     * @param baseUrl the base url
     */
    public PlayerControllerEndpoint(String baseUrl) {
        logger.info("Constructing Endpoint using base url: {}", baseUrl);
        constructRequestSpec(baseUrl);
    }

    /**
     * Send GET request to create player.
     *
     * @param editor the editor
     * @param player the player
     * @return the {@link ValidatableResponse}
     */
    @Step("Execute GET request to CREATE player")
    public ValidatableResponse createPlayer(String editor, PlayerCreateRequestDTO player) {
        var url = String.format(CREATE_PLAYER_URL, editor);
        logger.debug("Executing CREATE player request for url: {}", url);
        var playerFieldsMap = player.convertToMap();
        return RestAssured
                .given(this.requestSpecification)
                .params(playerFieldsMap)
                .when()
                .get(url)
                .then();
    }

    /**
     * Send DELETE player request.
     *
     * @param editor the editor
     * @param id     the id
     * @return the validatable response
     */
    @Step("Execute DELETE request to DELETE player")
    public ValidatableResponse deletePlayer(String editor, Long id) {
        var url = String.format(DELETE_PLAYER_URL, editor);
        logger.debug("Executing DELETE player request for url: {}", url);
        var playerToDelete = new PlayerDeleteRequestDTO(id);
        return RestAssured
                .given(this.requestSpecification)
                .body(playerToDelete)
                .when()
                .delete(url)
                .then();
    }

    /**
     * Send GET request to get player by id.
     *
     * @param id the id
     * @return the player by id
     */
    @Step("Execute POST request to GET player by id")
    public ValidatableResponse getPlayerById(Long id) {
        logger.debug("Executing POST player request to GET player");
        var playerToGet = new PlayerGetByPlayerIdRequestDTO(id);
        return RestAssured
                .given(this.requestSpecification)
                .body(playerToGet)
                .when()
                .post(GET_PLAYER_URL)
                .then();
    }

    /**
     * Send GET all players request.
     *
     * @return the all players
     */
    @Step("Execute GET request to GET all players")
    public ValidatableResponse getAllPlayers() {
        logger.debug("Executing GET player request to GET all players");
        return RestAssured
                .given()
                .spec(this.requestSpecification)
                .when()
                .get(GET_ALL_PLAYERS_URL)
                .then();
    }

    /**
     * Send UPDATE player request.
     *
     * @param editor the editor
     * @param id     the id
     * @param player the {@link PlayerUpdateRequestDTO} with fields to update
     * @return the validatable response
     */
    @Step("Execute PATCH request to UPDATE player")
    public ValidatableResponse updatePlayer(String editor, Long id, PlayerUpdateRequestDTO player) {
        var url = String.format(UPDATE_PLAYER_URL, editor, id);
        logger.debug("Executing PATCH player request to UPDATE player: {}", url);
        return RestAssured
                .given(this.requestSpecification)
                .body(player)
                .when()
                .patch(url)
                .then();
    }

    private void constructRequestSpec(String baseUrl) {
        logger.debug("Set up request specification");
        this.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setContentType(ContentType.JSON)
                .build();
    }
}
