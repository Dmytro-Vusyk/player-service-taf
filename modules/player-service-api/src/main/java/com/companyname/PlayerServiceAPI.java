package com.companyname;

import com.companyname.endpoints.PlayerControllerEndpoint;

public class PlayerServiceAPI {

    private static PlayerServiceAPI playerServiceAPI;
    //TODO: replace field initialization with config file
    private static final String BASE_URL = "http://3.68.165.45/";
    private PlayerControllerEndpoint playerControllerEndpoint;

    private PlayerServiceAPI() {
        initUnversionedEndpoints();
    }

    public static PlayerServiceAPI getAPI() {
        if (PlayerServiceAPI.playerServiceAPI == null) {
            PlayerServiceAPI.playerServiceAPI = new PlayerServiceAPI();
        }
        return PlayerServiceAPI.playerServiceAPI;
    }

    private void initUnversionedEndpoints() {
        this.playerControllerEndpoint = new PlayerControllerEndpoint(BASE_URL);
    }

    public PlayerControllerEndpoint getPlayerControllerEndpoint() {
        return this.playerControllerEndpoint;
    }
}