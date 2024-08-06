package com.roman.service.validate;

import com.roman.dao.entity.Category;
import com.roman.dao.entity.News;
import com.roman.dao.repository.category.CategoryRepository;
import com.roman.dao.repository.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdValidate {

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;
    /*
    Не совсем я понимаю, почему если возвращать напрямую mayBeNews.isPresent(), то будет возвращён Optional.empty()
     */

    public boolean checkNewsId(Long id){
        Optional<News> mayBeNews = newsRepository.findById(id);
        boolean present = mayBeNews.isEmpty();
        return present;
    }

    public boolean checkCategoryId(Long id){
        Optional<Category> mayBeCategory = categoryRepository.findById(id);
        boolean present = mayBeCategory.isEmpty();
        return present;
    }
}
