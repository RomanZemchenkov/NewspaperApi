package com.roman.service.service_impl.category;

import com.roman.dao.repository.category.CategoryRepository;
import com.roman.service.validate.category.CategoryValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCategoryService {

    private final CategoryRepository repository;
    private final CategoryValidate validate;

    public Long delete(String id){
        Long categoryId = Long.valueOf(id);

        validate.checkId(categoryId);

        return repository.deleteCategoryById(categoryId) ? categoryId : 0L;
    }
}
