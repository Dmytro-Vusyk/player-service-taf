package com.companyname;


import com.companyname.config.PropertiesHandler;
import com.companyname.endpoints.PlayerControllerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerServiceAPI {

    private static final Logger logger = LoggerFactory.getLogger(PlayerServiceAPI.class);
    private static PlayerServiceAPI playerServiceAPI;
    private final static ThreadLocal<PlayerControllerEndpoint> playerControllerEndpoint = new ThreadLocal<>();

    private PlayerServiceAPI() {
        logger.debug("Constructing Player Service API");
        // this config should be moved to BaseAPI class(class should be created on commercial framework.)
        var baseUrl = PropertiesHandler.getInstance().getEnvProperties().getProperty("player.service.host");
        initUnversionedEndpoints(baseUrl);
    }

    public static PlayerServiceAPI getAPI() {
        if (PlayerServiceAPI.playerServiceAPI == null) {
            PlayerServiceAPI.playerServiceAPI = new PlayerServiceAPI();
        }
        return PlayerServiceAPI.playerServiceAPI;
    }

    private void initUnversionedEndpoints(String baseUrl) {
        playerControllerEndpoint.set(new PlayerControllerEndpoint(baseUrl));
    }

    public PlayerControllerEndpoint getPlayerControllerEndpoint() {
        return playerControllerEndpoint.get();
    }
}