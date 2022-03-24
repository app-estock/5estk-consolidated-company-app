FROM openjdk:11
WORKDIR /usr/src/app
ENV MYSQL_DATABASE = stockmarket
ENV MYSQL_USER = root
ENV MYSQL_PASSWORD = Maxak351#
ENV MYSQL_CI_URL = jdbc:mysql://localhost:3306/stockmarket
ENV SPRING_PROFILES_ACTIVE = Dev
COPY ./build/libs/Company-0.0.1-SNAPSHOT.jar Company-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar", "Company-0.0.1-SNAPSHOT.jar"]