package com.org.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.org.Dao.StudentDao;
import com.org.controller.MyController;
import com.org.entities.Student;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDao studentDao;
	
	private static final Logger logger = LogManager.getLogger(MyController.class);

	@Override
	public List<Student> getAllStudent() {
		logger.info("Fetching all Students Data ");
		List<Student> findAll = studentDao.findAll();
		logger.info("Fetched {} students ",findAll.size());
		return findAll;
	}

	@Override
	public Student getStudent(long studentid) {
		logger.info("Fetched student with ID {}",studentid);
		Optional<Student> isStudent = studentDao.findById(studentid);
		if(isStudent.isPresent()) {
			logger.info("Student with Id {} Found ",studentid);
			return isStudent.get();
		}else {
			logger.warn("Student with Id {} not found ", studentid);
			return null;
		}
		
	}

	@Override
	public Student addStudent(Student student) {

		logger.info("Adding New Student..",student);
		Student saveStud = studentDao.save(student);
		logger.info("{} Student added with Id..."+student.getId());
		return saveStud;
	}

	 @Override
	    public Student updateStudent(Student student) {
	        logger.info("Updating student: {}", student);
	        Student updatedStudent = studentDao.save(student);
	        logger.info("Student updated with ID: {}", updatedStudent.getId());
	        return updatedStudent;
	    }
	 @Override
	    public boolean deleteStudent(long studentid) {
	        logger.info("Deleting student with ID: {}", studentid);
	        try {
	            Optional<Student> studopt = studentDao.findById(studentid);
	            if (studopt.isPresent()) {
	                studentDao.delete(studopt.get());
	                logger.info("Student with ID: {} deleted successfully", studentid);
	                return true;
	            } else {
	                logger.warn("Student with ID: {} not found", studentid);
	                return false;
	            }
	        } catch (Exception e) {
	            logger.error("Exception in deleteStudent ", e);
	            return false;
	        }


	 }

}
