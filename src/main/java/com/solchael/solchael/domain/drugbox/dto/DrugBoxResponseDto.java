package com.solchael.solchael.domain.drugbox.dto;

import com.solchael.solchael.domain.drugbox.entity.DrugBox;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DrugBoxResponseDto {

    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    public static DrugBoxResponseDto fromEntity(DrugBox drugBox) {

        return DrugBoxResponseDto.builder()
                .name(drugBox.getLocationName())
                .address(drugBox.getLocationAddress())
                .latitude(drugBox.getLatitude())
                .longitude(drugBox.getLongitude())
                .build();
    }
}
