package com.roman;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:sql/init.sql","classpath:sql/load.sql"})
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BaseServiceAndWebTest extends BaseTest {

    protected static final String WITHOUT_EXCEPTION = null;
    protected static final String EMPTY_TITLE = "";
    protected static final String EXIST_CATEGORY_TITLE = "Base_category_title";
    protected static final String EXIST_CATEGORY_ID = "1";
    protected static final String EXIST_NEWS_TITLE = "News_title";
    protected static final String EXIST_NEWS_TEXT = "News_text";

}
