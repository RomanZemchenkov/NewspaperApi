package com.roman.service.service_impl.news;

import com.roman.dao.entity.News;
import com.roman.dao.repository.news.NewsRepository;
import com.roman.service.dto.news.ShowNewsDto;
import com.roman.service.dto.news.UpdateNewsDto;
import com.roman.service.mapper.news.MapToNews;
import com.roman.service.mapper.news.MapToShowNews;
import com.roman.service.validate.category.CategoryValidate;
import com.roman.service.validate.news.NewsValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateNewsService {

    private final NewsRepository repository;
    private final MapToNews mapToNews;
    private final MapToShowNews mapToShow;
    private final NewsValidate validate;
    private final CategoryValidate categoryValidate;

    public ShowNewsDto update(UpdateNewsDto dto){
        Long newsId = Long.valueOf(dto.getId());
        Long categoryId = Long.valueOf(dto.getCategoryId());
        String title = dto.getTitle();
        validate.checkId(newsId);
        categoryValidate.checkId(categoryId);

        News existNews = repository.findNewsWithCategoryById(newsId);

        if(!existNews.getTitle().equals(title)){
            validate.checkTitlePattern(title);
            validate.checkTitleExist(title);
        }

        News updateNews = mapToNews.map(dto, existNews);

        News updatedNews = repository.save(updateNews);

        return mapToShow.map(updatedNews);
    }
}
