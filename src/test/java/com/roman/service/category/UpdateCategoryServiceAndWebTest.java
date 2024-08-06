package com.roman.service.category;

import com.roman.BaseServiceAndWebTest;
import com.roman.service.dto.category.CreateCategoryDto;
import com.roman.service.dto.category.UpdateCategoryDto;
import com.roman.service.service_impl.category.CreateCategoryService;
import com.roman.service.service_impl.category.UpdateCategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

public class UpdateCategoryServiceAndWebTest extends BaseServiceAndWebTest {

    private final UpdateCategoryService service;
    private final CreateCategoryService createService;
    private static final String NEW_CATEGORY_TITLE = "New title";

    @Autowired
    public UpdateCategoryServiceAndWebTest(UpdateCategoryService service, CreateCategoryService createService) {
        this.service = service;
        this.createService = createService;
    }

    @ParameterizedTest
    @DisplayName("Тест обновления категории")
    @MethodSource("argumentsForUpdateTest")
    void updateCategoryTest(UpdateCategoryDto dto, boolean shouldBeException) {
        createService.create(new CreateCategoryDto(NEW_CATEGORY_TITLE));
        if (shouldBeException){
            Assertions.assertThrows(Exception.class,() -> service.update(dto));
        } else {
            Assertions.assertDoesNotThrow(() -> service.update(dto));
        }
    }

    static Stream<Arguments> argumentsForUpdateTest() {
        return Stream.of(
                Arguments.of(new UpdateCategoryDto("1", EMPTY_TITLE), true),
                Arguments.of(new UpdateCategoryDto("2", EXIST_CATEGORY_TITLE), true),
                Arguments.of(new UpdateCategoryDto("2",EMPTY_TITLE), true),
                Arguments.of(new UpdateCategoryDto("1","Not Exist title"), false)
        );
    }
}
