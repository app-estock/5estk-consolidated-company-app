FROM openjdk:11
WORKDIR /usr/src/app
ENV MYSQL_DATABASE = stockmarket
ENV MYSQL_USER = root
ENV MYSQL_PASSWORD = root
ENV MYSQL_CI_URL = jdbc:mysql://localhost:3306/stockmarket
ENV SPRING_PROFILES_ACTIVE = Dev
COPY ./build/libs/SaveCompany-0.0.1-SNAPSHOT.jar SaveCompany-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "SaveCompany-0.0.1-SNAPSHOT.jar"]