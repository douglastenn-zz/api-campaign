FROM maven:3.5-jdk-8-alpine

WORKDIR /api-campaign

ADD . /api-campaign

CMD ["mvn", "spring-boot:run"]
