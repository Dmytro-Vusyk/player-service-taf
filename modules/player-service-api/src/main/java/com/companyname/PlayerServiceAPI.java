package com.companyname;


import com.companyname.endpoints.PlayerControllerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerServiceAPI {

    private static final Logger logger = LoggerFactory.getLogger(PlayerServiceAPI.class);
    private static PlayerServiceAPI playerServiceAPI;
    //TODO: replace field initialization with config file
    private static final String BASE_URL = "http://3.68.165.45/";
    private final static ThreadLocal<PlayerControllerEndpoint> playerControllerEndpoint = new ThreadLocal<>();

    private PlayerServiceAPI() {
        logger.debug("Constructing Player Service API");
        initUnversionedEndpoints();
    }

    public static PlayerServiceAPI getAPI() {
        if (PlayerServiceAPI.playerServiceAPI == null) {
            PlayerServiceAPI.playerServiceAPI = new PlayerServiceAPI();
        }
        return PlayerServiceAPI.playerServiceAPI;
    }

    private void initUnversionedEndpoints() {
        this.playerControllerEndpoint.set(new PlayerControllerEndpoint(BASE_URL));
    }

    public PlayerControllerEndpoint getPlayerControllerEndpoint() {
        return this.playerControllerEndpoint.get();
    }
}