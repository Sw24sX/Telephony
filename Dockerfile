FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /telephony
COPY target/telephony-0.0.1-SNAPSHOT.jar /telephony/telephony-app.jar
ENTRYPOINT ["java","-jar","telephony-app.jar"]