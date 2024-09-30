package com.companyname.endpoints;

import com.companyname.models.playerserviceapi.PlayerDeleteRequestDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import com.companyname.models.playerserviceapi.PlayerCreateRequestDTO;

import java.util.Map;

public class PlayerControllerEndpoint {
    private final String baseUrl;
    private final String CREATE_PLAYER_URL = "/player/create/%s";
    private final String DELETE_PLAYER_URL = "/player/delete/%s";
    private final String GET_PLAYER_URL = "/player/get";
    private final String GET_ALL_PLAYERS_URL = "/player/get/all";
    private final String UPDATE_PLAYER_URL = "/player/update/%s/%s";
    private RequestSpecification requestSpecification;


    public PlayerControllerEndpoint(String baseUrl) {
        this.baseUrl = baseUrl;
        constructRequestSpec();
    }

    public ValidatableResponse createPlayer(String editor, PlayerCreateRequestDTO player, int expectedStatusCode) {
        var url = String.format(CREATE_PLAYER_URL, editor);
        var playerFieldsMap = convertToMap(player);
        return RestAssured
                .given(this.requestSpecification)
                .params(playerFieldsMap)
                .when()
                .get(url)
                .then()
                .statusCode(expectedStatusCode);
    }

    public ValidatableResponse deletePlayer(String editor, Long id) {
        var url = String.format(DELETE_PLAYER_URL, editor);
        var playerToDelete = new PlayerDeleteRequestDTO(id);
        return RestAssured
                .given(this.requestSpecification)
                .contentType(ContentType.JSON)
                .body(playerToDelete)
                .when()
                .delete(url)
                .then();
    }

    public void getPlayerById() {

    }

    public void getAllPlayers() {

    }

    public void updatePlayer() {

    }

    //TODO: move method to DTO base class
    private static Map<String, String> convertToMap(Object dto) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {
        });
    }

    private void constructRequestSpec() {
        this.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }
}
