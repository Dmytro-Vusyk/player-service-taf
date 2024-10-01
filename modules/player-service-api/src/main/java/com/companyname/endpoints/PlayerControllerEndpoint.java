package com.companyname.endpoints;

import com.companyname.models.playerserviceapi.PlayerCreateRequestDTO;
import com.companyname.models.playerserviceapi.PlayerDeleteRequestDTO;
import com.companyname.models.playerserviceapi.PlayerGetByPlayerIdRequestDTO;
import com.companyname.models.playerserviceapi.PlayerUpdateRequestDTO;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

/**
 * This class describes realization of the type Player controller endpoint.
 */
public class PlayerControllerEndpoint {
    private final String baseUrl;
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
        this.baseUrl = baseUrl;
        constructRequestSpec();
    }

    /**
     * Send GET request to create player.
     *
     * @param editor             the editor
     * @param player             the player
     * @return the {@link ValidatableResponse}
     */
    public ValidatableResponse createPlayer(String editor, PlayerCreateRequestDTO player) {
        var url = String.format(CREATE_PLAYER_URL, editor);
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
    public ValidatableResponse deletePlayer(String editor, Long id) {
        var url = String.format(DELETE_PLAYER_URL, editor);
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
    public ValidatableResponse getPlayerById(Long id) {
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
    public ValidatableResponse getAllPlayers() {
        return RestAssured
                .given(this.requestSpecification)
                .when()
                .get(GET_ALL_PLAYERS_URL)
                .then();
    }

    /**
     * Send UPDATE player requset.
     *
     * @param editor the editor
     * @param id     the id
     * @param player the {@link PlayerUpdateRequestDTO} with fields to update
     * @return the validatable response
     */
    public ValidatableResponse updatePlayer(String editor, Long id, PlayerUpdateRequestDTO player) {
        var url = String.format(UPDATE_PLAYER_URL, editor, id);
        return RestAssured
                .given(this.requestSpecification)
                .body(player)
                .when()
                .patch(url)
                .then();
    }

    private void constructRequestSpec() {
        this.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setContentType(ContentType.JSON)
                .build();
    }
}
