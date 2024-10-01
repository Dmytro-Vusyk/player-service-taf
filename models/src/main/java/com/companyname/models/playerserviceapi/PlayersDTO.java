package com.companyname.models.playerserviceapi;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayersDTO extends BaseDTO {
    private List<PlayerItemDTO> players;
}
