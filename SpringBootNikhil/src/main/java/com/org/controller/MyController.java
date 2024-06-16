package com.org.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.Services.StudentService;
import com.org.entities.Student;

@RestController
public class MyController {
	
	
	private static final Logger log = LogManager.getLogger(MyController.class);
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/students")
	public List<Student> getAllStudent(){
		
		log.info("Fetching All Students");
		List<Student> students = studentService.getAllStudent();
		log.debug("Number is student fetched..{} "+students.size());
		return this.studentService.getAllStudent();
	}
	
	@GetMapping("/students/{studentid}")
	public Student getStudent(@PathVariable String studentid) {
		log.info("Fetching student with ID {}",studentid);
		try {
            Student student = this.studentService.getStudent(Long.parseLong(studentid));
            if (student != null) {
                log.debug("Student details: {}", student);
            } else {
                log.warn("Student with id {} not found", studentid);
            }
            return student;
        } catch (NumberFormatException e) {
            log.error("Invalid student id format: {}", studentid, e);
            throw e;
        }
	}
	  @PostMapping("/students")
	    public Student addStudent(@RequestBody Student student) {
	        log.info("Adding a new student: {}", student);
	        Student addedStudent = this.studentService.addStudent(student);
	        log.debug("Added student details: {}", addedStudent);
	        return addedStudent;
	 }
	  
	@PutMapping("/students")
	public Student updateStudent(@RequestBody Student student) {
		 log.info("Updating student: {}", student);
	        Student updatedStudent = this.studentService.updateStudent(student);
	        log.debug("Updated student details: {}", updatedStudent);
	        return updatedStudent;
	}
	
	@DeleteMapping("/students/{studentid}")
	public ResponseEntity<Object> deleteStudent(@PathVariable long studentid){
		   log.info("Deleting student with id: {}", studentid);
	        try {
	            boolean isStudentDeleted = studentService.deleteStudent(studentid);
	            if (isStudentDeleted) {
	                log.info("Student with id {} deleted successfully", studentid);
	                return new ResponseEntity<>("Student is deleted Successfully.", HttpStatus.OK);
	            } else {
	                log.warn("Student with id {} not found", studentid);
	                return new ResponseEntity<>("Student is Not found", HttpStatus.NOT_FOUND);
	            }
	        } catch (Exception e) {
	            log.error("Error occurred while deleting student with id: {}", studentid, e);
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

}
