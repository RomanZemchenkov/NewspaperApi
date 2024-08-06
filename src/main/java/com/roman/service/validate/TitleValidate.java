package com.roman.service.validate;

import com.roman.dao.entity.Category;
import com.roman.dao.entity.News;
import com.roman.dao.filter.category.CategoryFilter;
import com.roman.dao.filter.news.NewsFilter;
import com.roman.dao.repository.category.CategoryRepository;
import com.roman.dao.repository.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class TitleValidate {

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;
    private static final String TITLE_PATTERN = "([\\p{L}\\p{N}ёЁ]+(\\s*\\.*_*)?)+";

    public boolean checkExistNewsTitle(String title){
        NewsFilter filter = new NewsFilter.Builder().title(title).build();
        List<News> mayBeNews = newsRepository.findNewsByFilter(filter);
        return mayBeNews.isEmpty();
    }

    public boolean checkExistCategoryTitle(String title){
        CategoryFilter filter = new CategoryFilter.Builder().title(title).build();
        List<Category> categories = categoryRepository.findCategoryByFilter(filter);
        return categories.isEmpty();
    }

    public boolean checkTitlePattern(String title){
        boolean result = Pattern.matches(TITLE_PATTERN, title);
        return result;
    }
}
