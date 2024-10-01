package com.companyname.models.playerserviceapi;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerCreateResponseDTO extends BaseDTO {
    private Integer age;
    private String gender;
    private Long id;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
