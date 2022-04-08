package com.synectiks.asset.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.DepartmentService;
import com.synectiks.asset.domain.Department;

@RestController
@RequestMapping("/api")
public class DepartmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/department/{id}")
	public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
		logger.info("Request to get department. Id: "+id);
		Optional<Department> odp = departmentService.getDepartment(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/department")
	public ResponseEntity<List<Department>> getAllDepartment() {
		logger.info("Request to get department");
		List<Department> list = departmentService.getAllDepartment();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/department/{id}")
	public ResponseEntity<Optional<Department>> deleteDepartment(@PathVariable Long id) {
		logger.info("Request to delete department by id: {}", id);
		Optional<Department> oSpa = departmentService.deleteDepartment(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/department")
	public ResponseEntity<Department> createDepartment(@RequestBody Department obj){
		logger.info("Request to create new department");
		Department spa = departmentService.createDepartment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/department")
	public ResponseEntity<Department> updateDepartment(@RequestBody Department obj){
		logger.info("Request to update department");
		Department spa = departmentService.updateDepartment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/department")
	public ResponseEntity<Optional<Department>> partialUpdateDepartment(@RequestBody Department obj){
		logger.info("Request to partially update department");
		Optional<Department> oSpa = departmentService.partialUpdateDepartment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/department/search")
	public ResponseEntity<List<Department>> searchAllDepartment(@RequestParam Map<String, String> obj){
		logger.info("Request to search department");
		List<Department> list = departmentService.searchAllDepartment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
}
