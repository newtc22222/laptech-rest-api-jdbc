## Ecommerce Website for Laptop

This project is a `Spring boot` web service with `REST API`.

- Version (Spring boot): 2.7.8
- Build: `mvn clean install`
- Run project: `mvn spring-boot:run`

---

## INFORMATION

### Connection

- DBMS: MySQL
- Connection Framework: Spring boot JDBC (`JDBCTemplate`)
- [File database](src/main/resources/scripts)

### MAIN folder's structure

#### java

- **common**: config code for all system
    - **config**: configuration of `Swagger` and `Spring security`
    - **dto**: DTO stand for `Sort` and `Paging`
    - **enums**: The collection of enum which use for system
    - **exception**: The custom Exception handler to catch and response error
    - **filter**: Log URL information in server
    - **runner**: *Run to make sample user* in database (if database doesn't have one)
- **util**: The collection of class which support to convert data type (or parse)
- **model**: include blueprints of object
- **dao**: contains interface and code for query + update in database
    - **impl**: definition of interface
- **mapper**: `RowMapper` support for `JdbcTemplate` query
- **dto**: model with optional information
    - **filter**: The collection of class which includes filter field
    - **request**: The collection of class which use to get input data from web app
    - **response**: The collection of class which handle data to answer
- **service**: contain interface and code to receive params, handle and give back data
    - **impl**: definition of interface
    - **export**: build for make document (pdf, excel, csv, ...)
    - **payment**: build with extra extension: momo, paypal, ...
    - **statistic**: build to calculate data
    - **upload**: build to receive image and store in server
- **jwt**: extra extension support for `Spring Security`
    - **config**: custom error handler - `Access Denied` and `AuthenticationEntryPoint`
    - **dto**: model used for authentication of system
    - **filter**: get information in request, inspect and accept (or reject)
    - **service**: maintain and handle user in system
    - **util**: contain support class of create jwt token (and refresh token)
- **controller**: __main module__ include `@SpringbootApplication` (subclass was built base on service).
  This package contains **annotation** of `Springfox Swagger`, `Spring Boot` and `Spring Security` pre-author

#### resources

- **scripts**: file sql of database (structure and sample data)
- `application.yaml`: config file of server and application
- `banner.txt`: use instead of default banner of spring boot
- `query.properties`: includes the collection of `MySQL store procedure`
- `token.properties`: includes `SECRET_KEY` of JWT and `access_token_time`

---

#### FLOW OF DATA

``` 
-- INPUT --
controller --(params|dto)--> service --(filter|model)--> dao --(query)--> database [MySQL]

-- OUTPUT --
Input has issue [DTO: Invalid params (400); JWT: Authentication (401), Authorization (403)]
service|dto --(Error response)--> controller

Input has no issue [SERVICE: Data response (200, 201), Error Response (501)]
database --(result)--> dao --(data|[Error] - empty value)--> service --(Data|Error response)--> controller
```

---

### Documents and Files

- [Diagrams](https://drive.google.com/file/d/1qZTzLWsiRYUONJhafOu_H9mPP1xIWZpL/view)
- [Document](https://drive.google.com/drive/u/1/folders/1QeuA0jng2ANcQ92gs_uupGr8-Ka_bMli)
