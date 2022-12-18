echo 'Building schoolregistration project'
./gradlew clean build
echo 'Generating docker image'
docker build . -t schoolregistration:latest
echo 'Starting docker containers'
docker-compose up -d