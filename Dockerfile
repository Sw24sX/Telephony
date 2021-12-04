#FROM tomcat:jdk11
#
#ADD ./Build/sample.war /usr/local/tomcat/webapps/
#
#EXPOSE 8080

#FROM maven:3.6.3 as maven
#LABEL COMPANY="ShuttleOps"
#LABEL MAINTAINER="support@shuttleops.com"
#LABEL APPLICATION="Sample Application"
#
#WORKDIR /usr/src/app
#COPY . /usr/src/app
#RUN mvn package
#
#FROM tomcat:8.5-jdk15-openjdk-oracle
#ARG TOMCAT_FILE_PATH=/docker
#
##Data & Config - Persistent Mount Point
#ENV APP_DATA_FOLDER=/var/lib/SampleApp
#ENV SAMPLE_APP_CONFIG=${APP_DATA_FOLDER}/config/
#
#ENV CATALINA_OPTS="-Xms1024m -Xmx4096m -XX:MetaspaceSize=512m -	XX:MaxMetaspaceSize=512m -Xss512k"
#
##Move over the War file from previous build step
#WORKDIR /usr/local/tomcat/webapps/
#COPY --from=maven /usr/src/app/target/SampleApp.war /usr/local/tomcat/webapps/api.war
#
#COPY ${TOMCAT_FILE_PATH}/* ${CATALINA_HOME}/conf/
#
#WORKDIR $APP_DATA_FOLDER
#
#EXPOSE 8080
#ENTRYPOINT ["catalina.sh", "run"]


FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /telephony
COPY target/telephony-0.0.1-SNAPSHOT.jar /telephony/telephony-app.jar
ENTRYPOINT ["java","-jar","telephony-app.jar"]