package com.student.com.controllers;

import com.student.com.handler.StudentDecoder;
import com.student.com.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentDecoder studentDecoder;


    @GetMapping("/")
    public String index() {
        Student[] students = studentDecoder.getStudents();

        if(students == null){
            return "No students found";
        }

        for (Student student : students) {
            System.out.println(student);
        }

        return "Hello World";
    }

    // /search?name=John
    @GetMapping("/search")
    public String searchByName(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "course", required = false) String course){
        Student to_return = null;
        Student[] students = studentDecoder.getStudents();
        if (students == null){
            return "No students found";
        }
        int len = students.length;

        if (name != null){
            for (Student student : students) {
                if (student.getFirst_name().equals(name)) {
                    to_return = student;
                    break;
                }
            }
            return to_return.toString();
        }
        else if (course != null){
            Student[] students_with_course = new Student[len];
            for (int i = 0; i < len; i++) {
                String[] course_nos = students[i].courseNos();
                if (course_nos == null){
                    continue;
                }
                for (String courseNo : course_nos) {
                    if (courseNo.equals(course)) {
                        students_with_course[i] = students[i];

                    }
                }
            }


            StringBuilder json = new StringBuilder();
            for (int i = 0; i < len; i++) {
                if (students_with_course[i] != null){
                    json.append(students_with_course[i].toString());
                }
            }
            return json.toString();
        }
        return "Please enter a name or course";

    }
}
