package com.solchael.solchael.service;

import com.solchael.solchael.dto.MedicineDto;
import com.solchael.solchael.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final MedicineRepository medicineRepository;

    @Value("${API-KEY.adminKey}")
    private String apiKey;

    @Value("${API-KEY.apiUrl}")
    private String apiUrl;


    @Transactional
    public void loadData() {
        int page = 1;
        try {
            while (true) {
                String url = apiUrl(page);

                //  페이지에 접근해줄 Document 객체 생성
                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                Document doc = dBuilder.parse(url);

                doc.getDocumentElement().normalize(); // 루트 태그를 가져옴
//                System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

                // 파싱할 정보가 있는 tag에 접근
                NodeList nList = doc.getElementsByTagName("item");
//                System.out.println("파싱할 리스트 수 : "+ nList.getLength());

                // DB 에 저장
                saveData(nList);
                page += 1;
                if (page >= 48)  break; // 데이터가 4798개 있으니까
                System.out.println("page number : "+ page);
            } // while end
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DB 에 저장하는 메서드
    private void saveData(NodeList nList) {
        for(int temp = 0; temp < nList.getLength(); temp++){
            Node nNode = nList.item(temp);
            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) nNode;
                String itemName = getTagValue("itemName", eElement);
                String useMethodQesitm = getTagValue("useMethodQesitm", eElement);
                String efcyQesitm = getTagValue("efcyQesitm", eElement);
                String atpnQesitm = getTagValue("atpnQesitm", eElement);
                String seQesitm = getTagValue("seQesitm", eElement);
                String itemImage = getTagValue("itemImage", eElement);;

                System.out.println("itemName : " + itemName);

//                MedicineDto medicineDto = MedicineDto.createMedicine(itemName, useMethodQesitm, efcyQesitm, atpnQesitm, seQesitm, itemImage);

//                medicineRepository.save(medicineDto.fromEntity(medicineDto));
            }	// if end
        }	// for end
    }

    // 공공 데이터 api에 접속할 url 생성
    public String apiUrl(int page) throws UnsupportedEncodingException {

        return apiUrl + "?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey + // api 키
                "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(page), "UTF-8") + // 페이지 번호
                "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("3", "UTF-8") + // 한 페이지 결과 수
                "&" + URLEncoder.encode("itemName", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8") + // 제품명
                "&" + URLEncoder.encode("efcyQesitm", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8") + // 효능
                "&" + URLEncoder.encode("useMethodQesitm", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8") + // 사용법
                "&" + URLEncoder.encode("atpnQesitm", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8") + // 주의사항
                "&" + URLEncoder.encode("seQesitm", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8") + // 부작용
                "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8");
    }

    // xml 문서에서 지정된 태그의 값 추출하는 메서드
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }
}
