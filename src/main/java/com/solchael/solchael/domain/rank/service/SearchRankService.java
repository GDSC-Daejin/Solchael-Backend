package com.solchael.solchael.domain.rank.service;

import com.solchael.solchael.domain.medicine.entity.Medicine;
import com.solchael.solchael.domain.medicine.repository.MedicineRepository;
import com.solchael.solchael.domain.rank.dto.SearchRankDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchRankService {

    private static final String CACHE_KEY = "RANK";

    private final MedicineRepository medicineRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public void save(SearchRankDto searchRankDto) {
        String id = searchRankDto.getId();
        Double currentScore = redisTemplate.opsForZSet().score(CACHE_KEY, id);

        if (currentScore != null) {
            // 이미 존재하는 경우 현재 score에 1을 더하여 저장
            redisTemplate.opsForZSet().add(CACHE_KEY, id, currentScore + 1);
        } else {
            // 처음 추가되는 경우 1로 초기화
            redisTemplate.opsForZSet().add(CACHE_KEY, id, 1);
        }
        redisTemplate.expire(CACHE_KEY, 1, TimeUnit.HOURS);
    }

    public List<SearchRankDto> getAllRank() {
        // 내림차순으로 정렬된 상위 10개에 속하는 score를 가져와서 list에 저장
        Set<ZSetOperations.TypedTuple<String>> list = redisTemplate.opsForZSet().reverseRangeWithScores(CACHE_KEY, 0, 9);

        return list.stream()
                .map(this::getSearchRankDtoByTuple)
                .collect(Collectors.toList());
    }

    // 이건 그냥 swagger에서 보기 편하라고 객체로 변환한 것
    private SearchRankDto getSearchRankDtoByTuple(ZSetOperations.TypedTuple<String> tuple) {
        String id = tuple.getValue();
        Double score = tuple.getScore();

        // id를 기반으로 다른 정보를 가져와 SearchRankDto 객체를 생성
        Medicine medicine = medicineRepository.findById(Long.parseLong(id));

        // Medicine 객체와 score 정보를 활용하여 SearchRankDto 객체 생성
        return SearchRankDto.builder()
                .id(medicine.getId().toString())
                .name(medicine.getName())
                .count(score.intValue())  // 소수점 버림
                .build();
    }
}
