# Spring Boot сервис для автоматического поиска и исправления грамматических ошибок в тексте с использованием API Яндекс.Спеллера.

## Технологии
- Java 21
- Spring Data JPA
- Spring RestClient
- JUnit 5 & Mockito

# Запуск проекта
- gradlew clean build
- docker compose up --build

# Пример работы
- Создаём задачу
  ```bash
  curl -X POST http://localhost:8080/tasks \
     -H "Content-Type: application/json" \
     -d '{"text": "Проверка сервиса: магаз пашол за хлебам", "language": "RU"}'
- Получаем ответ
  ```json
  {
    "id":"237033f9-61c1-4dbe-816e-61eb98637dd7"
  }
- Ждем пару секунд, в это время планировщик обращается к Яндекс.Спеллеру и сохраняет результат в базу данных.
- Получаем результат по id
  ```bash
  curl http://localhost:8080/tasks/237033f9-61c1-4dbe-816e-61eb98637dd7
- Итоговый ответ
  ```json
  {
    "status":"COMPLETED",
    "correctedText":"Проверка сервиса: магаз пошел за хлебом",
    "errorMessage":null
  }
