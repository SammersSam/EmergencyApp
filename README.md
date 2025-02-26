# EmergencyApp
  Simple project for handling emergency calls and dispatching emergency resources

Requirements:

- Java 17
- Maven
- Docker

Setup and Run:

    git clone https://github.com/SammersSam/emergency-app.git
    cd emergency-app


Check the application.properties file (in src/main/resources) and make sure the database credentials (URL, username, password) are correct.



-Start the main database container

    docker compose up -d postgres

-Start the test database container

    docker compose -f docker-compose.test.yml up -d db_test

-Build the application

    mvn clean package

-Remove any old or orphaned containers

    docker compose down --remove-orphans

-Rebuild and start the application

    docker compose up --build -d



    