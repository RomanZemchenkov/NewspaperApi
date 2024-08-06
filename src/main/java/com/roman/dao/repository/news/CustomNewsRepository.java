package com.roman.dao.repository.news;

import com.roman.dao.entity.News;
import com.roman.dao.filter.news.NewsFilter;

import java.util.List;

public interface CustomNewsRepository {

    boolean deleteNewsById(Long id);

    List<News> findNewsByFilter(NewsFilter filter);

    List<News> findAllWithCategory();
}
