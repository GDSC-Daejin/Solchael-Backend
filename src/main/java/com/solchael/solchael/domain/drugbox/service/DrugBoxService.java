package com.solchael.solchael.domain.drugbox.service;

import com.solchael.solchael.domain.drugbox.dto.DrugBoxDto;
import com.solchael.solchael.domain.drugbox.dto.DrugBoxResponseDto;
import com.solchael.solchael.domain.drugbox.entity.DrugBox;
import com.solchael.solchael.domain.drugbox.repository.DrugBoxRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DrugBoxService {

    private final DrugBoxRepository drugBoxRepository;

//    // csv 파일 읽어서 DB에 저장 ( 이름, 주소 )
//    @Transactional(readOnly = true)
//    public List<DrugBox> saveAll(List<DrugBox> drugBoxList) {
//        if(CollectionUtils.isEmpty(drugBoxList)) return Collections.emptyList();
//        return drugBoxRepository.saveAll(drugBoxList);
//    }

    public List<DrugBoxResponseDto> findAll() {
        return drugBoxRepository.findAll()
                .stream()
                .map(DrugBoxResponseDto::toEntity)
                .toList();

    }

}
