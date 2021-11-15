# ReserveYourSpot // Comment: headline should be "Reserve your spot"

// Comment: root folder name should be reserve-your-spot
// Comment: documentation link, confluence link
// Comment: prerequisites like java 11, locally installed mySql (setup of it)...
// Comment: missing endpoints

This project should ensure that employees in a company can successfully reserve a place in the office. They can choose the office where they will work as well as the desk. 


##  Used By

* PRODYNA Innovative IT Consultancy
## Technology Stack
##### This project was built using InteliiJ IDEA and uses the following technologies:
* Java 11
* Spring Framework/Spring Boot
* REST CRUD operations
* Swagger UI with REST
* Spring DATA MySQL
* Test coverage
* Rest error handlers
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
  GET /stations/
```


#### Get WorkStation

```http
  GET /stations/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of station to fetch |


#### Delete WorkStation

```http
  DELETE /stations/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of station to delete |

#### Update WorkStation

```http
  PUT /stations/update
```
## Authors

- [@markoilic](https://bitbucket.prodyna.com/profile)
