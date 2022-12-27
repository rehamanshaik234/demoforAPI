package com.example.demoforAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
	@Autowired
	StudentRepo studentRepo;

	@PostMapping("/api/students")
	public ResponseEntity<Student> savaStudent(@RequestBody Student student){
		return  new ResponseEntity<>(studentRepo.save(student), HttpStatus.CREATED);
	}
	@GetMapping("/api/students")
	public ResponseEntity<List<Student>> getStudent(){
		return new ResponseEntity<>(studentRepo.findAll(),HttpStatus.OK);
	}

	@GetMapping("/api/students/{id}")
	public ResponseEntity<Student> getStudentByID(@PathVariable long id){
		Optional<Student> student= studentRepo.findById(id);
		if(student.isPresent()){
			return new ResponseEntity<>(student.get(),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping(value = "api/update_students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable long id,@RequestBody Student stud){
		Optional<Student> student= studentRepo.findById(id);
		if(student.isPresent()){
			student.get().setStudentName(stud.getStudentName());
			student.get().setStudentAddress(stud.getStudentAddress());
			student.get().setStudentEmail(stud.getStudentEmail());
		return new ResponseEntity<>(studentRepo.save(student.get()),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping(value = "/api/delete_students")
	public ResponseEntity deleteByID(@RequestBody Student stud) {
		Optional<Student> student = studentRepo.findById(stud.getId());
		System.out.println(student.toString());
		if (student.isPresent()) {
			studentRepo.deleteById(stud.getId());
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

}
