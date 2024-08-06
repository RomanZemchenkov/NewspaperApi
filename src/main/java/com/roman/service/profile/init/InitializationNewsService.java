package com.roman.service.profile.init;

import com.roman.service.dto.category.CreateCategoryDto;
import com.roman.service.dto.news.CreateNewsDto;
import com.roman.service.service_impl.category.CreateCategoryService;
import com.roman.service.service_impl.news.CreateNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("init")
@RequiredArgsConstructor
@Service
@Transactional
public class InitializationNewsService {

    private final CreateCategoryService categoryService;
    private final CreateNewsService newsService;

    public void init() {
        CreateCategoryDto category1 = new CreateCategoryDto("Технологические новинки");
        CreateCategoryDto category2 = new CreateCategoryDto("Новости здоровья");

        String categoryId1 = categoryService.create(category1).getId();
        String categoryId2 = categoryService.create(category2).getId();

        CreateNewsDto news1 = new CreateNewsDto(
                "Искусственный интеллект в медицинской диагностике",
                "Недавние исследования показали, что искусственный интеллект может значительно улучшить диагностику заболеваний. Новые алгоритмы могут выявлять заболевания на ранних стадиях с высокой точностью.",
                categoryId2
        );

        CreateNewsDto news2 = new CreateNewsDto(
                "Запуск нового смартфона от XYZ",
                "Компания XYZ анонсировала свой новый флагманский смартфон, который будет обладать революционными функциями и улучшенной камерой. Ожидается, что он выйдет в следующем месяце.",
                categoryId1
        );

        CreateNewsDto news3 = new CreateNewsDto(
                "Секреты успешного стартапа в 2024 году",
                "В этом году стартапы могут воспользоваться новыми трендами в области технологий и маркетинга. В статье рассматриваются ключевые аспекты, которые помогут вашему бизнесу выйти на новый уровень.",
                categoryId1
        );

        CreateNewsDto news4 = new CreateNewsDto(
                "Как укрепить иммунную систему в зимний период",
                "Зимний период может быть сложным для поддержания здоровья. В статье даны советы по укреплению иммунной системы, включая правильное питание и физическую активность.",
                categoryId2
        );

        CreateNewsDto news5 = new CreateNewsDto(
                "Новые тренды в фитнесе на 2024 год",
                "Фитнес-индустрия продолжает развиваться, и в 2024 году ожидаются новые тренды и инновации. Мы рассмотрим, что нового можно ожидать в области тренировок и здорового образа жизни.",
                categoryId2
        );

        newsService.create(news1);
        newsService.create(news2);
        newsService.create(news3);
        newsService.create(news4);
        newsService.create(news5);
    }
}
