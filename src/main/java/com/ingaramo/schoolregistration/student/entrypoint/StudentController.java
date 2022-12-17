package com.ingaramo.schoolregistration.student.entrypoint;

import com.ingaramo.schoolregistration.student.StudentDto;
import com.ingaramo.schoolregistration.student.usecase.DeleteStudent;
import com.ingaramo.schoolregistration.student.usecase.EditStudent;
import com.ingaramo.schoolregistration.student.usecase.FindAllStudents;
import com.ingaramo.schoolregistration.student.usecase.GetStudent;
import com.ingaramo.schoolregistration.student.usecase.SaveStudent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final FindAllStudents findAllStudents;
    private final SaveStudent saveStudent;
    private final EditStudent editStudent;
    private final GetStudent getStudent;
    private final DeleteStudent deleteStudent;

    @GetMapping
    public Set<StudentDto> getStudentList(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        return findAllStudents.execute(page, size);
    }

    @PostMapping
    public ResponseEntity<StudentDto> saveStudent(@RequestBody StudentDto StudentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveStudent.execute(StudentDto));
    }

    @PutMapping
    public ResponseEntity<StudentDto> editStudent(@RequestBody StudentDto StudentDto) {
        return ResponseEntity.ok(editStudent.execute(StudentDto));
    }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable("id") Integer studentId) {
        return getStudent.execute(studentId);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Integer studentId) {
        deleteStudent.execute(studentId);
    }
}
