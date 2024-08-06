package com.roman.service.news;

import com.roman.BaseServiceAndWebTest;
import com.roman.service.dto.news.CreateNewsDto;
import com.roman.service.dto.news.UpdateNewsDto;
import com.roman.service.service_impl.news.CreateNewsService;
import com.roman.service.service_impl.news.UpdateNewsService;
import com.roman.service.validate.exception.TitleExistException;
import com.roman.service.validate.exception.TitlePatternException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

public class UpdateNewsServiceAndWebTest extends BaseServiceAndWebTest {

    private final UpdateNewsService service;
    private final CreateNewsService createService;
    private static final String NEW_NEWS_TITLE = "New title";
    private static final String NEW_NEWS_TEXT = "New text";

    @Autowired
    public UpdateNewsServiceAndWebTest(UpdateNewsService service, CreateNewsService createService) {
        this.service = service;
        this.createService = createService;
    }


    @ParameterizedTest
    @DisplayName("Тест обновления новости")
    @MethodSource("argumentsForUpdateNewsTest")
    void updateNewsTest(UpdateNewsDto dto, boolean shouldBeException,Class<? extends Exception> expectedException){
        CreateNewsDto dto1 = new CreateNewsDto(NEW_NEWS_TITLE, NEW_NEWS_TEXT, EXIST_CATEGORY_ID);
        createService.create(dto1);

        if (shouldBeException){
            Assertions.assertThrows(expectedException,() -> service.update(dto));
        } else {
            Assertions.assertDoesNotThrow(() -> service.update(dto));
        }
    }

    static Stream<Arguments> argumentsForUpdateNewsTest(){
        return Stream.of(
                Arguments.of(new UpdateNewsDto("1",EMPTY_TITLE,EXIST_NEWS_TEXT,EXIST_CATEGORY_ID),true,TitlePatternException.class),
                Arguments.of(new UpdateNewsDto("2",EXIST_NEWS_TITLE,EXIST_NEWS_TEXT,EXIST_CATEGORY_ID),true, TitleExistException.class),
                Arguments.of(new UpdateNewsDto("2",EXIST_NEWS_TITLE,"newText",EXIST_CATEGORY_ID),true,TitleExistException.class),
                Arguments.of(new UpdateNewsDto("1","newTitle","newText",EXIST_CATEGORY_ID),false,WITHOUT_EXCEPTION),
                Arguments.of(new UpdateNewsDto("2","validTitle",EXIST_NEWS_TEXT,EXIST_CATEGORY_ID),false,WITHOUT_EXCEPTION)
        );
    }
}
