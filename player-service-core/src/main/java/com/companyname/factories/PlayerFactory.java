package com.companyname.factories;

public class PlayerFactory {

    private static PlayerFactory instance;
    private final PlayerCreateRequestDTOFactory createRequestFactory;

    private PlayerFactory() {
        this.createRequestFactory = new PlayerCreateRequestDTOFactory();
    }

    public static PlayerFactory getInstance() {
        if (instance == null) {
            instance = new PlayerFactory();
        }
        return instance;
    }

    public PlayerCreateRequestDTOFactory buildCreatePayload() {
        return createRequestFactory;
    }
}
