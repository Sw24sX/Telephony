FROM adoptopenjdk:11-jre-hotspot

ARG GOOGLE_APPLICATION_CREDENTIALS="peerless.json"
COPY ${GOOGLE_APPLICATION_CREDENTIALS} "/usr/peerless.json"
ENV GOOGLE_APPLICATION_CREDENTIALS="/usr/peerless.json"

RUN apt update
RUN apt install -y sox
ARG SOX_PATH="/usr/bin/sox"
ENV SOX_PATH=${SOX_PATH}

ARG JAR_FILE="target/TextToSpeechService-0.0.1-SNAPSHOT.jar"
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
