# Sem3-SYSEksm-backend

## Getting Started

This document explains how to use this code (build, test and deploy), locally with maven, and remotely with maven controlled by Github actions
 - [How to use](https://docs.google.com/document/d/1rymrRWF3VVR7ujo3k3sSGD_27q73meGeiMYtmUtYt6c/edit?usp=sharing)


## Endpoints

### Create a new FoocleBusiness with an admin account

Returns Ok `200` if everything went well.

```http
  POST /api/business
```
| Header parameter | Type     | Description                                    |
|:-----------------|:---------|:-----------------------------------------------|
| `Content-Type`   | `string` | **Required**. Has to be ```application/json``` |
| `Accept`         | `string` | **Required**. Has to be ```application/json``` |

| Parameter              | Type     | Description     |
|:-----------------------|:---------|:----------------|
| `cvr`                  | `String` | **Required**.   |
| `name`                 | `String` | **Required**.   |
| `description`          | `String` | **Required**.   |
| `businessEmail`        | `String` | **Required**.   |
| `businessPhoneNumber`  | `String` | **Required**.   |
| `address`              | `String` | **Required**.   |
| `city`                 | `String` | **Required**.   |
| `zipCode`              | `String` | **Required**.   |
| `country`              | `String` | **Required**.   |
| `firstname`            | `String` | **Required**.   |
| `lastname`             | `String` | **Required**.   |
| `businessAccountEmail` | `String` | **Required**.   |
| `password`             | `String` | **Required**.   |
| `phoneNumber`          | `String` | **Required**.   |

### Create FoocleSpot

To create FoocleSpot you need to be logged in with a FoocleBusiness admin Account.  

```http
  POST /api/spot
```
| Header parameter | Type     | Description                                    |
|:-----------------|:---------|:-----------------------------------------------|
| `Accept`         | `string` | **Required**. Has to be ```application/json``` |
| `x-access-token` | `string` | **Required**. Token received from log in       |

| Parameter           | Type     | Description   |
|:--------------------|:---------|:--------------|
| `businessAccountID` | `Number` | **Required**. |
| `address`           | `String` | **Required**. |
| `city`              | `String` | **Required**. |
| `zipCode`           | `String` | **Required**. |
| `country`           | `String` | **Required**. |

### Create a new FoocleScout account

Returns Ok `200` if everything went well.

``http
POST /api/scout
``

| Header parameter | Type     | Description                                    |
|:-----------------|:---------|:-----------------------------------------------|
| `Content-Type`   | `string` | **Required**. Has to be ```application/json``` |
| `Accept`         | `string` | **Required**. Has to be ```application/json``` |


| Parameter     | Type     | Description   |
|:--------------|:---------|:--------------|
| `firstname`   | `String` | **Required**. |
| `lastname`    | `String` | **Required**. |
| `Email`       | `String` | **Required**. |
| `password`    | `String` | **Required**. |
| `phoneNumber` | `String` | **Required**. |




### Get foocleSpots

```http
  GET /api/spot
```
| Header parameter | Type     | Description                                    |
|:-----------------|:---------|:-----------------------------------------------|
| `Accept`         | `string` | **Required**. Has to be ```application/json``` |
| `x-access-token` | `string` | **Required**. Token received from log in       |

#### Example Response

```json
[
  {
    "id": 1,
    "contact_id": 1,
    "email": "example@email.com",
    "phoneNumber": "12345678",
    "firstname": "example",
    "lastname": "exampleson",
    "cvr": "1",
    "businessName": "Example Business",
    "location": {
      "id": 5,
      "address": "42 ExampleStreet",
      "city": "ExampleCity",
      "zipCode": "9999",
      "country": "ExampleCountry",
      "longitude": "12.519298",
      "latitude": "55.754429"
    }
  }
]
```


### Get SpotMenus for foocleSpot 

```http
  GET /api/spot/{id}/menu
```
Replace url parameter `{id}` with an id for a FoocleSpot.

| Header parameter | Type     | Description                                    |
|:-----------------|:---------|:-----------------------------------------------|
| `Accept`         | `string` | **Required**. Has to be ```application/json``` |
| `x-access-token` | `string` | **Required**. Token received from log in       |

#### Example Response

```json
[
  {
    "id":  1,
    "description":  "The food is really tasty",
    "pictures":  "URL",
    "foodPreferences":  "vegan",
    "pickupTimeFrom": "2022-10-06T11:50:00",
    "pickupTimeTo":  "2022-10-06T12:50:00",
    "foocleSpotID": 1 
  },
  {
    "id":  2,
    "description":  "The food is super tasty",
    "pictures":  "URL",
    "foodPreferences":  "vegan",
    "pickupTimeFrom": "2022-10-07T11:50:00",
    "pickupTimeTo":  "2022-10-07T12:50:00",
    "foocleSpotID": 1
  }
]
```


### Create SpotMenu for foocleSpot

```http
  POST /api/spot/{id}/menu
```
Replace url parameter `{id}` with an id for a FoocleSpot.

| Header parameter | Type     | Description                                    |
|:-----------------|:---------|:-----------------------------------------------|
| `Accept`         | `string` | **Required**. Has to be ```application/json``` |
| `x-access-token` | `string` | **Required**. Token received from log in       |





#### Example Response

```json
{
  "id":  1,
  "description":  "The food is really tasty",
  "pictures":  "URL",
  "foodPreferences":  "vegan",
  "pickupTimeFrom": "2022-10-06T11:50:00",
  "pickupTimeTo":  "2022-10-06T12:50:00",
  "foocleSpotID": 1 
}
```

### Login FoocleScout account

```http
  POST /api/login/scout
```
| Header parameter | Type     | Description                                    |
|:-----------------|:---------|:-----------------------------------------------|
| `Content-Type`   | `string` | **Required**. Has to be ```application/json``` |
| `Accept`         | `string` | **Required**. Has to be ```application/json``` |

| Parameter  | Type     | Description    |
|:-----------| :------- |:---------------|
| `username` | `string` | **Required**   |
| `password` | `string` | **Required**   |

#### Example Response

```json
{
  "email": "example@email.com",
  "token": "JWT_TOKEN_HERE"
}
```

### Login FoocleBusiness account

```http
  POST /api/login/business
```
| Header parameter | Type     | Description                                    |
|:-----------------|:---------|:-----------------------------------------------|
| `Content-Type`   | `string` | **Required**. Has to be ```application/json``` |
| `Accept`         | `string` | **Required**. Has to be ```application/json``` |

| Parameter  | Type     | Description    |
|:-----------| :------- |:---------------|
| `username` | `string` | **Required**   |
| `password` | `string` | **Required**   |

#### Example Response

```json
{
  "email": "example@email.com",
  "token": "JWT_TOKEN_HERE"
}
```

### Validate JWT
Returns status code `200` `OK` if the token is valid.

```http
  HEAD /api/login/validate
```
| Header parameter | Type   | Description   |
|:-----------------|:-------|:--------------|
| x-access-token   | string | **Required**  |


## JPA snippets

### Setup in Intellij
- open view->too windows->persistence
- open the Database tab and create a new data source (remember to point to a database event though this is already written in the persistence unit. This is necessary in order to use the JPQL console)
- in the persistence window right click the pu or an entity and choose "console"
- write a jpql query in the console and execute it.

### Create model in workbench (cannot be done from Intellij - No model designer yet)
- file-> new model
- dobbelclick the mydb icon and change to relevant database (create one first if needed)
- click the Add Diagram icon
- click the table icon in the left side panel and click in the squared area to insert new table
- dobbelclick the new table and change name and add columns (remember to add a check mark in 'ai' for the primary key)
- do the process again to add a second table
- now in the panel choose the 'non identifying relationship' on to many
- click first on the child table (the one that should hold the foreign key) and then on the parent. A new relationship was now added.
- When done with designing - goto top menu: Database->forward engineer.
  - Check that all settings looks right and click continue
  - click continue again (no changes needed here)
  - Make sure the 'Export mysql table objects' is checked and Show filter to make sure that all your tables are in the 'objects to process' window -> click continue
  - Verify that the generated script looks right -> click continue
  - click close and open the database to see the new tables, that was just created.

### Create entities from database in Intellij (Persistence mappings)
- From inside the Persistence window:
- Right-click a persistence unit, point to Generate Persistence Mapping and select By Database Schema.
- Select the 
  - data source 
  - package
  - tick tables to include
  - open tables to see columns and add the ones with mapped type: Collection<SomeEntity> and SomeEntity
  - click OK.




