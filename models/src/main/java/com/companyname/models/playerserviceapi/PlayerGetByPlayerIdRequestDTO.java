package com.companyname.models.playerserviceapi;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerGetByPlayerIdRequestDTO {
    private Long playerId;
}
