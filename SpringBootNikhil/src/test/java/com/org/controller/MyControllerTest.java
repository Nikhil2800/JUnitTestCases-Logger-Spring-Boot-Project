package com.org.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.org.Dao.StudentDao;
import com.org.Services.StudentService;
import com.org.entities.Student;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MyControllerTest {
	
	@Mock
	private StudentService studentService;
	
	@InjectMocks
	private MyController myController;
	
	@Test
	void getAllStudentTest() {
		// Setting up test data 
		Student stu1 = new Student(1,"Nikhil Shinde","Sangli");
		when(studentService.addStudent(any(Student.class))).thenReturn(stu1);
		Student student1 = myController.addStudent(stu1);
		Student stu2 =new Student(2,"Anil Shinde","Sangli");
		when(studentService.addStudent(any(Student.class))).thenReturn(stu2);
		Student student2 = myController.addStudent(stu2);

		List<Student> expected=new ArrayList<>();
		expected.add(student1);
		expected.add(student2);
		
		// mocking behaviour 
		when(studentService.getAllStudent()).thenReturn(expected);
		
		// Assertions
		List<Student> actual = myController.getAllStudent();
		assertEquals(2,actual.size());
	}
	
	@Test
	void getStudentTest() {
		Student student = new Student(1,"Nikhil Shinde","Sangli");  //step 1
		when(studentService.addStudent(any(Student.class))).thenReturn(student); //add any object of that class mock behaviour step 3
		Student student1 = myController.addStudent(student); //first add student for getting with id //step 2
		
		when(studentService.getStudent(anyLong())).thenReturn(student1); //step 5
		Student stud = myController.getStudent("1");  //step 4
		
		assertEquals(1, stud.getId()); //step 6
		assertNotEquals(2, stud.getId());		//step 6
		
	}
	
	@Test
	void addStudentTest() {
		Student student  = new Student(1,"Nikhil Shinde","Sangli");
		when(studentService.addStudent(any(Student.class))).thenReturn(student);
		Student addStudent = myController.addStudent(student);
		
		assertEquals(student,addStudent);
	}
	
	@Test
	void UpdateStudentTest() {
		Student student = new Student(1,"Nikhil Shinde","Pune");
		when(studentService.addStudent(any(Student.class))).thenReturn(student);
		Student addStud = myController.addStudent(student);
		
		Student student2 = new Student(1,"Nikhil Shinde","Sangli");
		when(studentService.updateStudent(any(Student.class))).thenReturn(student2);
		Student updateStudent = myController.updateStudent(student2);
		
		assertEquals("Sangli", updateStudent.getCity());
		
	}
	
	@Test
	void deleteStudentTest() {
		Student student = new Student(1,"Nikhil Shinde","Sangli");
		when(studentService.addStudent(any(Student.class))).thenReturn(student);
		Student addStudent = myController.addStudent(student);
		
		when(studentService.deleteStudent(anyLong())).thenReturn(true);
		ResponseEntity<Object> deleteStudent = myController.deleteStudent(1);
		assertEquals(HttpStatus.OK, deleteStudent.getStatusCode());
		
		when(studentService.deleteStudent(anyLong())).thenReturn(false);
		ResponseEntity<Object> deleteStudent2 = myController.deleteStudent(2);
		assertNotEquals(HttpStatus.OK, deleteStudent2.getStatusCode());
		
	}

	
	

}
