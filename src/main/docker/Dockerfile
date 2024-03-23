FROM amazoncorretto:21-alpine

LABEL maintainer=rebirth3005
ARG DEPENDENCY=dependency

COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-Dspring.profiles.active=production","-Xmx2048m","-cp","app:app/lib/*","com.rebirth.cyberplanta.consumer.ConsumerApp"]

EXPOSE 9090 8082
