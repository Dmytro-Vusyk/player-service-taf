package com.companyname.models.playerserviceapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerGetByPlayerIdRequestDTO extends BaseDTO {
    private Long playerId;
}
