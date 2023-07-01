FROM adoptopenjdk/openjdk11

WORKDIR /app
COPY ./target/demo*.jar /app
EXPOSE 8080

CMD java -jar demo*.jar

