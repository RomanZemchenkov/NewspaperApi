# Газета версия 0.1.0

Данное приложение позволяет создавать, обновлять, находить и удалять категории и статьи.

## Улучшения:
1. В новой версии появилась возможность создавать категории, к которым будут относиться новости.
    Примечение: теперь создать новость без категории невозможно.


## Установка приложения

### Клонирование проекта
```bash
 git clone https://gitlab.skillbox.ru/roman_zemchenkov/java_basics.git
```

### Сборка проекта
```bash
 mvn clean package -DskipTests
```

### Создание Docker образа
```bash
 docker build -t newspaper-api:0.1.0 .
```

### Запуск Docker Compose для создания контейнера
```bash
 cd docker
 docker-compose up --build
```

### Запуск Docker Compose для создания контейнера c инициализацией готовых статей
```bash
 cd docker
 SPRING_PROFILES_ACTIVE=init docker-compose up --build
```

## Использование приложения

После выполнения всех предыдущих шагов приложение будет доступно по адресу
```
http://localhost:8080/api/news
http://localhost:8080/api/categories
```

