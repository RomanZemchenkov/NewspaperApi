package com.roman.dto.repository.news;

import com.roman.dao.entity.Category;
import com.roman.dao.entity.News;
import com.roman.dao.filter.category.CategoryFilter;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional(propagation = Propagation.REQUIRES_NEW)
public class NewsRepositoryTest extends DtoBaseTest {


    @Autowired
    public NewsRepositoryTest(CategoryRepository repository, NewsRepository newsRepository) {
        super(repository, newsRepository);
    }

    @Test
    @DisplayName("Создание новой новости")
    void createNewNews() {
        List<Category> categories = repository.findCategoryByFilter(new CategoryFilter.Builder().title(CATEGORY_TITLE).build());
        assertThat(categories.isEmpty()).isFalse();
        Category category = categories.get(0);
        News news = new News("NewTitle", "NewText", category);
        Assertions.assertDoesNotThrow(() -> newsRepository.save(news));
    }

    /*
    Во время первого теста я получаю исключение, которое пытаюсь перехватить и которое ожидаю
    Но тест падает по какой-то непонятной мне причине
     */
    @Test
    @DisplayName("Обновление новости")
    void updateNews() {
        Optional<News> mayBeNews = newsRepository.findById(1L);
        assertThat(mayBeNews.isPresent()).isTrue();

        News news = mayBeNews.get();
        news.setTitle("New title");
        news.setText("New text");

        Assertions.assertDoesNotThrow(() -> newsRepository.saveAndFlush(news));
    }

    @ParameterizedTest
    @DisplayName("Поиск новости по id")
    @MethodSource("argumentsForFindNewsTest")
    void findCustomer(Long id, boolean expectedResult) {
        Optional<News> customer = newsRepository.findById(id);
        assertThat(customer.isPresent()).isEqualTo(expectedResult);
    }

    static Stream<Arguments> argumentsForFindNewsTest() {
        return Stream.of(
                Arguments.of(1L, true),
                Arguments.of(2L, false));
    }

    @ParameterizedTest
    @DisplayName("Тест удаления новости по id")
    @MethodSource("argumentsForDeleteById")
    void deleteCategoryById(Long id, boolean shouldBeResult){
        boolean result = newsRepository.deleteNewsById(id);
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
