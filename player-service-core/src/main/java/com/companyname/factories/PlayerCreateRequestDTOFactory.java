package com.companyname.factories;

import com.companyname.enums.PlayerEditors;
import com.companyname.enums.UserRoles;
import com.companyname.models.playerserviceapi.PlayerCreateRequestDTO;
import com.companyname.utils.Faker;

public class PlayerCreateRequestDTOFactory {
    private static final Faker faker = new Faker();

    public static PlayerCreateRequestDTO createDefaultPlayer() {
        return PlayerCreateRequestDTO.builder()
                .age(String.valueOf(faker.number().numberBetween(18, 60)))
                .editor(faker.options().option(PlayerEditors.class).getValue())
                //TODO: refactor gender to enum
                .gender(faker.options().option("Male", "Female", "Other"))
                .login(faker.funnyName().name())
                .password(faker.regexify("[A-Za-z0-9]{8,12}"))
                .role(UserRoles.USER.getValue())
                .screenName(faker.letterify("?????"))
                .build();
    }

    public static PlayerCreateRequestDTO createCustomPlayer(String age, String editor, String gender, String login, String password, String role, String screenName) {
        return PlayerCreateRequestDTO.builder()
                .age(age)
                .editor(editor)
                .gender(gender)
                .login(login)
                .password(password)
                .role(role)
                .screenName(screenName)
                .build();
    }
}
