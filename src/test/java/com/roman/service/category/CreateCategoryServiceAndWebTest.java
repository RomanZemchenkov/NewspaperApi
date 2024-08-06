package com.roman.service.category;

import com.roman.BaseServiceAndWebTest;
import com.roman.service.dto.category.CreateCategoryDto;
import com.roman.service.service_impl.category.CreateCategoryService;
import com.roman.service.validate.exception.TitleExistException;
import com.roman.service.validate.exception.TitlePatternException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

public class CreateCategoryServiceAndWebTest extends BaseServiceAndWebTest {

    private final CreateCategoryService service;

    @Autowired
    public CreateCategoryServiceAndWebTest(CreateCategoryService service) {
        this.service = service;
    }

    @ParameterizedTest
    @DisplayName("Тест создания категории")
    @MethodSource("argumentsForCreateTest")
    void createCategoryTest(CreateCategoryDto dto, boolean shouldBeException, Class<? extends Exception> expectedException){
        if (shouldBeException){
            Assertions.assertThrows(expectedException,() -> service.create(dto));
        } else {
            Assertions.assertDoesNotThrow(() -> service.create(dto));
        }
    }

    static Stream<Arguments> argumentsForCreateTest(){
        return Stream.of(
                Arguments.of(new CreateCategoryDto(EXIST_CATEGORY_TITLE),true, TitleExistException.class),
                Arguments.of(new CreateCategoryDto(EMPTY_TITLE),true, TitlePatternException.class),
                Arguments.of(new CreateCategoryDto("New Title"),false,WITHOUT_EXCEPTION)
        );
    }
}
