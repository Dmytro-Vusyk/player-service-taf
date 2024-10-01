package com.companyname.models.playerserviceapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerCreateRequestDTO extends BaseDTO {
    private String age;
    private String editor;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
