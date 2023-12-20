FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/stoodo_api

COPY . /workspace/stoodo_api
RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build -x test
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*-SNAPSHOT.jar)

FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/stoodo_api/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /stoodo_api/lib
COPY --from=build ${DEPENDENCY}/META-INF /stoodo_api/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /stoodo_api
COPY --from=build /workspace/stoodo_api/src/main/resources/application.properties /stoodo_api
ENTRYPOINT ["java","-cp","stoodo_api:stoodo_api/lib/*","fr.stoodev.stoodo.StoodoApplication"]
