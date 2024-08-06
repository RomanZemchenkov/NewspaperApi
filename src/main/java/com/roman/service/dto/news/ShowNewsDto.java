package com.roman.service.dto.news;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ShowNewsDto {

    private final String id;
    private final String title;
    private final String text;
    private final String date;
    private final String categoryTitle;

    public ShowNewsDto(String id, String title, String text, String date, String categoryTitle) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.categoryTitle = categoryTitle;
    }
}
