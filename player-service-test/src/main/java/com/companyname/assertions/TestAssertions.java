package com.companyname.assertions;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class TestAssertions extends Assertions {

    private TestAssertions() {
        // private constructor to prevent instantiation
    }

    @Step("Assert HTTP Status code is: {expectedStatusCode}")
    public static void assertStatusCodeIs(ValidatableResponse response, int expectedStatusCode){
        assertThat(response.extract().statusCode())
                .as("Verify status code is " + expectedStatusCode)
                .isEqualTo(expectedStatusCode);
    }

    @Step("Assert response matches JSON schema: {schemaPath}")
    public static void assertResponseMatchesJsonSchema(ValidatableResponse response, String schemaPath){
        response.assertThat().body(matchesJsonSchemaInClasspath(schemaPath));
    }
}
