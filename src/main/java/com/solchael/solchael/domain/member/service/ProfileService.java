package com.solchael.solchael.domain.member.service;


import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.solchael.solchael.domain.member.entity.Member;
import com.solchael.solchael.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final Storage storage;
    private final MemberRepository memberRepository;

    @Value("${API-KEY.bucketName}")
    private String bucketName;

    @Value("${API-KEY.gcsUrl}")
    private String gcsUrl;

    // 사용자 프로필 이미지 등록
    @Transactional
    public String updateMemberInfo(Long id, MultipartFile image) throws IOException {

        String downloadUrl;
        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage 에 저장될 파일 이름
        String ext = image.getContentType(); // 파일의 형식 ex) JPG

        Member member = memberRepository.findById(id);

        // Cloud에 이미지 업로드
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                image.getInputStream()
        );
        downloadUrl = gcsUrl + bucketName + "/" + uuid;

        member.uploadProfileImage(member, downloadUrl); // DB에 저장

        return downloadUrl;
    }
}
