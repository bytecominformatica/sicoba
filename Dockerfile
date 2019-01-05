FROM node:10-alpine as build-front
WORKDIR /opt/sicoba
RUN apk update && apk upgrade && \
    apk add --no-cache bash git openssh
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

FROM openjdk:11-oracle
ARG GRADLE_OPTS="-Dorg.gradle.daemon=false"
MAINTAINER clairton.c.l@gmail.com
VOLUME /tmp
WORKDIR /opt/sicoba
COPY --from=build-front /opt/sicoba .
RUN ./gradlew clean build -x test && \
    ls -lah && \
    rm -Rf !(app.jar)
#    mv build/libs/sicoba-2.0.0.jar app.jar && \
#    rm -Rf .bowerrc .gradle bower.json build build.gradle gradle gradlew gulpfile.js \
#    node_modules package-lock.json package.json settings.gradle src

ENV PROFILE="staging"
ENV DATABASE_HOST="localhost"
ENV DATABASE_PORT=5432
ENV DATABASE_NAME="bytecom"
ENV DATABASE_USER="bytecom"
ENV DATABASE_PASS="bytecom"
ENV APP_TOKEN="TOKEN_EXAMPLE"
ENV EMAIL_SAC="sac@bytecominformatica.com.br"
ENV EMAIL_ADMIN="admin@bytecominformatica.com.br"
ENV EMAIL_SUPORTE="suporte@bytecominformatica.com.br"
ENV DOMAIN="localhost:8080"
ENV SENDGRID_API_KEY="SENDGRID_API_KEY_SECRET"
ENV EMAIL_HOST="smtp.zoho.com"
ENV EMAIL_USERNAME="sicoba@bytecominformatica.com.br"
ENV EMAIL_PASSWORD="secretPassword"
ENV EMAIL_PORT=465

RUN ls -lah
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
EXPOSE 8080
