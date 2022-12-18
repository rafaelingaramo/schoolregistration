# README

School registration system

Design and implement simple school registration system
- Assuming you already have a list of students
- Assuming you already have a list of courses
- A student can register to multiple courses
- A course can have multiple students enrolled in it.
- A course has 50 students maximum
- A student can register to 5 course maximum

Provide the following REST API:
- Create students CRUD 
- Create courses CRUD 
- Create API for students to register to courses
- Create abilities for user to view all relationships between students and courses
+ Filter all students with a specific course
+ Filter all courses for a specific student
+ Filter all courses without any students
+ Filter all students without any courses


## Project Requirements 
* Java 11

## Project Details
* Spring Boot with Spring Data JPA on MYSQL
* This project was build using the UseCase pattern
* The restful pattern was followed in this project
* Unit tests were written with JUnit 

### How to Run 

Execute ./startup.sh, it's going to compile, create docker image and start the process

### How to execute unit tests

Execute ./gradlew tests

### Postman collection 

In the root of this project there's a Postman collection, check `schoolregistration.postman_collection.json`

## Endpoints and Payloads 

Following the Restful pattern

* StudentController - CRUD operations
  * /student
    * POST - create new student
    * GET - get a list of all students paged
    * PUT - edit existing student
    * DELETE - soft delete student
    * GET /{id} - get specific student by id
* CourseController - CRUD operations
  * /course
      * POST - create new course
      * GET - get a list of all courses paged
      * PUT - edit existing course
      * DELETE - soft delete course
      * GET /{id} - get specific course by id
* CourseEnrollController - Enroll to a specific course 
  * /course/{courseId}/enroll/student/{studentId}
    * POST - enroll student in a course
* DashboardController - Reports
  * /dashboard
    * GET /students-per-course/{course-id} - List students per course
    * GET /courses-per-student/{student-id} - List courses per student
    * GET /empty-student-courses - List courses without students
    * GET /empty-course-students - List students without courses

Check postman collection for more info and payloads.
