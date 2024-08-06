package com.roman.service.service_impl.category;

import com.roman.dao.entity.Category;
import com.roman.dao.repository.category.CategoryRepository;
import com.roman.service.dto.category.ShowCategoryDto;
import com.roman.service.dto.category.UpdateCategoryDto;
import com.roman.service.mapper.category.MapToCategory;
import com.roman.service.mapper.category.MapToShowCategoryDto;
import com.roman.service.validate.category.CategoryValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCategoryService {

    private final CategoryRepository repository;
    private final CategoryValidate validate;
    private final MapToCategory mapToCategory;
    private final MapToShowCategoryDto mapToShowCategory;

    public ShowCategoryDto update(UpdateCategoryDto dto){
        Long categoryId = Long.valueOf(dto.getId());
        String title = dto.getTitle();
        validate.checkId(categoryId);

        Category updateCategory = repository.findById(categoryId).get();
        if(!updateCategory.getTitle().equals(title)){
            validate.checkTitlePattern(title);
        }
        validate.checkTitleExist(title);

        Category updatedCategory = mapToCategory.map(dto, updateCategory);
        return mapToShowCategory.map(updatedCategory);
    }
}
