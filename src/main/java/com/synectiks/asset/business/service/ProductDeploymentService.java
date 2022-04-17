package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Department;
import com.synectiks.asset.domain.DeploymentEnvironment;
import com.synectiks.asset.domain.Product;
import com.synectiks.asset.domain.ProductDeployment;
import com.synectiks.asset.repository.ProductDeploymentRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ProductDeploymentService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductDeploymentService.class);
		
	@Autowired
	ProductService productService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	DeploymentEnvironmentService deploymentEnvironmentService;
	
	@Autowired
	DepartmentProductService departmentProductService;
	
	@Autowired
	ProductDeploymentRepository productDeploymentRepository;
	
	public ProductDeployment deploy(Long departmentid, Long productId, Long deploymentEnvironmentId){
		logger.info("Deploying a product");
		Optional<Department> oDp = departmentService.getDepartment(departmentid);
		Optional<Product> op = productService.getProduct(productId);
		Optional<DeploymentEnvironment> oDe = deploymentEnvironmentService.getDeploymentEnvironment(deploymentEnvironmentId);
		if(!oDp.isPresent()) {
			throw new BadRequestAlertException("Department not found", "Department", "idnotfound");
		}		
		if(!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", "Product", "idnotfound");
		}
		if(!oDe.isPresent()) {
			throw new BadRequestAlertException("Deployment environment not found", "DeploymentEnvironment", "idnotfound");
		}

		ProductDeployment dp = new ProductDeployment();
		dp.setDepartment(oDp.get());
		dp.setProduct(op.get());
		dp.setDeploymentEnvironment(oDe.get());
		dp.setStatus(Constants.ACTIVE);
		Instant instant = Instant.now();
		dp.setDescription(oDe.get().getName() + " is associated with product "+op.get().getName()+" in environment "+oDe.get().getName());
		dp.setCreatedOn(instant);
		dp.setUpdatedOn(instant);
		dp = productDeploymentRepository.save(dp);
		Department department =  dp.getDepartment();
		department.setProductList(departmentProductService.getAllProductsOfDepartment(department));
		dp.setDepartment(department);
		return dp;
	}
	
	public boolean unDeploy(Long departmentid, Long productId, Long deploymentEnvironmentId){
		logger.info("Undeploying a product");
		Optional<Department> oDp = departmentService.getDepartment(departmentid);
		Optional<Product> op = productService.getProduct(productId);
		Optional<DeploymentEnvironment> oDe = deploymentEnvironmentService.getDeploymentEnvironment(deploymentEnvironmentId);
		if(!oDp.isPresent()) {
			throw new BadRequestAlertException("Department not found", "Department", "idnotfound");
		}		
		if(!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", "Product", "idnotfound");
		}
		if(!oDe.isPresent()) {
			throw new BadRequestAlertException("Deployment environment not found", "DeploymentEnvironment", "idnotfound");
		}

		ProductDeployment dp = new ProductDeployment();
		dp.setDepartment(oDp.get());
		dp.setProduct(op.get());
		dp.setDeploymentEnvironment(oDe.get());
		
		Optional<ProductDeployment> odp = productDeploymentRepository.findOne(Example.of(dp));
		if(odp.isPresent()) {
			productDeploymentRepository.deleteById(odp.get().getId());
			logger.info("Product {} instance terminated successfully from server {}", odp.get().getProduct().getName(), odp.get().getDeploymentEnvironment().getName());
			return true;
		}
		logger.warn("Product instance termination failed");
		return false;
	}
	
	List<ProductDeployment> getDeploymentEnvironmentOfProduct(Department department, Product product){
		ProductDeployment dp = new ProductDeployment();
		dp.setProduct(product);
		dp.setDepartment(department);
		return productDeploymentRepository.findAll(Example.of(dp));
	}
}
