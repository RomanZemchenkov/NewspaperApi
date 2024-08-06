package com.roman.service.dto.category;

import lombok.Getter;

@Getter
public class UpdateCategoryDto {

    private final String id;
    private final String title;

    public UpdateCategoryDto(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
