package com.companyname.endpoints;

public class PlayerControllerEndpoint {
    private final String baseUrl;
    private final String CREATE_PLAYER_URL = "/player/create/{}";
    private final String DELETE_PLAYER_URL = "/player/delete/{}";
    private final String GET_PLAYER_URL = "/player/get";
    private final String GET_ALL_PLAYERS_URL = "/player/get/all";
    private final String UPDATE_PLAYER_URL = "/player/update/{}/{}";


    public PlayerControllerEndpoint(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void createPlayer(){

    }

    public void deletePlayer(){

    }

    public void getPlayerById(){

    }

    public void getAllPlayers(){

    }

    public void updatePlayer(){

    }
}
