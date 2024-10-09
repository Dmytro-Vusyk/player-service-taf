package com.companyname.playerserviceapi.actions;

import com.companyname.PlayerServiceAPI;
import com.companyname.endpoints.PlayerControllerEndpoint;
import com.companyname.models.playerserviceapi.PlayerCreateRequestDTO;
import com.companyname.models.playerserviceapi.PlayerUpdateRequestDTO;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class PlayerServiceApiActions {


    private final PlayerControllerEndpoint playerControllerEndpoint;

    public PlayerServiceApiActions() {
        this.playerControllerEndpoint = PlayerServiceAPI.getAPI().getPlayerControllerEndpoint();
    }

    @Step("Create player")
    public ValidatableResponse createPlayer(String editor, PlayerCreateRequestDTO player) {
        return this.playerControllerEndpoint.createPlayer(editor, player);
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

    @Step("Get all players")
    public ValidatableResponse getAllPlayers() {
        return this.playerControllerEndpoint.getAllPlayers();
    }
}
