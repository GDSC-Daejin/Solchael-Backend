package com.solchael.solchael.domain.drugbox.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@Setter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DrugBox {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drugbox_id")
    private Long id;

    private String locationName;
    private String locationAddress;
    private Double latitude;
    private Double longitude;
}
