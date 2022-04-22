package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.CloudEnvironment;
import com.synectiks.asset.domain.Department;
import com.synectiks.asset.domain.DepartmentProduct;
import com.synectiks.asset.domain.Product;
import com.synectiks.asset.repository.DepartmentProductRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class DepartmentProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentProductService.class);
		
	@Autowired
	ProductService productService;
	
	@Autowired
	CloudEnvironmentService cloudEnvironmentService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	ProductServicesService productServicesService;
	
	@Autowired
	DepartmentProductRepository departmentProductRepository;
	
	public Department atachProduct(Long departmentid, Long cloudEnvironmentId, Long productId ){
		logger.info("Attaching product to the department");
		Optional<Department> oDp = departmentService.getDepartment(departmentid);
		Optional<CloudEnvironment> oCle = cloudEnvironmentService.getCloudEnvironment(cloudEnvironmentId);
		Optional<Product> op = productService.getProduct(productId);
		if(!oDp.isPresent()) {
			throw new BadRequestAlertException("Department not found", "Department", "idnotfound");
		}		
		if(!oCle.isPresent()) {
			throw new BadRequestAlertException("Cloud environment not found", "CloudEnvironment", "idnotfound");
		}
		if(!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", "Product", "idnotfound");
		}
		

		DepartmentProduct dp = new DepartmentProduct();
		dp.setProduct(op.get());
		dp.setCloudEnvironment(oCle.get());
		dp.setDepartment(oDp.get());
		dp.setStatus(Constants.ACTIVE);
		Instant instant = Instant.now();
		dp.setDescription(oDp.get().getName() + " is associated with product "+op.get().getName());
		dp.setCreatedOn(instant);
		dp.setUpdatedOn(instant);
		dp = departmentProductRepository.save(dp);
		Department department =  dp.getDepartment();
		department.setProductList(getAllProductsOfDepartment(dp.getDepartment()));
		return department;
	}
	
	public boolean detachProduct(Long departmentid, Long cloudEnvironmentId, Long productId){
		logger.info("Detaching product from a department");
		Optional<Department> oDp = departmentService.getDepartment(departmentid);
		Optional<CloudEnvironment> oCle = cloudEnvironmentService.getCloudEnvironment(cloudEnvironmentId);
		Optional<Product> op = productService.getProduct(productId);
		if(!oDp.isPresent()) {
			throw new BadRequestAlertException("Department not found", "Department", "idnotfound");
		}		
		if(!oCle.isPresent()) {
			throw new BadRequestAlertException("Cloud environment not found", "CloudEnvironment", "idnotfound");
		}
		if(!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", "Product", "idnotfound");
		}
		
		DepartmentProduct dp = new DepartmentProduct();
		dp.setProduct(op.get());
		dp.setCloudEnvironment(oCle.get());
		dp.setDepartment(oDp.get());
		
		Optional<DepartmentProduct> odp = departmentProductRepository.findOne(Example.of(dp));
		if(odp.isPresent()) {
			departmentProductRepository.deleteById(odp.get().getId());
			logger.info("Product deleted from department successfully");
			return true;
		}
		logger.warn("Product deletion from department failed");
		return false;
	}
	
	public List<Product> getAllProductsOfDepartment(Department department) {
		DepartmentProduct dp = new DepartmentProduct();
		dp.setDepartment(department);
		List<Product> productList = new ArrayList<>();
		List<DepartmentProduct> listDp = departmentProductRepository.findAll(Example.of(dp),  Sort.by(Direction.DESC, "id"));
		for(DepartmentProduct d: listDp) {
			Product product = d.getProduct();
			product.setServiceList(productServicesService.getAllServicesOfProduct(product));
			productList.add(product);
		}
		return productList;
	}

	public List<Product> getAllProducts(Department department, CloudEnvironment cloudEnvironment) {
		DepartmentProduct dp = new DepartmentProduct();
		dp.setDepartment(department);
		dp.setCloudEnvironment(cloudEnvironment);
		List<Product> productList = new ArrayList<>();
		List<DepartmentProduct> listDp = departmentProductRepository.findAll(Example.of(dp),  Sort.by(Direction.DESC, "id"));
		for(DepartmentProduct d: listDp) {
			Product product = d.getProduct();
			product.setServiceList(productServicesService.getAllServicesOfProduct(product));
			productList.add(product);
		}
		return productList;
	}
	
	public List<Product> getAllProductsOfDepartment(Long departmentId) {
		Optional<Department> od = departmentService.getDepartment(departmentId);
		if(!od.isPresent()) {
			return Collections.emptyList();
		}
		return getAllProductsOfDepartment(od.get());
	}
	
	public List<Product> getAllProducts(Long departmentId, Long cloudEnvId) {
		Optional<Department> od = departmentService.getDepartment(departmentId);
		Optional<CloudEnvironment> oce = cloudEnvironmentService.getCloudEnvironment(cloudEnvId);
		if(!od.isPresent()) {
			return Collections.emptyList();
		}
		if(!oce.isPresent()) {
			return Collections.emptyList();
		}
		return getAllProducts(od.get(), oce.get());
	}
	
}
