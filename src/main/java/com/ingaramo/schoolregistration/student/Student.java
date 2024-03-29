package com.ingaramo.schoolregistration.student;

import com.ingaramo.schoolregistration.course.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "student")
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name="name", nullable = false, length = 100)
    private String name;

    @Column(name="status", nullable = false)
    private Boolean status;

    @Builder.Default
    @ManyToMany(mappedBy = "students")
    @Where(clause = "status = true")
    Set<Course> studentCourses = new HashSet<>();
}
