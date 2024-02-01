package com.solchael.solchael.controller;

import com.solchael.solchael.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OpenApiController {

    private final OpenApiService openApiService;

    @PostMapping("/api/v1/loadData")
    public void loadData() {
        openApiService.loadData();
    }
}