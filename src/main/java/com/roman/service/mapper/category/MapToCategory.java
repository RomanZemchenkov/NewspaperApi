package com.roman.service.mapper.category;

import com.roman.dao.entity.Category;
import com.roman.service.dto.category.CreateCategoryDto;
import com.roman.service.dto.category.UpdateCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class MapToCategory {

    public Category map(CreateCategoryDto dto){
        return new Category(dto.getTitle());
    }

    public Category map(UpdateCategoryDto dto,Category existCategory){
        existCategory.setTitle(dto.getTitle());
        return existCategory;
    }
}
