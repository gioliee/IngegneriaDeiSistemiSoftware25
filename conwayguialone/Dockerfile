#### Dockerfile in project conwaygui
FROM eclipse-temurin:17.0.5_8-jre-focal as builder
## ADD extracts the tar
ADD ./build/distributions/conwayguialone-1.0.tar /
WORKDIR /conwayguialone-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./
RUN mkdir logs

CMD ["bash", "conwayguialone"]


## 1) gradlew distTar

## docker rmi conwayguialone:2.0   	 
## docker  build -t conwayguialone:2.0 .   //La version 2.0 ha ip local
## docker run -it --rm --name conwayguialone -p7110:7110 --env "HOST_IP=192.168.1.132" conwayguialone:2.0   
## docker run -it --rm --name conwayguialone -p7110:7110 --env "HOST_IP=192.168.1.34" conwayguialone:2.0  

## Register the image on DockerHub
## docker tag conwayguialonelocal:2.0 natbodocker/conwayguialone:2.0
## type docker_password.txt | docker login --username natbodocker --password-stdin
## docker login --username natbodocker
## docker push natbodocker/conwayguialone:2.0

 