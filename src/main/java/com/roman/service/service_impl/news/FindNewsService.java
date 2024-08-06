package com.roman.service.service_impl.news;

import com.roman.dao.entity.News;
import com.roman.dao.filter.news.NewsFilter;
import com.roman.dao.repository.news.NewsRepository;
import com.roman.service.dto.news.ShowNewsDto;
import com.roman.service.mapper.news.MapToShowNews;
import com.roman.service.validate.category.CategoryValidate;
import com.roman.service.validate.news.NewsValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindNewsService {

    private final NewsRepository repository;
    private final NewsValidate newsValidate;
    private final CategoryValidate categoryValidate;
    private final MapToShowNews mapToShow;

    public ShowNewsDto findById(String id){
        Long newsId = Long.valueOf(id);
        newsValidate.checkId(newsId);

        News news = repository.findNewsWithCategoryById(newsId);
        return mapToShow.map(news);
    }

    public List<ShowNewsDto> findAll(){
        return repository.findAllWithCategory().stream().map(mapToShow::map).toList();
    }

    public List<ShowNewsDto> finaAllByCategory(String categoryId){
        categoryValidate.checkId(Long.valueOf(categoryId));

        NewsFilter filter = new NewsFilter.Builder().categoryId(categoryId).build();
        List<News> news = repository.findNewsByFilter(filter);

        return news.stream().map(mapToShow::map).toList();
    }
}
