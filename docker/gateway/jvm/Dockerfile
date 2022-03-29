FROM adoptopenjdk/openjdk11:slim as BUILD

RUN mkdir /app-src

COPY . /app-src
WORKDIR /app-src
RUN ./gradlew --no-daemon :gateway:shadowJar
#RUN ./gradlew --no-daemon minimizedJar



FROM adoptopenjdk/openjdk11:jre

COPY --from=BUILD /app-src/gateway/build/libs/gateway-0.1.0-all.jar /bin/runner/run.jar
#COPY --from=BUILD /app-src/gateway/build/libs/gateway-0.1.0-all.min.jar /bin/runner/run.jar

WORKDIR /bin/runner

EXPOSE 8080:8080

CMD ["java","-jar","run.jar"]