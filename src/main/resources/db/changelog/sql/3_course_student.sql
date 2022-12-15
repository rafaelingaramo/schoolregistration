CREATE TABLE student_course
(id int primary key not null auto_increment,
 course_id int not null,
 student_id int not null,
 foreign key (course_id) references course(id),
 foreign key (student_id) references student(id)
);