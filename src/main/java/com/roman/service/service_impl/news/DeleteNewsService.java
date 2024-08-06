package com.roman.service.service_impl.news;

import com.roman.dao.repository.news.NewsRepository;
import com.roman.service.validate.news.NewsValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteNewsService {

    private final NewsRepository repository;
    private final NewsValidate newsValidate;

    public Long delete(String id){
        Long newsId = Long.valueOf(id);

        newsValidate.checkId(newsId);

        return repository.deleteNewsById(newsId) ? newsId : 0L;
    }

}
