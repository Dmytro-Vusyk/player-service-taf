package com.companyname.models.playerserviceapi;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerUpdateResponseDTO extends BaseDTO {
    private int age;
    private String gender;
    private Long id;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
