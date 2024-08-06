package com.roman.service.mapper.news;

import com.roman.dao.entity.News;
import com.roman.service.dto.news.ShowNewsDto;
import com.roman.service.mapper.date.InstantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapToShowNews {

    private final InstantMapper mapper;

    public ShowNewsDto map(News news){
        return new ShowNewsDto(
                String.valueOf(news.getId()),
                news.getTitle(),
                news.getText(),
                mapper.mapToString(news.getDate()),
                news.getCategory().getTitle());
    }
}
