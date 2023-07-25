# the first stage of our build will use a maven 3.6.1 parent image
FROM maven:3.6.1-jdk-8-alpine AS BUILD

# copy the pom and src code to the container
RUN mkdir -p /app
COPY . /app
WORKDIR /app

# package our application code
RUN mvn clean package
# we will use openjdk 8 with alpine as it is a very small linux distro
FROM openjdk:8-jre-alpine3.9

# copy the packaged jar file into our docker image
#COPY Sales/target/*.jar /app.jar
COPY --from=BUILD /app/target/*.jar /app.jar

# environment variables
 ENV DB_URL=
 ENV KAFKA_PRODUCER_SERVER_URL=

# set the startup command to execute the jar
CMD ["java", "-jar", "/app.jar","--db.url=${DB_URL}","--kafka.producer.url=${KAFKA_PRODUCER_SERVER_URL}"]