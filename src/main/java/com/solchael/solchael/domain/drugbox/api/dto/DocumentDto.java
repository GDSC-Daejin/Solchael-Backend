package com.solchael.solchael.domain.drugbox.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DocumentDto {

    @JsonProperty("y")
    private Double latitude;

    @JsonProperty("x")
    private Double longitude;
}
