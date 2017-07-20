FROM maven:3.5-jdk-8-alpine

WORKDIR /api-campaign

ADD . /api-campaign

RUN mvn clean package

CMD ["java", "-jar", "target/campaign-v1-0.0.1-SNAPSHOT.jar"]
