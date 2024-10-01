package com.companyname.models.playerserviceapi;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class PlayerCreateRequestDTO extends BaseDTO {
    private String age;
    private String editor;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
