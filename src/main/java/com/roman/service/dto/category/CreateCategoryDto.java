package com.roman.service.dto.category;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateCategoryDto {

    private String title;

    public CreateCategoryDto(){}

    public CreateCategoryDto(String title) {
        this.title = title;
    }
}
