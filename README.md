# Sprintdev

## Running

1. After cloning the repo ```cd sprintdev```
2. Run docker compose ```docker compose up```. This will start the Postgresql database and the keycloak server 

There are 8 users registered in the keycloak server with the same password as the username
- admin
- productowner
- scrummaster
- devlead
- dev1
- dev2
- qalead
- qa1

To modify any of keycloak settings or to add/remove users, connect to keycloak admin dashboard on ```localhost:28080``` with username admin and password admin

4. Start the server. It will run on ```localhost:8080```
5. Clone and run the frontend from [sprintdev-front](https://github.com/summrh4ze/sprintdev-front) repo