package com.companyname.playerservice.functionaltests.update;

import com.companyname.assertions.TestAssertions;
import com.companyname.factories.PlayerFactory;
import com.companyname.models.playerserviceapi.PlayerUpdateRequestDTO;
import com.companyname.playerservice.functionaltests.PlayerServiceTestSpec;
import com.companyname.testutils.TestGroups;
import com.companyname.utils.Faker;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

@Feature("Update Player")
@Story("API consumer is able to update Player")
public class UpdatePlayerNegativeTests extends PlayerServiceTestSpec {

    @Description("Verify that player controller api returns NOT_FOUND when UPDATE operation is performed on non existent user")
    @Severity(SeverityLevel.NORMAL)
    @Issue("PS-12376") //issue for "200 OK status code is returned if update nonexistent user id"
    @TmsLink("PS-327")
    @Tags(@Tag(TestGroups.NEGATIVE))
    @Test(dataProvider = "editorProvider", groups = {TestGroups.NEGATIVE})
    public void testEditorCanNotUpdatePlayerByInvalidId(String editor) {
        var randomPlayerId = Faker.instance().random().nextLong(Long.MAX_VALUE);
        var defaultPlayer = PlayerFactory.getInstance()
                .buildCreatePayload()
                .createDefaultPlayer();
        var playerToUpdate = PlayerUpdateRequestDTO.builder()
                .age(Integer.parseInt(defaultPlayer.getAge()))
                .gender(defaultPlayer.getGender())
                .login(defaultPlayer.getLogin())
                .password(defaultPlayer.getPassword())
                .role(defaultPlayer.getRole())
                .screenName(defaultPlayer.getScreenName())
                .build();

        var response = this.playerServiceActions.updatePlayer(editor, randomPlayerId, playerToUpdate);
        TestAssertions.assertStatusCodeIs(response, HttpStatus.SC_NOT_FOUND);
    }
}
