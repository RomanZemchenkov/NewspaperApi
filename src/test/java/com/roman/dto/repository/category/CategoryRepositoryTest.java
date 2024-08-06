package com.roman.dto.repository.category;

import com.roman.dao.entity.Category;
import com.roman.dao.repository.category.CategoryRepository;
import com.roman.dao.repository.news.NewsRepository;
import com.roman.dto.repository.DtoBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CategoryRepositoryTest extends DtoBaseTest {


    @Autowired
    public CategoryRepositoryTest(CategoryRepository repository, NewsRepository newsRepository) {
        super(repository, newsRepository);
    }

    @Test
    @DisplayName("Тест создания новой категории")
    void createCategory() {
        Category category = new Category("New_category");
        Assertions.assertDoesNotThrow(() -> repository.save(category));
    }

    @Test
    @DisplayName("Тест обновления категории")
    void updateCategory() {
        Optional<Category> mayBeCategory = repository.findById(1L);
        assertThat(mayBeCategory.isPresent()).isTrue();

        Category category = mayBeCategory.get();
        category.setTitle("NewTitle");

        Assertions.assertDoesNotThrow(() -> repository.save(category));
    }

    @ParameterizedTest
    @DisplayName("Поиск категории по id")
    @MethodSource("argumentsForFindById")
    void findCategoryById(Long id, boolean shouldBeEntity) {
        Optional<Category> mayBeCategory = repository.findById(id);
        if(!shouldBeEntity){
            assertThat(mayBeCategory.isPresent()).isFalse();
        } else {
            assertThat(mayBeCategory.isPresent()).isTrue();
        }
    }

    static Stream<Arguments> argumentsForFindById() {
        return Stream.of(
                Arguments.of(1L, true),
                Arguments.of(0L, false),
                Arguments.of(100L, false)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест удаления категории по id")
    @MethodSource("argumentsForDeleteById")
    void deleteCategoryById(Long id, boolean shouldBeResult){
        boolean result = repository.deleteCategoryById(id);
        if(shouldBeResult){
            assertThat(result).isTrue();
        } else {
            assertThat(result).isFalse();
        }
    }

    static Stream<Arguments> argumentsForDeleteById() {
        return Stream.of(
                Arguments.of(1L, true),
                Arguments.of(0L, false),
                Arguments.of(100L, false)
        );
    }
}
