FROM openjdk:11
WORKDIR /usr/src/app
ENV SPRING_PROFILES_ACTIVE = Dev
COPY ./build/libs/Company-0.0.1-SNAPSHOT.jar Company-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar", "Company-0.0.1-SNAPSHOT.jar"]