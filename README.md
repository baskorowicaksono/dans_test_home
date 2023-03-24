# Simple Login & Job Lists API

* These APIs are developed for the initial test of Dans MultiPro recruitment process.
* Tech Stack: Java Springboot, MySQL, JPA, Spring-Security.

# Getting Started
The server can be accessed by running 
```bash
mvn spring-boot:run
```
or by manually starting the [DansApiApplication](src/main/java/dev/baskorowicaksono/dansapi/DansApiApplication.java). The server uses localhost on port 8080.
## The APIs
There are 6 total endpoints that have been developed, consists of 3 GET Endpoints and 3 POST Endpoints.

### DB Schema
The server uses MySQL to store the data of users. Here, i created three tables, such as table of _users_, _roles_, and _users_roles_.
* **Users** table is used to store the value of users data. The structure can be seen as below

```json
{
  "id"            : "long",
  "name"          : "string",
  "username"      : "string",
  "email"         : "string",
  "hashedPassword": "string"
}
```
* **Roles** table is used to store the value of roles available to the users. The structure can be seen as below.
```json
{
  "id"            : "long",
  "name"          : "string"
}
```
* **Users** and **Roles** table have the relationship of many-to-many, whereas many users can be associated to a single role, meanwhile a single user can also have multiple roles. Therefore there's a third table that connect them, which is **users_roles** table.

```json
{
  "user_id": "long",
  "role_id": "long"
}
```
### POST Endpoints
* **Add New Role Endpoint** (http://localhost:8080/api/auth/role) with request body consists of

```json
{
  "name": "string"
}
```

* **Register New User Endpoint** (http://localhost:8080/api/auth/register) with request body consists of

```json
{
  "name"    : "string",
  "username": "string",
  "email"   : "string",
  "password": "string",
  "role"    : "string"
}
```
Role must be defined before, using the Add New Role Endpoint. If the is not found within the DB, it will return an error.

* **Login User Endpoint** (http://localhost:8080/api/auth/login) with request body consists of

```json
{
  "usernameOrEmail" : "string",
  "password"        : "string"
}
```
User can try to log in using either their username or their email. The auth process is done by verifying the password inputed by the user is valid or not.

### GET Endpoints
* Get List of Jobs Available (http://localhost:8080/api/job_list)
* Get Details of a Job (http://localhost:8080/api/job_details/{job_id}) --> uses a path variable which is the id of the job entity.
* Download List of Jobs as CSV (http://localhost:8080/api/job_list/csv)
