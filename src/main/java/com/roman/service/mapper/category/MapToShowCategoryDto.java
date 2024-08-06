package com.roman.service.mapper.category;

import com.roman.dao.entity.Category;
import com.roman.service.dto.category.ShowCategoryDto;
import org.springframework.stereotype.Component;

@Component
public class MapToShowCategoryDto {

    public ShowCategoryDto map(Category category){
        return new ShowCategoryDto(
                String.valueOf(category.getId()),
                category.getTitle()
        );
    }
}
