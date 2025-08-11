// dto/UpdateMenuRequest.java
package com.example.bootheat.dto;

import jakarta.validation.constraints.Min;

public record UpdateMenuRequest(
        String name,
        @Min(0) Integer price,
        Boolean available,
        String modelUrl,
        String previewImage,
        String description,
        String category // 선택 입력
) {}