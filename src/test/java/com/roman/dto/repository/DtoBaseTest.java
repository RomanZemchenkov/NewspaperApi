package com.roman.dto.repository;

import com.roman.BaseTest;
import com.roman.dao.entity.Category;
import com.roman.dao.entity.News;
import com.roman.dao.repository.category.CategoryRepository;
import com.roman.dao.repository.news.NewsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:sql/init.sql","classpath:sql/load.sql"})
public class DtoBaseTest extends BaseTest {

    protected final CategoryRepository repository;
    protected final NewsRepository newsRepository;
    protected static final String CATEGORY_TITLE = "Base_category_title";

    @Autowired
    public DtoBaseTest(CategoryRepository repository, NewsRepository newsRepository) {
        this.repository = repository;
        this.newsRepository = newsRepository;
    }

    @AfterEach
    @Transactional
    void downUp(){
        repository.deleteAll();
    }

}
