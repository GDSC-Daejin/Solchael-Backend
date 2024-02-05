package com.solchael.solchael.controller;

import com.solchael.solchael.service.OpenApiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이건 건드리면 안 돼요!")
@RestController
@RequiredArgsConstructor
public class OpenApiController {

    private final OpenApiService openApiService;

    @PostMapping("/api/v1/loadData")
    public void loadData() {
        openApiService.loadData();
    }
}