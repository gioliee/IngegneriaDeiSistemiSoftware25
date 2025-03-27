#### Dockerfile in project conway25JavaMqtt
FROM eclipse-temurin:17.0.5_8-jre-focal as builder
## ADD extracts the tar
ADD ./build/distributions/conway25JavaMqtt-1.0.tar /
WORKDIR /conway25JavaMqtt-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./
RUN mkdir logs

CMD ["bash", "conway25JavaMqtt"]


## 1) gradlew distTar

## docker rmi conway25javamqtt:2.0   	 
## docker  build -t conway25javamqtt:2.0 .   //La version 2.0 ha ip local
## docker run -it --rm --name conway25javamqtt -p7110:7110 --env "HOST_IP=192.168.1.132" conway25javamqtt:2.0   
## docker run -it --rm --name conway25javamqtt -p7110:7110 --env "HOST_IP=192.168.1.34" conway25javamqtt:2.0  

## Register the image on DockerHub
## docker tag conway25javamqttlocal:2.0 natbodocker/conway25javamqtt:2.0
## type docker_password.txt | docker login --username natbodocker --password-stdin
## docker login --username natbodocker
## docker push natbodocker/conway25javamqtt:2.0

 