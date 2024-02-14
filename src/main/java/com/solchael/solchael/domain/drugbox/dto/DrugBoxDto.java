package com.solchael.solchael.domain.drugbox.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DrugBoxDto {

    private Long id;
    private String locationName;
    private String locationAddress;
}
