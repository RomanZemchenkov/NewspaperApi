package com.roman.service.service_impl.category;

import com.roman.dao.entity.Category;
import com.roman.dao.repository.category.CategoryRepository;
import com.roman.service.dto.category.ShowCategoryDto;
import com.roman.service.mapper.category.MapToShowCategoryDto;
import com.roman.service.validate.category.CategoryValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindCategoryService {

    private final CategoryRepository repository;
    private final CategoryValidate validate;
    private final MapToShowCategoryDto map;

    public ShowCategoryDto findById(String id){

        Long categoryId = Long.valueOf(id);
        validate.checkId(categoryId);

        Category category = repository.findById(categoryId).get();
        return map.map(category);
    }

    public List<ShowCategoryDto> findAll(){
        List<Category> all = repository.findAll();
        return all.stream().map(map::map).toList();
    }
}
