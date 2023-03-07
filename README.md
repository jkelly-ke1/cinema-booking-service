# Cinema Booking Service
This is simple Java+Spring RESTful service for cinema seat administration.
# Requirements
- Java 8 or higher
- PostgreSQL 14
- InteliJ IDEA prefered

## Project Dependecies
List of project dependencies:
- Spring Boot 2.7.1
- Spring Data JPA
- PostgreSQL
- Spring Security
- Hibernate Validator
- Model Mapper
- Lombok

## Main Functionality And Features
Functionality of this service depends on role that user have. 
There is `ADMIN` and `EMPLOYEE` roles that is available. 
### Available queries for Employee role
Following API endpoints can be acessed at `http://localhost:8080`
- __POST__ `/seat/add` - for creating one seat with custom parameters.
- __POST__ `/seat/new-session` - create new cinema session with custom parameters (Parameters should be defined on the client side)
- __GET__ `/seat/find-all`
- __GET__ `/seat/find-by-date` - find seats by movie session time.
- __GET__ `/seat/find-by-hall/{hallNubmer}` 
- __PATCH__ `/seat/{id}/reserve` - assign seat by specific person.
- __PATCH__ `/seat/{id}/release` - make specific seat free again.

### Admin role queries list
- __GET__ `/admin/employee/find/all` and `find/{id}` - to obtain users.
- __POST__ `/admin/employee/add`
- __DELETE__ `/admin/{id}/delete` - to delete employee by id.
- __PATCH__ `/admin/employee/{id}/edit` - to edit employee.

# Getting Started
All that has to do that to download or clone this project and open in IDE. Due Hibernate using, we can start project easily without creating table manually,
just create database, configure `application.propeties` file and start project. As we use BCrypt-based password encryption we need to insert it manually. 
Basic DB configuration:
```sql
INSERT INTO employee(username, password) VALUES ('admin', '*your bcrypted password*');
INSERT INTO role(id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role(id, name) VALUES (2, 'ROLE_EMPLOYEE');
INSERT INTO employee_roles(employee_id, roles_id) VALUES (admin_id, 1);
```
# Future Enhancements
- Front-end/standalone client implementation.
- Expanding administration features.
- Improve security.
