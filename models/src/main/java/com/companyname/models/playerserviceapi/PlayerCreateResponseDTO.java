package com.companyname.models.playerserviceapi;

import lombok.Data;

@Data
public class PlayerCreateResponseDTO {
    private Integer age;
    private String gender;
    private Long id;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
