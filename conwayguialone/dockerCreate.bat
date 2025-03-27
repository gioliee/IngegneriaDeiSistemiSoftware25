docker-compose -f conwaygui.yaml -p conwaygui down
docker rmi conwayguialone:2.0
gradlew distTar --stacktrace
docker  build -t conwayguialone:2.0 .
docker-compose -f conwaygui.yaml -p conwaygui up