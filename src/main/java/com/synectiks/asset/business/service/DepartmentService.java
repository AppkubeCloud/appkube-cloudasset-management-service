package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synectiks.asset.domain.Department;
import com.synectiks.asset.repository.DepartmentRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class DepartmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);
		
	@Autowired
	DepartmentRepository departmentRepository;
	
	public Optional<Department> getDepartment(Long id) {
		logger.info("Get department by id: {}", id);
		return departmentRepository.findById(id);
	}
	
	public List<Department> getAllDepartment() {
		logger.info("Get all departments");
		return departmentRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<Department> deleteDepartment(Long id) {
		logger.info("Delete department by id: {}", id);
		Optional<Department> oDp = getDepartment(id);
		if(!oDp.isPresent()) {
			logger.warn("Id {} not found. department cannot be deleted", id);
			return oDp;
		}
		departmentRepository.deleteById(id);
		return oDp;
	}
	
	public Department createDepartment(Department obj){
		logger.info("Create new department");
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return departmentRepository.save(obj);
	}
	
	public Department updateDepartment(Department obj){
		logger.info("Update department. Id: {}", obj.getId());
		if(!departmentRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Department", "idnotfound");
		}
		if(Objects.isNull(obj.getOrgId()) || (!Objects.isNull(obj.getOrgId()) && obj.getOrgId() < 0)) {
			throw new BadRequestAlertException("Invalid organization id", "Department", "idnotfound");
		}
		obj.setUpdatedOn(Instant.now());
		return departmentRepository.save(obj);
	}
	
	public Optional<Department> partialUpdateDepartment(Department obj){
		logger.info("Update department partialy. Id: {}", obj.getId());
		if(!departmentRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Department", "idnotfound");
		}
		
		if(Objects.isNull(obj.getOrgId()) || (!Objects.isNull(obj.getOrgId()) && obj.getOrgId() < 0)) {
			throw new BadRequestAlertException("Invalid organization id", "Department", "idnotfound");
		}
		
		Optional<Department> result = departmentRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getName())) {
					existingObj.setName(obj.getName());
				}
				if(!StringUtils.isBlank(obj.getDescription())) {
					existingObj.setDescription(obj.getDescription());
				}
				if(!Objects.isNull(obj.getOrgId()) && obj.getOrgId() >= 0) {
					existingObj.setOrgId(obj.getOrgId());
				}
				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus());
				}
				existingObj.updatedOn(Instant.now());
				return existingObj;
			})
			.map(departmentRepository::save);
		return result;
	}
	
	public List<Department> searchAllDepartment(Map<String, String> obj) {
		logger.info("Search departments");
		Department dp = new Department();
		boolean isFilter = false;
		
		if(!StringUtils.isBlank(obj.get("id"))) {
			dp.setId(Long.parseLong(obj.get("id")));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("name"))) {
			dp.setName(obj.get("name"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("description"))) {
			dp.setDescription(obj.get("description"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("orgId"))) {
			dp.setOrgId(Long.parseLong(obj.get("orgId")));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("status"))) {
			dp.setStatus(obj.get("status"));
			isFilter = true;
		}
		
		List<Department> list = null;
		if(isFilter) {
			list = departmentRepository.findAll(Example.of(dp), Sort.by(Direction.DESC, "id"));
		}else {
			list = departmentRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	

	
}
