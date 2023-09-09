# API backend for Agendify with Spring Boot
## Required

- Install and configuring Docker
- Java 1.8 or later
- Gradle 7.5+
- Download this project
- And IDE for compiling Spring Boot project

## Running
The application can't start without database running

### Database MySQL with Docker
1. Open terminal
2. Running container:
    ``` docker
    docker pull image mysql 
    ```
    ``` docker
    docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=databasepass -v /var/lib/mysql:/var/lib/mysql -d mysql
    ```
    ``` docker
   docker exec -it mysql /bin/bash -l
    ```
    ``` docker
    mysql -u root -p
    ```
   ###### type password defined in docker run command
3. Configuring database
    ``` mysql
    CREATE DATABASE agendify;
    ```
   ``` mysql
   CREATE USER 'laboratorio' IDENTIFIED BY 'laboratorio';
    ```
   ``` mysql
   GRANT ALL PRIVILEGES ON agendify.* TO 'laboratorio';
    ```
   
### API Backend
- Open this application in the IDE and run it.
