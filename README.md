
Reserve your spot
=======


This project should ensure that employees in a company can successfully reserve a place in the office. They can choose the office where they will work as well as the desk. 


##  Used By

* PRODYNA Innovative IT Consultancy
##  Documentation Link

* https://secure.prodyna.de/confluence/display/PS/Spring+boot+microservice+task
## Technology Stack
##### This project was built using IntelliJ IDEA and uses the following technologies:
* Java 11
* Spring Framework/Spring Boot version 2.5.6
* REST CRUD operations
* Swagger UI with REST version 3.0.0
* Spring DATA MySQL
* Test coverage
* Rest error handlers
* Locally installed mySql 8.0 (setup: database name: prodyna, username: root, password: 4567)
* Docker & docker-compose
* Postman
* Installed IntelliJ IDEA plugin Diagrams.net Integration

##  Setup
###Setup for local development

1. Run MySQL docker container on docker desktop
2. Run application in IntelliJ IDEA
3. Test the API below using Postman or Swagger UI
4. Link to open Swagger UI- http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
###Setup for running application on other device

1. Install MySQL Workbench 8.0 and create new connection (database=prodyna, username=root, password=4567)
2. Install DockerDesktop
3. Create profile on DockerHub
4. Install Postman for testing REST API
5. Run next Docker command on your local machine from the correct location where docker-compose.yml file is:
  ```http
 Docker-compose up
```
6. Wait the application to start
7. Run Postman and import collection of REST API
8. Test the API below using Postman or Swagger UI
9. Link to open Swagger UI- http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
10. Install plugin Diagrams.net Integration on IntelliJ IDEA (link for help: https://www.jetbrains.com/help/idea/managing-plugins.html)

## API Reference
### Administration scenario

### OfficeSpace

#### Find all office-spaces

```http
  GET /api/office-spaces
```

#### Find office-space

```http
  GET /api/office-spaces/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of office-space to find |

#### Find office-rooms by office-space id

```http
  GET /api/office-spaces/{officeSpaceId}/office-rooms
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of office-space to find office-rooms|

#### Save office-space

```http
  POST /api/office-spaces
```
#### Delete office-space

```http
  DELETE /api/office-spaces/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of office-space to delete |

#### Update office-space

```http
  PUT /api/office-spaces/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of office-space to update |

### OfficeRoom

#### Find all office-rooms

```http
  GET /api/office-rooms
```

#### Find office-room

```http
  GET /api/office-rooms/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of office-room to find |

#### Find work-stations by office-room id

```http
  GET /api/office-rooms/{officeRoomId}/work-stations
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of office-room to find work-stations|

#### Save office-room

```http
  POST /api/office-rooms
```

#### Delete office-room

```http
  DELETE /api/office-rooms/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of office-room to delete |

#### Update office-room

```http
  PUT /api/office-rooms/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of office-room to update |

### WorkStation

#### Save work-stations

```http
  POST /api/work-stations
```
#### Delete work-stations

```http
  DELETE /api/work-stations/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of work-station to delete |

#### Update work-stations

```http
  PUT /api/work-stations/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of work-station to update |

### Reservation scenario

### OfficeSpace

#### Find all office-spaces

```http
  GET /api/office-spaces
```

#### Find office-space by id and date range

```http
  GET /api/office-spaces/office-view?officeSpaceID=''&dateFrom=''&dateTo=''
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of office-space to find |
| `dateFrom`      | `LocalDate` | **Required**: date from needed |
| `dateTo`      | `LocalDate` | **Required**: date to needed |

### Reservation

#### Find reservations by user id and date range

```http
  GET /api/reservations?userId=''&dateFrom=''&dateTo=''
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `int` | **Required**: Id of user to find reservations for|
| `dateFrom`      | `LocalDate` | **Required**: date from needed |
| `dateTo`      | `LocalDate` | **Required**: date to needed |

#### Save reservations by date range

```http
  POST /api/reservations/save?userId=''&workStationId=''&dateFrom=''&dateTo=''
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `int` | **Required**: Id of user to save |
| `workStationId`      | `int` | **Required**: Id of work-station to save |
| `dateFrom`      | `LocalDate` | **Required**: date from needed |
| `dateTo`      | `LocalDate` | **Required**: date to needed |

#### Delete/Cancel reservation

```http
  DELETE /api/reservations/cancel?userId=''&workStationId=''&date=''
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `int` | **Required**: Id of user to cancel reservation for |
| `workStationId`      | `int` | **Required**: Id of work-station to cancel reservation for|
| `date`      | `LocalDate` | **Required**: date to cancel reservation |

#### Delete all reservations

```http
DELETE /api/reservations/deleteAll?reservationsIds=''
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `reservationsIds`      | `List<Integer>` | **Required**: Ids of reservations to delete |

#### Delete reservation

```http
  DELETE /api/reservations/${id}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**: Id of reservation to delete |
## Authors

- [@markoilic](https://bitbucket.prodyna.com/profile)
