<<<<<<< HEAD
# Reserve your spot
=======
# ReserveYourSpot // Comment: headline should be "Reserve your spot"

// Comment: root folder name should be reserve-your-spot
// Comment: documentation link, confluence link
// Comment: prerequisites like java 11, locally installed mySql (setup of it)...
// Comment: missing endpoints
>>>>>>> c640802f12ff2a17c16602c949b5315361a0c59d

This project should ensure that employees in a company can successfully reserve a place in the office. They can choose the office where they will work as well as the desk. 


##  Used By

* PRODYNA Innovative IT Consultancy
##  Documentation Link

* https://secure.prodyna.de/confluence/display/PS/Spring+boot+microservice+task
## Technology Stack
##### This project was built using InteliiJ IDEA and uses the following technologies:
* Java 11
* Spring Framework/Spring Boot
* REST CRUD operations
* Swagger UI with REST
* Spring DATA MySQL
* Test coverage
* Rest error handlers
* Locally installed mySql (setup of it: database name: prodyna, username: root, password: 1234)

## API Reference

### Users
#### Get all users

```http
  GET /users/
```

#### Get user

```http
  GET /users/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of user to fetch |

#### Add user

```http
  POST/users/
```

#### Delete user

```http
  DELETE /users/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of user to delete |

#### Update user

```http
  PUT /users/update
```

### OfficeRooms
#### Get all rooms

```http
  GET /rooms/
```


#### Get OfficeRoom

```http
  GET /rooms/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of room to fetch |

#### Add rooms

```http
  POST /rooms/
```

#### Delete OfficeRoom

```http
  DELETE /rooms/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of room to delete |

#### Update OfficeRoom

```http
  PUT /rooms/update
```
### WorkStations
#### Get all stations

```http
  GET /workStations/
```


#### Get WorkStation

```http
  GET /workStations/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of station to fetch |

#### Add WorkStation

```http
  POST /workStations/
```
#### Delete WorkStation

```http
  DELETE /workStations/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of station to delete |

#### Update WorkStation

```http
  PUT /workStations/update
```
### Reservations
#### Get all reservations

```http
  GET /reservations/
```


#### Get Reservation

```http
  GET /reservations/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of reservation to fetch |


#### Delete Reservation

```http
  DELETE /reservations/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of reservation to delete |

#### Add Reservation

```http
  POST /reservations/
```

## Authors

- [@markoilic](https://bitbucket.prodyna.com/profile)
