package com.roman.web.controller.category;

import com.roman.BaseServiceAndWebTest;
import com.roman.service.dto.category.CreateCategoryDto;
import com.roman.service.dto.category.UpdateCategoryDto;
import com.roman.service.service_impl.category.CreateCategoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoryControllerIT extends BaseServiceAndWebTest {

    private final MockMvc mockMvc;
    private static final String WITHOUT_ID = null;
    private static final String NEW_CATEGORY_TITLE = "New title";
    private final CreateCategoryService service;

    @Autowired
    public CategoryControllerIT(MockMvc mockMvc, CreateCategoryService service) {
        this.mockMvc = mockMvc;
        this.service = service;
    }

    @ParameterizedTest
    @DisplayName("Тест создания категории.")
    @MethodSource("argumentsForCreateTest")
    void createCategoryTest(String expectedId,String title, boolean shouldBeException, String exceptionMessage) throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "title" : "%s"
                        }
                        """.formatted(title)));
        if(shouldBeException){
            actions.andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andExpect(jsonPath("$.message", Matchers.is(exceptionMessage)));
        } else {
            actions.andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(jsonPath("$.id",Matchers.is(expectedId)))
                    .andExpect(jsonPath("$.title",Matchers.is(title)));
        }
    }

    static Stream<Arguments> argumentsForCreateTest(){
        return Stream.of(
                Arguments.of(WITHOUT_ID,EXIST_CATEGORY_TITLE,true,"Категория с названием '%s' уже существует.".formatted(EXIST_CATEGORY_TITLE)),
                Arguments.of(WITHOUT_ID,EMPTY_TITLE,true,"Название должно содержать хотя бы один символ."),
                Arguments.of("2","New_category",false,WITHOUT_EXCEPTION)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест поиска категории по id")
    @MethodSource("argumentsForFindTest")
    void findCategoryById(String id,String expectedTitle,boolean shouldBeException,String exceptionMessage) throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/" + id));
        if(shouldBeException){
            actions.andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(jsonPath("$.message",Matchers.is(exceptionMessage)));
        } else {
            actions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.id",Matchers.is(id)))
                    .andExpect(jsonPath("$.title",Matchers.is(expectedTitle)));
        }
    }

    static Stream<Arguments> argumentsForFindTest(){
        return Stream.of(
                Arguments.of("2",null,true,"Категория с id 2 не существует."),
                Arguments.of("100",null,true,"Категория с id 100 не существует."),
                Arguments.of(EXIST_CATEGORY_ID,EXIST_CATEGORY_TITLE,false,WITHOUT_EXCEPTION)
        );
    }

    @Test
    @DisplayName("Тест поиска всех категорий")
    void findAllCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$",Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id",Matchers.is("1")));
    }

    @ParameterizedTest
    @DisplayName("Тест обновления категории")
    @MethodSource("argumentsForUpdateCategoryTest")
    void updateCategory(UpdateCategoryDto dto,boolean shouldBeException,String exceptionMessage) throws Exception {
        service.create(new CreateCategoryDto(NEW_CATEGORY_TITLE));

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "id" : "%s",
                           "title" : "%s"
                        }
                        """.formatted(dto.getId(), dto.getTitle())));

        if (shouldBeException){
            actions.andExpect(status().is4xxClientError())
                    .andExpect(jsonPath("$.message",Matchers.is(exceptionMessage)));
        } else {
            actions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.title",Matchers.is("Valid_category_title")));
        }
    }

    static Stream<Arguments> argumentsForUpdateCategoryTest(){
        return Stream.of(
                Arguments.of(new UpdateCategoryDto("3",EXIST_CATEGORY_TITLE),true,"Категория с id 3 не существует."),
                Arguments.of(new UpdateCategoryDto("1",""),true,"Название должно содержать хотя бы один символ."),
                Arguments.of(new UpdateCategoryDto("1",NEW_CATEGORY_TITLE),true,"Категория с названием '%s' уже существует.".formatted(NEW_CATEGORY_TITLE)),
                Arguments.of(new UpdateCategoryDto("1","Valid_category_title"),false, WITHOUT_EXCEPTION)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест удаления категории по id")
    @MethodSource("argumentsForDeleteCategoryTest")
    void deleteNewsById(String id, boolean shouldBeException) throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/categories/" + id));
        if(shouldBeException){
            actions.andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message",Matchers.is("Категория с id " + id + " не существует.")));
        } else {
            actions.andExpect(MockMvcResultMatchers.status().isNoContent())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message",Matchers.is("Категория с id " + id + " удалена.")));
        }
    }

    static Stream<Arguments> argumentsForDeleteCategoryTest() {
        return Stream.of(
                Arguments.of("1",false),
                Arguments.of("0",true),
                Arguments.of("100",true)
        );
    }
}
