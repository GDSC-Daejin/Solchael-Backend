package com.solchael.solchael.global.config.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class GoogleCloudStorageConfig {

    @Value("${API-KEY.projectId}")
    private String project;

    @Value("${API-KEY.resource}")
    private String resourceJson;

    @Bean
    public Storage storage() throws IOException {

        ClassPathResource resource = new ClassPathResource(resourceJson);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        return StorageOptions.newBuilder()
                .setProjectId(project)
                .setCredentials(credentials)
                .build()
                .getService();
    }
}