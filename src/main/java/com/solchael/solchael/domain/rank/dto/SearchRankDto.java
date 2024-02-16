package com.solchael.solchael.domain.rank.dto;

import com.solchael.solchael.domain.medicine.entity.Medicine;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Builder
public class SearchRankDto {

    @Id
    private String id;
    private String name;
    private int count;

    public static SearchRankDto createRecentSearchDto(Medicine medicine) {
        return SearchRankDto.builder()
                .id(medicine.getId().toString())
                .name(medicine.getName())
                .build();
    }

}
