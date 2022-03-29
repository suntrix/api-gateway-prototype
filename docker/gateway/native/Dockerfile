FROM adoptopenjdk/openjdk11:slim as BUILD

RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends libcurl4-openssl-dev \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir /app-src

WORKDIR /app-src

RUN mkdir -p gateway
RUN mkdir -p targets/default
RUN mkdir -p targets/open-movie-database
RUN mkdir -p targets/the-movie-db
COPY build.gradle.kts gradle.properties settings.gradle.kts /app-src/
COPY gateway/build.gradle.kts /app-src/
COPY targets/default/build.gradle.kts targets/open-movie-database/build.gradle.kts targets/the-movie-db/build.gradle.kts /app-src/
COPY gradlew /app-src/
RUN mkdir -p gradle
COPY gradle /app-src/gradle/

RUN ./gradlew clean build --no-daemon > /dev/null 2>&1 || true

COPY ./ /app-src/

RUN ./gradlew --no-daemon :gateway:linkReleaseExecutableLinuxX64



FROM ubuntu:20.04

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'

RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends tzdata curl ca-certificates fontconfig locales \
    && echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen \
    && locale-gen en_US.UTF-8 \
    && rm -rf /var/lib/apt/lists/*

#RUN apt-get update \
#    && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends openssl \
#    && rm -rf /var/lib/apt/lists/*

COPY --from=BUILD /app-src/gateway/build/bin/linuxX64/releaseExecutable/gateway.kexe /bin/runner/gateway.kexe

WORKDIR /bin/runner

EXPOSE 8080:8080

CMD ["./gateway.kexe"]
