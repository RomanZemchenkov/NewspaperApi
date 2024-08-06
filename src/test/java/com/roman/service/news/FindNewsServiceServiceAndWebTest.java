package com.roman.service.news;

import com.roman.BaseServiceAndWebTest;
import com.roman.service.dto.news.ShowNewsDto;
import com.roman.service.service_impl.news.FindNewsService;
import com.roman.service.validate.exception.NewsDoesntExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FindNewsServiceServiceAndWebTest extends BaseServiceAndWebTest {

    private final FindNewsService service;

    @Autowired
    public FindNewsServiceServiceAndWebTest(FindNewsService service) {
        this.service = service;
    }

    @ParameterizedTest
    @DisplayName("Поиск новости по id")
    @MethodSource("argumentsForFindByIdTest")
    void findNewsById(String id, boolean shouldBeException) {
        if (shouldBeException){
            Assertions.assertThrows(NewsDoesntExistException.class,() -> service.findById(id));
        } else {
            Assertions.assertDoesNotThrow(() -> service.findById(id));
        }
    }

    static Stream<Arguments> argumentsForFindByIdTest() {
        return Stream.of(
                Arguments.of("1", false),
                Arguments.of("0", true),
                Arguments.of("100", true));
    }

    @Test
    @DisplayName("Поиск всех новостей")
    void findAllNews(){
        List<ShowNewsDto> all = service.findAll();
        assertThat(all).hasSize(1);
    }
}
