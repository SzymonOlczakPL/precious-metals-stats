FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY preciousMetalsStats.jar preciousMetalsStats.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /preciousMetalsStats.jar"]