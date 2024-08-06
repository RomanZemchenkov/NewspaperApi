package com.roman.dao.repository.category;

import com.roman.dao.entity.Category;
import com.roman.dao.filter.category.CategoryFilter;

import java.util.List;
import java.util.Optional;

public interface CustomCategoryRepository {

    boolean deleteCategoryById(Long id);

    List<Category> findCategoryByFilter(CategoryFilter filter);

}
