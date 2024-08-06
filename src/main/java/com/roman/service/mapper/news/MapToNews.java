package com.roman.service.mapper.news;

import com.roman.dao.entity.Category;
import com.roman.dao.entity.News;
import com.roman.dao.filter.category.CategoryFilter;
import com.roman.dao.repository.category.CategoryRepository;
import com.roman.service.dto.news.CreateNewsDto;
import com.roman.service.dto.news.UpdateNewsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class MapToNews {

    private final CategoryRepository repository;

    public News map(CreateNewsDto dto){
        Long categoryId = Long.valueOf(dto.getCategoryId());
        Category category = repository.findById(categoryId).get();
        return new News(
                dto.getTitle(),
                dto.getText(),
                category
        );
    }

    public News map(UpdateNewsDto dto, News existNews){
        existNews.setTitle(dto.getTitle());
        existNews.setText(dto.getText());
        existNews.setDate(Instant.now(Clock.systemDefaultZone()));
        Category category = repository.findById(Long.valueOf(dto.getCategoryId())).get();
        existNews.setCategory(category);
        return existNews;
    }
}
