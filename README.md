# Sem3-CA2-backend-template

### Getting Started

This document explains how to use this code (build, test and deploy), locally with maven, and remotely with maven controlled by Github actions
 - [How to use](https://docs.google.com/document/d/1rymrRWF3VVR7ujo3k3sSGD_27q73meGeiMYtmUtYt6c/edit?usp=sharing)


### Endpoints

#### Get anonymous greeting

```http
  GET /api/info
```

| Parameter  | Type     | Description              |
|:-----------| :------- | :----------------------- |

#### Get count of users

```http
  GET /api/info/all
```

| Parameter  | Type     | Description              |
|:-----------| :------- | :----------------------- |

#### Get greeting for a client with _user_ roles

```http
  GET /api/info/user
```

| Header parameter | Type   | Description   |
|:-----------------|:-------|:--------------|
| x-access-token   | string | **Required**  |

#### Get greeting for a client with _admin_ roles

```http
  GET /api/info/admin
```

| Header parameter | Type   | Description   |
|:-----------------|:-------|:--------------|
| x-access-token   | string | **Required**  |

#### Get random cat picture and the current weather report of CPH

```http
  GET /api/weatherNcat
```

| Parameter | Type   | Description   |
|:----------|:-------|:--------------|

#### Login

```http
  POST /api/login
```

| Parameter  | Type     | Description              |
|:-----------| :------- | :----------------------- |
| `username` | `string` | **Required** |
| `password` | `string` | **Required** |

#### Revalidate JWT

```http
  HEAD /api/login/validate
```
| Header parameter | Type   | Description   |
|:-----------------|:-------|:--------------|
| x-access-token   | string | **Required**  |


### JPA snippets

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

### create entities from database in Intellij (Persistence mappings)
- From inside the Persistence window:
- Right-click a persistence unit, point to Generate Persistence Mapping and select By Database Schema.
- Select the 
  - data source 
  - package
  - tick tables to include
  - open tables to see columns and add the ones with mapped type: Collection<SomeEntity> and SomeEntity
  - click OK.




