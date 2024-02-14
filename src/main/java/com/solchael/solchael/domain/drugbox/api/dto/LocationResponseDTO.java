package com.solchael.solchael.domain.drugbox.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationResponseDTO {

    @JsonProperty("documents")
    private List<DocumentDto> documentList;
}