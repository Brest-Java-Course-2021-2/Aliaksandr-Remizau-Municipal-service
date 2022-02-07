#  Available REST endpoints


### [Version](#version)
### [Clients](#clients)
  * [Get all clients DTO](#get-all-clients-dto)
  * [Get all clients](#get-all-clients)
  * [Get client](#get-client)
  * [Create a new client](#create-a-new-client)
  * [Update a client](#update-a-client)
  * [Delete a client](#delete-a-client)
### [Repairs](#repairs)
  * [Get all repairs](#get-all-repairs)
  * [Get  repair](#get-repair)
  * [Create a new repair](#create-a-new-repair)
  * [Update a repair](#update-a-repair)
  * [Delete a repair](#delete-a-repair)
  * [Get filtered repairs](#get-filtered-repairs)

### Version

Get information for the API version. 
Request sample cURL:

```bash
curl --location --request GET 'http://localhost:8088/version'
```

<details>
  <summary> 200 OK </summary>

Response Example

```bash
0.0.1
```

</details>

### Clients

#### Get all clients DTO

Get information for all clients with their name and number of repairs.


```bash
curl --location --request GET 'http://localhost:8088/clients_dto'
```

<details>
  <summary>200 OK</summary>

Response Example

```bash
[
    {
        "clientId": 1,
        "clientName": "ALEKSANDROVICH ALEKSEY IOSIFOVICH",
        "numberOfRepairs": 2
    },
    {
        "clientId": 3,
        "clientName": "BORODACH MICHAIL IVANOVICH",
        "numberOfRepairs": 0
    },
    {
        "clientId": 2,
        "clientName": "ORLOV PETR IVANOVICH",
        "numberOfRepairs": 3
    }
]
```

</details>

#### Get all clients

Get information for all clients .
```bash
curl --location --request GET 'http://localhost:8088/clients'
```

<details>
  <summary>200 OK</summary>

Response Example

```bash
[
    {
        "clientId": 1,
        "clientName": "ALEKSANDROVICH ALEKSEY IOSIFOVICH"
    },
    {
        "clientId": 3,
        "clientName": "BORODACH MICHAIL IVANOVICH"
    },
    {
        "clientId": 2,
        "clientName": "ORLOV PETR IVANOVICH"
    }
]
```

</details>

#### Get client

Get information about specific identified by its unique ID.

```bash
curl --location --request GET 'http://localhost:8088/clients/1'
```

<details>
  <summary>200 OK</summary>

Response Example

```bash
{
    "clientId": 1,
    "clientName": "ALEKSANDROVICH ALEKSEY IOSIFOVICH"
}
```

</details>

<details>
  <summary>404 Not Found</summary>

Trying to get a non-existent client

```bash
{
    "message": "client.not_found",
    "details": [
        "Client not found for id: 10"
    ]
}
```

</details>

#### Create a new client


```bash
curl --location --request POST 'http://localhost:8088/clients' \
--header 'Content-Type: application/json' \
--data-raw '{
  "clientName": "New Client"
  
}'
```

<details>
  <summary>200 OK</summary>

Client have been created. Returns the ID of the new client.

```bash
4
```
</details> 
<details>
  <summary>400 Bad Request</summary>

```bash
{
    "message": "validation_error",
    "details": [
        "Please provide client name!"
    ]
}
```
</details>

<details>
  <summary>422 Unprocessable Entity</summary>

An attempt to create a non-unique client

```bash
{
    "message": "validation_error",
    "details": [
        "Client with the same name already exists in DB."
    ]
}
```
</details>



#### Update a client


```bash
curl --location --request PUT 'http://localhost:8088/clients' \
--header 'Content-Type: application/json' \
--data-raw '{
  "clientId": 1,
  "clientName": "NewName"
}
'
```

<details>
  <summary>200 OK</summary>

Client have been updated. Returns the number of cliens affected.

```bash
1
```

</details>

<details>
  <summary>404 Not Found</summary>

Trying to update a non-existent Client

```bash
{
    "message": "client.not_found",
    "details": [
        "Client not found for id: 10"
    ]
}
```

</details>

#### Delete a client


```bash
curl --location --request DELETE 'http://localhost:8088/clients/3'
```

<details>
  <summary>200 OK</summary>

Client have been removed. Returns the number of clients affected.

```bash
1
```

</details>

<details>
  <summary>404 Not found</summary>

Trying to delete a non-existent client

```bash
{
    "message": "client.not_found",
    "details": [
        "Client not found for id: 10"
    ]
}
```

</details>

<details>
  <summary>422 Unprocessable Entity</summary>



```bash
{
    "message": "data_base_error",
    "details": [
        "JdbcSQLIntegrityConstraintViolationException: Referential integrity constraint violation: \"REPAIR_CLIENT_FK: PUBLIC.REPAIR FOREIGN KEY(CLIENT_ID) REFERENCES PUBLIC.CLIENT(CLIENT_ID) (1)\"; SQL statement:\ndelete from client where client_id = ? [23503-200]"
    ]
}
```

</details>

### Repairs

#### Get all repairs

Get information for all repairs .
```bash
curl --location --request GET 'http://localhost:8088/repairs'
```

<details>
  <summary>200 OK</summary>


```bash
[
    {
        "repairId": 1,
        "repairType": "ELECTRIC",
        "address": "MOSKOVSKAYA 263-13",
        "difficultyLevel": "EASY",
        "preferenceDate": "2021-12-20",
        "clientId": 1
    },
    {
        "repairId": 2,
        "repairType": "FINISHING",
        "address": "MOSKOVSKAYA 263-13",
        "difficultyLevel": "HARD",
        "preferenceDate": "2021-12-25",
        "clientId": 1
    },
    {
        "repairId": 3,
        "repairType": "FINISHING",
        "address": "MOSKOVSKAYA 250-10",
        "difficultyLevel": "HARD",
        "preferenceDate": "2021-12-29",
        "clientId": 2
    },
    {
        "repairId": 4,
        "repairType": "PLUMBER",
        "address": "MOSKOVSKAYA 250-10",
        "difficultyLevel": "MEDIUM",
        "preferenceDate": "2021-12-30",
        "clientId": 2
    },
    {
        "repairId": 5,
        "repairType": "ANOTHER",
        "address": "MOSKOVSKAYA 100-15",
        "difficultyLevel": "EASY",
        "preferenceDate": "2021-12-30",
        "clientId": 2
    }
]
```

</details>

#### Get repair

Get information for a single repair identified by its unique ID.

```bash
curl --location --request GET 'http://localhost:8088/repairs/1'
```


<details>
  <summary>200 OK</summary>

```bash
{
    "repairId": 1,
    "repairType": "ELECTRIC",
    "address": "MOSKOVSKAYA 263-13",
    "difficultyLevel": "EASY",
    "preferenceDate": "2021-12-20",
    "clientId": 1
}
```

</details>

<details>
  <summary>404 Not Found</summary>

Trying to get a non-existent repair

```bash
{
    "message": "repair.not_found",
    "details": [
        "Repair not found for id: 8"
    ]
}
```

</details>

#### Create a new repair


```bash
curl --location --request POST 'http://localhost:8088/repairs' \
--header 'Content-Type: application/json' \
--data-raw '{
  "repairType": "ANOTHER",
  "address": "ORLOVSKAYA 51-74",
  "difficultyLevel": "HARD",
  "preferenceDate": "2021-12-12",
  "clientId": "1"
}'
```

<details>
  <summary>200 OK</summary>

Repair have been created. Returns the ID of the new repair.

```bash
6
```

</details>

<details>
  <summary> 422 Unprocessable Entity</summary>

An attempt to create repair with invalid fields.

```bash
{
    "message": "data_base_error",
    "details": [
        "JdbcSQLIntegrityConstraintViolationException: NULL not allowed for column \"PREFERENCE_DATE\"; SQL statement:\ninsert into repair(repair_type, address, difficulty_level, preference_date, client_id)  values(?, ?, ?, ?, ?) [23502-200]"
    ]
}
```

</details>

#### Update a repair


```bash
curl --location --request PUT 'http://localhost:8088/repairs' \
--header 'Content-Type: application/json' \
--data-raw '{ 
  "repairId": 1,
  "repairType": "ANOTHER",
  "address": "ORLOVSKAYA 51-74",
  "difficultyLevel": "HARD",
  "preferenceDate": "2021-12-12",
  "clientId": "1"
}'
```

<details>
  <summary>200 OK</summary>

Repair have been updated. Returns the number of repairs affected.

```bash
1
```

</details>

<details>
  <summary>422 Unprocessable Entity</summary>

Trying to update repair with invalid fields

```bash
{
    "message": "data_base_error",
    "details": [
        "JdbcSQLIntegrityConstraintViolationException: NULL not allowed for column \"PREFERENCE_DATE\"; SQL statement:\nupdate repair set repair_type = ?, address = ?, difficulty_level = ?, preference_date = ?, client_id = ?  where repair_id = ? [23502-200]"
    ]
}
```

</details>

<details>
  <summary>404 Not Found</summary>

Trying to update a non-existent repair

```bash
{
    "message": "repair.not_found",
    "details": [
        "Repair not found for id: 9"
    ]
}
```

</details>


#### Delete a repair


```bash
curl --location --request DELETE 'http://localhost:8088/repairs/1'
```

<details>
  <summary>200 OK</summary>

Repair have been removed. Returns the number of repairs affected.

```bash
1
```

</details>

<details>
  <summary>404 Not Found</summary>

Trying to delete a non-existent repair

```bash
{
    "message": "repair.not_found",
    "details": [
        "Repair not found for id: 7"
    ]
}
```

</details>

#### Get filtered repairs 

Get information for repairs depend on their preference date .

```bash
curl --location --request GET 'http://localhost:8088/repairs/filter?startLimitDate=2021-12-25&endLimitDate=2021-12-30'
```

<details>
  <summary>200 OK</summary>



```bash
[
    {
        "repairId": 2,
        "repairType": "FINISHING",
        "address": "MOSKOVSKAYA 263-13",
        "difficultyLevel": "HARD",
        "preferenceDate": "2021-12-25",
        "clientId": 1
    },
    {
        "repairId": 3,
        "repairType": "FINISHING",
        "address": "MOSKOVSKAYA 250-10",
        "difficultyLevel": "HARD",
        "preferenceDate": "2021-12-29",
        "clientId": 2
    },
    {
        "repairId": 4,
        "repairType": "PLUMBER",
        "address": "MOSKOVSKAYA 250-10",
        "difficultyLevel": "MEDIUM",
        "preferenceDate": "2021-12-30",
        "clientId": 2
    },
    {
        "repairId": 5,
        "repairType": "ANOTHER",
        "address": "MOSKOVSKAYA 100-15",
        "difficultyLevel": "EASY",
        "preferenceDate": "2021-12-30",
        "clientId": 2
    }
]
```

</details>
