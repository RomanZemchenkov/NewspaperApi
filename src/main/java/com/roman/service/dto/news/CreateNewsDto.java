package com.roman.service.dto.news;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateNewsDto {

    private final String title;
    private final String text;
    private final String categoryId;

    public CreateNewsDto(String title, String text, String categoryId) {
        this.title = title;
        this.text = text;
        this.categoryId = categoryId;
    }
}
