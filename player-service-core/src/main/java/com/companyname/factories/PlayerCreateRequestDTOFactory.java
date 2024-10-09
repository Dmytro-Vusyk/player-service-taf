package com.companyname.factories;

import com.companyname.enums.Genders;
import com.companyname.enums.PlayerEditors;
import com.companyname.enums.UserRoles;
import com.companyname.models.playerserviceapi.PlayerCreateRequestDTO;
import com.companyname.utils.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Player create request dto factory.
 */
public class PlayerCreateRequestDTOFactory {

    private static final Logger logger = LoggerFactory.getLogger(PlayerCreateRequestDTOFactory.class);
    private static final Faker faker = new Faker();
    private static final String PASSWORD_PATTERN = "[A-Za-z0-9]{8,12}";

    /**
     * Create {@link PlayerCreateRequestDTO} with all default fields.
     *
     * @return the player create request dto
     */
    public PlayerCreateRequestDTO createDefaultPlayer() {
        var player = PlayerCreateRequestDTO.builder()
                .age(String.valueOf(faker.number().numberBetween(18, 60)))
                .editor(faker.options().option(PlayerEditors.class).getValue())
                .gender(faker.options().option(Genders.class).getValue())
                .login(faker.funnyName().name())
                .password(faker.regexify(PASSWORD_PATTERN))
                .role(UserRoles.USER.getValue())
                .screenName(faker.letterify("?????"))
                .build();
        logger.debug("Build payload with all fields: {}", player);
        return player;
    }

    /**
     * Create {@link PlayerCreateRequestDTO} with required fields only.
     *
     * @return the player create request dto
     */
    public PlayerCreateRequestDTO createPlayerWithRequiredFieldsOnly() {
        var player = PlayerCreateRequestDTO.builder()
                .age(String.valueOf(faker.number().numberBetween(18, 60)))
                .editor(faker.options().option(PlayerEditors.class).getValue())
                .gender(faker.options().option(Genders.class).getValue())
                .login(faker.funnyName().name())
                .role(UserRoles.USER.getValue())
                .screenName(faker.letterify("?????"))
                .build();
        logger.debug("Build payload with required fields: {}", player);
        return player;
    }

    /**
     * Create custom {@link PlayerCreateRequestDTO}.
     *
     * @param age        the age
     * @param editor     the editor
     * @param gender     the gender
     * @param login      the login
     * @param password   the password
     * @param role       the role
     * @param screenName the screen name
     * @return the player create request dto
     */
    public PlayerCreateRequestDTO createCustomPlayer(String age, String editor, String gender, String login, String password, String role, String screenName) {
        var player = PlayerCreateRequestDTO.builder()
                .age(age)
                .editor(editor)
                .gender(gender)
                .login(login)
                .password(password)
                .role(role)
                .screenName(screenName)
                .build();
        logger.debug("Build custom payload: {}", player);
        return player;
    }
}
