FROM eclipse-temurin:17

LABEL maintainer=rebirth3005
ARG DEPENDENCY=dependency

#RUN apt-get update && \
#    apt-get install -y wget gnupg software-properties-common && \
#    echo "deb https://assets.logdna.com stable main" | tee /etc/apt/sources.list.d/logdna.list && \
#   wget -qO - https://assets.logdna.com/logdna.gpg | apt-key add - &&  \
#   apt-get update && apt-get install -y logdna-agent && \
#    apt-get install -y supervisor && \
#    apt-get clean


COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
#COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

#RUN mkdir -p /var/log/mykafka
#RUN mkdir -p /var/log/supervisor

ENTRYPOINT ["java","-Dspring.profiles.active=production","-Xmx2048m","-cp","app:app/lib/*","com.rebirth.cyberplanta.consumer.ConsumerApp"]

EXPOSE 9090 8082
