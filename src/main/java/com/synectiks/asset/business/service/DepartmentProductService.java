package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.ArrayList;
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
	DepartmentProductRepository departmentProductRepository;
	
	public Department attachProduct(Long departmentid, Long productId, Long cloudEnvironmentId){
		logger.info("Attaching product to the department");
		Optional<Product> op = productService.getProduct(productId);
		Optional<CloudEnvironment> oCle = cloudEnvironmentService.getCloudEnvironment(cloudEnvironmentId);
		Optional<Department> oDp = departmentService.getDepartment(departmentid);
		
		if(!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", "Product", "idnotfound");
		}
		if(!oCle.isPresent()) {
			throw new BadRequestAlertException("Cloud environment not found", "CloudEnvironment", "idnotfound");
		}
		if(!oDp.isPresent()) {
			throw new BadRequestAlertException("Department not found", "Department", "idnotfound");
		}
		DepartmentProduct dp = new DepartmentProduct();
		dp.setProduct(op.get());
		dp.setCloudEnvironment(oCle.get());
		dp.setStatus(Constants.ACTIVE);
		Instant instant = Instant.now();
		dp.setDescription(oCle.get().getDepartment().getName() + " is associated with product "+op.get().getName());
		dp.setCreatedOn(instant);
		dp.setUpdatedOn(instant);
		dp = departmentProductRepository.save(dp);
		Department department =  dp.getCloudEnvironment().getDepartment();
		department.setProductList(getAllProductsOfDepartment(dp.getCloudEnvironment()));
		return department;
	}
	
	public boolean detachProduct(Long productId, Long cloudEnvironmentId){
		logger.info("Detaching product from a department");
		Optional<Product> op = productService.getProduct(productId);
		Optional<CloudEnvironment> oCle = cloudEnvironmentService.getCloudEnvironment(cloudEnvironmentId);
		if(!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", "Product", "idnotfound");
		}
		if(!oCle.isPresent()) {
			throw new BadRequestAlertException("Cloud environment not found", "CloudEnvironment", "idnotfound");
		}
		
		DepartmentProduct dp = new DepartmentProduct();
		dp.setProduct(op.get());
		dp.setCloudEnvironment(oCle.get());
		Optional<DepartmentProduct> odp = departmentProductRepository.findOne(Example.of(dp));
		if(odp.isPresent()) {
			departmentProductRepository.deleteById(odp.get().getId());
			logger.info("Product deleted from department successfully");
			return true;
		}
		logger.warn("Product deletion from department failed");
		return false;
	}
	
	public List<Product> getAllProductsOfDepartment(CloudEnvironment cloudEnvironment) {
		DepartmentProduct dp = new DepartmentProduct();
		dp.setCloudEnvironment(cloudEnvironment);
		List<Product> productList = new ArrayList<>();
		List<DepartmentProduct> listDp = departmentProductRepository.findAll(Example.of(dp),  Sort.by(Direction.DESC, "id"));
		for(DepartmentProduct d: listDp) {
			productList.add(d.getProduct());
		}
		return productList;
	}
	
//	public List<Services> getAllServicesOfProduct(Product product){
//		com.synectiks.asset.domain.ProductService ps = new com.synectiks.asset.domain.ProductService();
//		ps.setProduct(product);
//		List<com.synectiks.asset.domain.ProductService> list = departmentProductRepository.findAll(Example.of(ps), Sort.by(Direction.DESC, "id"));
//		List<Services> servicesList = new ArrayList<>();
//		for(com.synectiks.asset.domain.ProductService obj: list) {
//			servicesList.add(obj.getServices());
//		}
//		return servicesList;
//	}
}
