FROM openjdk:11

ADD entrypoint.sh entrypoint.sh
EXPOSE 8090
COPY target/*.jar /tmp/app.jar

RUN chmod 755 entrypoint.sh && chown 1000:root /tmp/app.jar && chown 1000:root entrypoint.sh  && apt remove -y unzip e2fsprogs --auto-remove --allow-remove-essential
USER 1000

ENTRYPOINT ["./entrypoint.sh"]
