package com.roman.service.dto.category;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ShowCategoryDto {

    private final String id;
    private final String title;

    public ShowCategoryDto(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
