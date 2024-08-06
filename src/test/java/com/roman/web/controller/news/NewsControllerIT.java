package com.roman.web.controller.news;

import com.roman.BaseServiceAndWebTest;
import com.roman.service.dto.news.UpdateNewsDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

@AutoConfigureMockMvc
@SpringBootTest
public class NewsControllerIT extends BaseServiceAndWebTest {

    private final MockMvc mockMvc;
    private static final String WITHOUT_ID = null;
    private static final String WITHOUT_EXCEPTION = null;

    @Autowired
    public NewsControllerIT(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @ParameterizedTest
    @DisplayName("Тест создания новости")
    @MethodSource("argumentsForCreateNewsTest")
    void createNewsTest(String title, String text, String id,String categoryId, boolean shouldBeException, String exception) throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/api/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                         {
                          "title" : "%s",
                          "text" : "%s",
                          "categoryId" : "%s"
                         }
                         """.formatted(title,text,EXIST_CATEGORY_ID)));
        if (shouldBeException) {
            actions.andExpect(MockMvcResultMatchers.status().isConflict())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(exception)));
        } else {
            actions.andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(id)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(title)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.text", Matchers.is(text)));
        }
    }

    static Stream<Arguments> argumentsForCreateNewsTest() {
        return Stream.of(
                Arguments.of(EXIST_NEWS_TITLE, EXIST_NEWS_TEXT, WITHOUT_ID, EXIST_CATEGORY_ID, true, "Новость с названием '%s' уже существует.".formatted(EXIST_NEWS_TITLE)),
                Arguments.of("", EXIST_NEWS_TEXT, WITHOUT_ID,EXIST_CATEGORY_ID, true, "Название должно содержать хотя бы один символ."),
                Arguments.of("newTitle", "Text", "2", "2", false, "Категория с id 2 не существует."),
                Arguments.of("newTitle", "", "2", EXIST_CATEGORY_ID, false, WITHOUT_EXCEPTION),
                Arguments.of("newTitle2", "Text", "2", EXIST_CATEGORY_ID, false, WITHOUT_EXCEPTION)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест поиска новости по id")
    @MethodSource("argumentsForFindNewsByIdTest")
    void findNewsById(String id, boolean shouldBeException) throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/api/news/" + id));
        if (shouldBeException) {
            actions.andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Новость с id " + id + " не существует.")));
        } else {
            actions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(id)));
        }
    }

    static Stream<Arguments> argumentsForFindNewsByIdTest() {
        return Stream.of(
                Arguments.of("1", false),
                Arguments.of("0", true),
                Arguments.of("100", true)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест поиска всех новостей")
    @MethodSource("argumentsForFindAllNewsTest")
    void findAllNews(int expectedCount, boolean shouldBeException) throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/api/news"));
        if(shouldBeException){
            actions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$",Matchers.not(Matchers.hasSize(expectedCount))));
        } else {
            actions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(expectedCount)));
        }
    }

    static Stream<Arguments> argumentsForFindAllNewsTest() {
        return Stream.of(
                Arguments.of(1,false),
                Arguments.of(2,true)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест обновления новости")
    @MethodSource("argumentsForUpdateNewsTest")
    void updateNews(UpdateNewsDto dto, boolean shouldBeException, String exception) throws Exception {
        String id = dto.getId();
        String title = dto.getTitle();
        String text = dto.getText();
        String categoryId = dto.getCategoryId();
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/api/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "id" : "%s",
                           "title" : "%s",
                           "text" : "%s",
                           "categoryId" : "%s"
                        }
                        """.formatted(id, title, text, categoryId)));
        if (shouldBeException) {
            actions.andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message",Matchers.is(exception)));
        } else {
            actions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(id)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(title)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.text", Matchers.is(text)));
        }
    }

    static Stream<Arguments> argumentsForUpdateNewsTest() {
        return Stream.of(
                Arguments.of(new UpdateNewsDto("1","",EXIST_NEWS_TEXT,EXIST_CATEGORY_ID),true, "Название должно содержать хотя бы один символ."),
                Arguments.of(new UpdateNewsDto("1",EXIST_NEWS_TITLE,EXIST_NEWS_TEXT,"2"), true,"Категория с id 2 не существует."),
                Arguments.of(new UpdateNewsDto("100",EXIST_NEWS_TITLE,EXIST_NEWS_TEXT,EXIST_CATEGORY_ID), true, "Новость с id " + 100 + " не существует."),
                Arguments.of(new UpdateNewsDto("2",EXIST_NEWS_TITLE,EXIST_NEWS_TEXT,EXIST_CATEGORY_ID), true,"Новость с id " + 2 + " не существует."),
                Arguments.of(new UpdateNewsDto("1","newTitle",EXIST_NEWS_TEXT,EXIST_CATEGORY_ID), false, WITHOUT_EXCEPTION)
                );
    }

    @ParameterizedTest
    @DisplayName("Тест удаления новости по id")
    @MethodSource("argumentsForDeleteNewsTest")
    void deleteNewsById(String id, boolean shouldBeException) throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/news/" + id));
        if(shouldBeException){
            actions.andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message",Matchers.is("Новость с id " + id + " не существует.")));
        } else {
            actions.andExpect(MockMvcResultMatchers.status().isNoContent())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message",Matchers.is("Новость с id " + id + " удалена.")));
        }
    }

    static Stream<Arguments> argumentsForDeleteNewsTest() {
        return Stream.of(
                Arguments.of("1",false),
                Arguments.of("0",true),
                Arguments.of("100",true)
        );
    }


}


