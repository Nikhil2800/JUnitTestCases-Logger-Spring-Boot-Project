package com.org.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.org.Dao.StudentDao;
import com.org.Services.StudentServiceImpl;
import com.org.entities.Student;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

	@Mock
	private StudentDao studentDao;
	
	@InjectMocks
	private StudentServiceImpl serviceImpl;
	
	
	@Test
	void getAllStudentTest() {
		Student student  = new Student(1,"Nikhil Shinde","Sangli");
		when(studentDao.save(any(Student.class))).thenReturn(student);
		Student student1 = serviceImpl.addStudent(student);
		
		Student stud  =new Student(2,"Anil Shinde","Samgli");
		when(studentDao.save(any(Student.class))).thenReturn(stud);
		Student student2 = serviceImpl.addStudent(stud);
		
		List<Student> list=new ArrayList<>();
		list.add(student1);
		list.add(student2);
		
		when(studentDao.findAll()).thenReturn(list);
		List<Student> allStudent = serviceImpl.getAllStudent();
		
		assertEquals(2, allStudent.size());
		
	}
	
	@Test
	void getStudent() {
		Student student = new Student(1,"Nikhil Shinde","Sangli");
		when(studentDao.save(any(Student.class))).thenReturn(student);
		Student addStudent = serviceImpl.addStudent(student);
		
		when(studentDao.findById(anyLong())).thenReturn(Optional.of(addStudent));
		Student actual = serviceImpl.getStudent(1);
		
		assertEquals(1,actual.getId());
		
	}
	
	@Test
	void addStudent() {
		Student student = new Student(1,"Nikhil Shinde","Sangli");
		when(studentDao.save(any(Student.class))).thenReturn(student);
		Student addStudent = serviceImpl.addStudent(student);
		
		assertEquals(student, addStudent);
		
	}
	
	@Test
	void updateStudentTest() {
		Student student = new Student(1,"Nikhil Shinde","Sangli");
		when(studentDao.save(any(Student.class))).thenReturn(student);
		Student addStudent = serviceImpl.addStudent(student);
		
		Student update  =new Student(1,"Nikhil Shinde","Pune");
		when(studentDao.save(any(Student.class))).thenReturn(update);
		Student updateStudent = serviceImpl.updateStudent(update);
		
		assertEquals("Pune", updateStudent.getCity());
		
	}
	
	@Test
	void deleteStudentTets() {
		Student student = new Student(1,"Nikhil Shinde","Sangli");
		when(studentDao.save(student)).thenReturn(student);
		Student addStudent = serviceImpl.addStudent(student);
		
		
		when(studentDao.findById(anyLong())).thenReturn(Optional.of(addStudent));
		boolean deleteStudent = serviceImpl.deleteStudent(1);
		assertEquals(true, deleteStudent);
		
	}
	
	
}
