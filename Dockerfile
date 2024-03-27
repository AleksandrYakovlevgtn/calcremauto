FROM amazoncorretto:8-alpine-jdk
# Устанавливаем рабочую директорию
WORKDIR /usr/src/app

# Копируем JAR файл вашего приложения в рабочую директорию внутри образа
COPY target/calcremauto-0.0.1-SNAPSHOT.jar /usr/src/app/app.jar

# Запускаем приложение при старте контейнера
CMD ["java", "-jar", "app.jar"]