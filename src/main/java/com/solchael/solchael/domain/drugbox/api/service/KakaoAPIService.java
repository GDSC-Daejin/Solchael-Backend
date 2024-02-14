package com.solchael.solchael.domain.drugbox.api.service;

import com.solchael.solchael.domain.drugbox.api.dto.LocationResponseDTO;
import com.solchael.solchael.domain.drugbox.entity.DrugBox;
import com.solchael.solchael.domain.drugbox.repository.DrugBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoAPIService {

    private final DrugBoxRepository drugBoxRepository;

    @Value("${API-KEY.kakao_apiKey}")
    private String kakao_apikey;

    // 좌표값 가져오기
    public void getCoordinate() {
        List<DrugBox> list = drugBoxRepository.findAll();
        List<LocationResponseDTO> coordinate = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            String address = list.get(i).getLocationAddress();
            final HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakao_apikey);

            RestTemplate restTemplate = new RestTemplate();
            String apiURL = "https://dapi.kakao.com/v2/local/search/address.JSON?" +
                    "query=" + address;

            final HttpEntity<String> entity = new HttpEntity<>(headers);
            LocationResponseDTO body = restTemplate.exchange(apiURL, HttpMethod.GET, entity, LocationResponseDTO.class).getBody();
            coordinate.add(body);
        }
        saveCoordinate(coordinate, list);
    }

    // 좌표값 DB 에 저장하기 ( 기존 DB에 update )
    @Transactional
    public void saveCoordinate(List<LocationResponseDTO> coordinate, List<DrugBox> list) {
        for (int i = 0; i < coordinate.size(); i++) {
            DrugBox drugBox = list.get(i);
            drugBox.setLatitude(coordinate.get(i).getDocumentList().get(0).getLatitude());
            drugBox.setLongitude(coordinate.get(i).getDocumentList().get(0).getLongitude());
            drugBoxRepository.save(drugBox);
        }
    }

}