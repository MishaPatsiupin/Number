# Описание проекта-хранилища фактов об числах

Сервис использует RESTful архитектуру с использованием стека Java: Spring Boot и Gradle, а также PostgreSQL в качестве базы данных.

### Особенности и выполненые требования:
1. **Технологический Стек**:
   - Стек Java с Spring Boot и Gradle.
   - PostgreSQL для хранения данных.

2. **Функциональность**:
   - Полный набор операций CRUD для всех сущностей.
   - POST метод для работы со списком параметров (передаются в теле запроса) для bulk операций, работа организована используя Java 8 (Stream API, лямбда-выражения).
   - Кастомные запросы с использованием `@Query` и параметрами к вложенным сущностям.
   - Простейшее кэширование в виде in-memory Map (в виде отдельного бина).
   - Обработка ошибок для статусов 400 и 500, ... .
   - Реализован счетчик обращений к основному сервису (Spring AOP), счетчик реализован в виде отдельного синхронизированного класса.

3. **Инструменты и Качество**:
   - Интеграция Swagger для документирования API и CheckStyle.
   - Строгое соблюдение Java Code Convention при написании кода.
   - Покрытие модульными тестами более 80% бизнес-логики.
   - Докеризация приложения для удобного развертывания.
   - Интеграция с SonarCloud для поддержания 0 bugs & 0 code smells.

4. **Стандарты Кодирования**:
   - Соблюдение правил именования переменных, методов и классов.
   - Поддержание слоистой архитектуры для разделения логики, данных и представления.
   - Реализация слоя DAO для доступа к данным с целью обеспечения разделения ответственностей.
