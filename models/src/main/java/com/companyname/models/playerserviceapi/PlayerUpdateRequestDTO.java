package com.companyname.models.playerserviceapi;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class PlayerUpdateRequestDTO extends BaseDTO {
    private int age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
