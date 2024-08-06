package com.roman.service.service_impl.news;

import com.roman.dao.entity.News;
import com.roman.dao.repository.news.NewsRepository;
import com.roman.service.dto.news.CreateNewsDto;
import com.roman.service.dto.news.ShowNewsDto;
import com.roman.service.mapper.news.MapToNews;
import com.roman.service.mapper.news.MapToShowNews;
import com.roman.service.validate.category.CategoryValidate;
import com.roman.service.validate.news.NewsValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNewsService {

    private final NewsRepository repository;
    private final MapToNews mapToNews;
    private final MapToShowNews mapToShow;
    private final NewsValidate newsValidate;
    private final CategoryValidate categoryValidate;

    public ShowNewsDto create(CreateNewsDto dto){
        String title = dto.getTitle();
        newsValidate.checkTitlePattern(title);
        newsValidate.checkTitleExist(dto.getTitle());
        categoryValidate.checkId(Long.valueOf(dto.getCategoryId()));

        News newNews = mapToNews.map(dto);
        News crearedNews = repository.save(newNews);

        return mapToShow.map(crearedNews);
    }
}
