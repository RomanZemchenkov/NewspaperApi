package com.roman.service.service_impl.category;

import com.roman.dao.entity.Category;
import com.roman.dao.repository.category.CategoryRepository;
import com.roman.service.dto.category.CreateCategoryDto;
import com.roman.service.dto.category.ShowCategoryDto;
import com.roman.service.mapper.category.MapToCategory;
import com.roman.service.mapper.category.MapToShowCategoryDto;
import com.roman.service.validate.category.CategoryValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryService {

    private final CategoryRepository repository;
    private final MapToCategory mapToCategory;
    private final CategoryValidate validate;
    private final MapToShowCategoryDto mapToShow;

    public ShowCategoryDto create(CreateCategoryDto dto){
        String title = dto.getTitle();
        validate.checkTitlePattern(title);
        validate.checkTitleExist(title);

        Category category = mapToCategory.map(dto);
        Category savedCategory = repository.save(category);

        return mapToShow.map(savedCategory);
    }
}
