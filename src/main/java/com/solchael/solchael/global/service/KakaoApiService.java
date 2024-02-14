package com.solchael.solchael.global.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class KakaoApiService {

    public String getKakaoApiFromAddress(String roadFullAddr) throws IOException {
        String apiKey = "승인키";
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        String jsonString = null;

        roadFullAddr = URLEncoder.encode(roadFullAddr, "UTF-8");

        String addr = apiUrl + "?query=" + roadFullAddr;

        URL url = new URL(addr);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);

        BufferedReader rd = null;
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuffer docJson = new StringBuffer();

        String line;

        while ((line=rd.readLine()) != null) {
            docJson.append(line);
        }

        jsonString = docJson.toString();
        rd.close();

        return jsonString;
    }
}
