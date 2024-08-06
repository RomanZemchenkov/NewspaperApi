package com.roman.service.dto.news;

import lombok.Getter;

@Getter
public class UpdateNewsDto {

    private final String id;
    private final String title;
    private final String text;
    private final String categoryId;

    public UpdateNewsDto(String id, String title, String text, String categoryId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.categoryId = categoryId;
    }
}
