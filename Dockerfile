# Используем базовый образ с поддержкой Java и установленным JRE
FROM adoptopenjdk:11-jre-hotspot

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR-файл собранного приложения в контейнер
COPY out/artifacts/demo1_jar/demo1.jar app.jar

# Определяем команду для запуска приложения при старте контейнера
CMD ["java", "-jar", "app.jar"]