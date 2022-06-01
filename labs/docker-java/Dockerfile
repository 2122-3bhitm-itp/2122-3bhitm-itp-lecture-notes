FROM eclipse-temurin:17
RUN mkdir /opt/app
COPY docker/data/monatsmitteltemperaturen.csv /opt/app/
COPY target/demo-1.0-SNAPSHOT-runner.jar /opt/app
CMD ["java", "-jar", "/opt/app/demo-1.0-SNAPSHOT-runner.jar"]