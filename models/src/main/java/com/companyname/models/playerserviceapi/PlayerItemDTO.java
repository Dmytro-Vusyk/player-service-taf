package com.companyname.models.playerserviceapi;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerItemDTO extends BaseDTO {
    private Integer age;
    private String gender;
    private Long id;
    private String role;
    private String screenName;
}
