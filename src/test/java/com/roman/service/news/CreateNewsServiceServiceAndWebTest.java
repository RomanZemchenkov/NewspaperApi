package com.roman.service.news;

import com.roman.BaseServiceAndWebTest;
import com.roman.service.dto.news.CreateNewsDto;
import com.roman.service.service_impl.news.CreateNewsService;
import com.roman.service.validate.exception.TitleExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

public class CreateNewsServiceServiceAndWebTest extends BaseServiceAndWebTest {

    private final CreateNewsService service;

    @Autowired
    public CreateNewsServiceServiceAndWebTest(CreateNewsService service) {
        this.service = service;
    }


    @ParameterizedTest
    @DisplayName("Тест создание новости")
    @MethodSource("argumentsForCreateNewsTest")
    void createNews(CreateNewsDto dto, boolean shouldBeException){
        if(shouldBeException){
            Assertions.assertThrows(TitleExistException.class,() -> service.create(dto));
        } else {
            Assertions.assertDoesNotThrow(() -> service.create(dto));
        }
    }

    static Stream<Arguments> argumentsForCreateNewsTest(){
        return Stream.of(
                Arguments.of(new CreateNewsDto(EXIST_NEWS_TITLE,EXIST_NEWS_TEXT,EXIST_CATEGORY_ID),true),
                Arguments.of(new CreateNewsDto(EXIST_NEWS_TITLE,"newText",EXIST_CATEGORY_ID),true),
                Arguments.of(new CreateNewsDto("newTitle",EXIST_NEWS_TEXT,EXIST_CATEGORY_ID),false),
                Arguments.of(new CreateNewsDto("newTitle2","newText",EXIST_CATEGORY_ID),false)
        );
    }
}
