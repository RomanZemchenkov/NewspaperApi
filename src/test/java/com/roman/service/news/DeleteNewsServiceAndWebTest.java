package com.roman.service.news;

import com.roman.BaseServiceAndWebTest;
import com.roman.service.service_impl.news.DeleteNewsService;
import com.roman.service.validate.exception.NewsDoesntExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteNewsServiceAndWebTest extends BaseServiceAndWebTest {

    private final DeleteNewsService service;

    @Autowired
    public DeleteNewsServiceAndWebTest(DeleteNewsService service) {
        this.service = service;
    }

    @ParameterizedTest
    @DisplayName("Тест удаления новости")
    @MethodSource("argumentsForDeleteByIdTest")
    void deleteNewsById(String id, boolean shouldBeException) {
        if (shouldBeException){
            Assertions.assertThrows(NewsDoesntExistException.class,() -> service.delete(id));
        } else {
            Assertions.assertDoesNotThrow(() -> assertThat(service.delete(id)).isEqualTo(Long.valueOf(id)));
        }
    }

    static Stream<Arguments> argumentsForDeleteByIdTest() {
        return Stream.of(
                Arguments.of("1", false),
                Arguments.of("0", true),
                Arguments.of("100", true)
                );
    }
}
