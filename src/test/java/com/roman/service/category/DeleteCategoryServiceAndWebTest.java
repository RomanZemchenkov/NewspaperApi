package com.roman.service.category;

import com.roman.BaseServiceAndWebTest;
import com.roman.service.service_impl.category.DeleteCategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

public class DeleteCategoryServiceAndWebTest extends BaseServiceAndWebTest {

    private final DeleteCategoryService service;

    @Autowired
    public DeleteCategoryServiceAndWebTest(DeleteCategoryService service) {
        this.service = service;
    }

    @ParameterizedTest
    @DisplayName("Тест удаления категории")
    @MethodSource("argumentsForDeleteByIdTest")
    void deleteCategoryById(String id, boolean shouldBeException){
        if (shouldBeException){
            Assertions.assertThrows(Exception.class, () -> service.delete(id));
        } else {
            Assertions.assertDoesNotThrow(() -> service.delete(id));
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
