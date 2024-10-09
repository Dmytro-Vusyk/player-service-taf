package com.companyname.playerserviceapi.actions;

import com.companyname.PlayerServiceAPI;
import com.companyname.endpoints.PlayerControllerEndpoint;
import com.companyname.models.playerserviceapi.*;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;

public class PlayerServiceApiActions {


    private final PlayerControllerEndpoint playerControllerEndpoint;

    public PlayerServiceApiActions() {
        this.playerControllerEndpoint = PlayerServiceAPI.getAPI().getPlayerControllerEndpoint();
    }

    @Step("Create player")
    public ValidatableResponse createPlayer(String editor, PlayerCreateRequestDTO player) {
        return this.playerControllerEndpoint.createPlayer(editor, player);
    }

    public PlayerCreateResponseDTO createPlayer(String editor, PlayerCreateRequestDTO player, int expectedStatusCode) {
        return createPlayer(editor, player).statusCode(expectedStatusCode)
                .extract()
                .body()
                .as(PlayerCreateResponseDTO.class);
    }

    @Step("Update player")
    public ValidatableResponse updatePlayer(String editor, Long playerToUpdateId, PlayerUpdateRequestDTO playerPayload) {
        return this.playerControllerEndpoint.updatePlayer(editor, playerToUpdateId, playerPayload);
    }

    @Step("Delete player")
    public ValidatableResponse deletePlayer(String editor, Long playerId) {
        return playerControllerEndpoint.deletePlayer(editor, playerId);
    }

    @Step("Get player by id")
    public ValidatableResponse getPlayerById(Long playerId) {
        return this.playerControllerEndpoint.getPlayerById(playerId);
    }

    public PlayerGetByPlayerIdResponseDTO getPlayerById(Long playerId, int expectedStatusCode) {
        return getPlayerById(playerId).statusCode(expectedStatusCode)
                .extract()
                .body()
                .as(PlayerGetByPlayerIdResponseDTO.class);
    }

    @Step("Get all players")
    public ValidatableResponse getAllPlayers() {
        return this.playerControllerEndpoint.getAllPlayers();
    }

    public PlayersDTO getAllPlayers(int expectedStatusCode) {
        return getAllPlayers()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(PlayersDTO.class);
    }
}
