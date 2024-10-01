package com.companyname.models.playerserviceapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerUpdateRequestDTO {
    private int age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
