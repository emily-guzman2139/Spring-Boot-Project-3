package com.student.com.handler;

import com.student.com.models.Student;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

@Component
public class StudentDecoder {

    private Student[] students;

    public StudentDecoder(ResourceLoader resourceLoader) {
//        this.students = get_students();
        this.students = getStudentsFromJson(resourceLoader.getResource("classpath:student_course_JSON.json"));
    }

    private Student[] get_students() {
        String url = "https://hccs-advancejava.s3.amazonaws.com/student_course.json";
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Student[] students = null;
        try {
            students = mapper.readValue(json, Student[].class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return students;

    }

    private Student[] getStudentsFromJson(Resource resource){
        try {
            File file = resource.getFile();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(file, Student[].class);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Student[] getStudents() {
        return students;
    }
}
