#ARG RUN_ARGS="--spring.profiles.active=development \
#    --server.session.timeout=600 \
#    --spring.datasource.url=jdbc:postgresql://10.77.5.81:5432/bytecom?user=bytecom&password=bytecom"
FROM node:10-alpine as build-front
WORKDIR /opt/sicoba
#VOLUME /tmp
RUN echo "$RUN_ARGS"
#RUN apk update && apk upgrade && \
#    apk add --no-cache bash git openssh
ADD gradle gradle
ADD src src
ADD .bowerrc .
ADD bower.json .
ADD build.gradle .
ADD gradlew .
ADD gulpfile.js .
ADD package.json .
ADD package-lock.json .
ADD settings.gradle .
RUN npm install && npm run build

FROM openjdk:8-jdk-alpine
MAINTAINER clairton.c.l@gmail.com
VOLUME /tmp
WORKDIR /opt/sicoba
COPY --from=build-front /opt/sicoba .
RUN ./gradlew clean build -x test
COPY build/libs/sicoba-2.0.0.jar app.jar
RUN rm -Rf .bowerrc .gradle bower.json build build.gradle gradle gradlew gulpfile.js \
    node_modules package-lock.json package.json settings.gradle src

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
EXPOSE 8080
