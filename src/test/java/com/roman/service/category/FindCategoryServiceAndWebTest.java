package com.roman.service.category;

import com.roman.BaseServiceAndWebTest;
import com.roman.service.service_impl.category.FindCategoryService;
import com.roman.service.validate.exception.CategoryDoesntExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FindCategoryServiceAndWebTest extends BaseServiceAndWebTest {

    private final FindCategoryService service;

    @Autowired
    public FindCategoryServiceAndWebTest(FindCategoryService service) {
        this.service = service;
    }

    @ParameterizedTest
    @DisplayName("Поиск категории по id")
    @MethodSource("argumentsForFindCategoryById")
    void findCategoryById(String id, boolean shouldBeException){
        if (shouldBeException){
            Assertions.assertThrows(CategoryDoesntExistException.class,() -> service.findById(id));
        } else {
            Assertions.assertDoesNotThrow(() -> service.findById(id));
        }
    }

    static Stream<Arguments> argumentsForFindCategoryById(){
        return Stream.of(
                Arguments.of("1",false),
                Arguments.of("0",true),
                Arguments.of("2",true)
        );
    }

    @Test
    @DisplayName("Поиск всех категорий")
    void findAllCategories(){
        assertThat(service.findAll()).hasSize(1);
    }
}
