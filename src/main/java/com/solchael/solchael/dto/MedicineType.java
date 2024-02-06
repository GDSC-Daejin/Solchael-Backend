package com.solchael.solchael.dto;

import lombok.Getter;

/**
 * pharmaceutical = true 이면 조제약. => 가루약 ( 6개월 )
 * ptp = true 이면 사용자가 직접 유통기한 입력
 * 가루약 ( 6개월 ), 알약 ( 12개월 )
 * 시럽약 ( 1개월 ), 연고나 크림 ( 6개월 ), 안약 ( 1개월 )
 */
@Getter
public enum MedicineType {
    PILL, SYRUP, POWDER, OINTMENT, DROP; // 알약, 시럽약, 가루약, 연고, 점이제 & 점비제 & 안약
}
