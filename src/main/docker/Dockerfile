FROM openjdk:12.0.2

COPY /maven/*.jar /usr/src/greeting/
COPY /maven/bin/greeting-api /usr/bin/greeting-api

RUN chmod 0755 /usr/bin/greeting-api && \
    mkdir /etc/greeting/ && \
    useradd -M jack -u 42

WORKDIR /etc/greeting/

USER 42 
CMD ["greeting-api"]

VOLUME [ "/etc/greeting/" ]

EXPOSE 8080