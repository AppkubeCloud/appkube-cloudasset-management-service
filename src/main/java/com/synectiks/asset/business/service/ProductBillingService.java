package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.Collections;
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
import com.synectiks.asset.domain.DeploymentEnvironment;
import com.synectiks.asset.domain.Product;
import com.synectiks.asset.domain.ProductBilling;
import com.synectiks.asset.repository.ProductBillingRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ProductBillingService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductBillingService.class);
		
	@Autowired
	ProductBillingRepository productBillingRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	DeploymentEnvironmentService deploymentEnvironmentService;
	
	@Autowired
	DepartmentService departmentService;
	
	
	public Optional<ProductBilling> getProductBilling(Long id) {
		logger.info("Get product billing by id: {}", id);
		return productBillingRepository.findById(id);
	}
	
	public List<ProductBilling> getAllProductBilling() {
		logger.info("Get all product billing");
		return productBillingRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<ProductBilling> deleteProductBilling(Long id) {
		logger.info("Delete product billing by id: {}", id);
		Optional<ProductBilling> oObj = getProductBilling(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. product billing cannot be deleted", id);
			return oObj;
		}
		productBillingRepository.deleteById(id);
		return oObj;
	}
	
	public ProductBilling createProductBilling(ProductBilling obj){
		logger.info("Create new product billing");
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		if(Objects.isNull(obj.getProduct()) || (!Objects.isNull(obj.getProduct()) && obj.getProduct().getId() < 0)) {
			throw new BadRequestAlertException("Invalid product id", "ProductBilling", "idnotfound");
		}
		if(Objects.isNull(obj.getDepartment()) || (!Objects.isNull(obj.getDepartment()) && obj.getDepartment().getId() < 0)) {
			throw new BadRequestAlertException("Invalid department id", "ProductBilling", "idnotfound");
		}
		if(Objects.isNull(obj.getDeploymentEnvironment()) || (!Objects.isNull(obj.getDeploymentEnvironment()) && obj.getDeploymentEnvironment().getId() < 0)) {
			throw new BadRequestAlertException("Invalid deployment-environment id", "ProductBilling", "idnotfound");
		}
		
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return productBillingRepository.save(obj);
	}
	
	public ProductBilling updateProductBilling(ProductBilling obj){
		logger.info("Update product billing. Id: {}", obj.getId());
		if(!productBillingRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ProductBilling", "idnotfound");
		}
		if(Objects.isNull(obj.getProduct()) || (!Objects.isNull(obj.getProduct()) && obj.getProduct().getId() < 0)) {
			throw new BadRequestAlertException("Invalid product id", "ProductBilling", "idnotfound");
		}
		if(Objects.isNull(obj.getDeploymentEnvironment()) || (!Objects.isNull(obj.getDeploymentEnvironment()) && obj.getDeploymentEnvironment().getId() < 0)) {
			throw new BadRequestAlertException("Invalid deployment-environment id", "ProductBilling", "idnotfound");
		}
		if(Objects.isNull(obj.getDepartment()) || (!Objects.isNull(obj.getDepartment()) && obj.getDepartment().getId() < 0)) {
			throw new BadRequestAlertException("Invalid department id", "ProductBilling", "idnotfound");
		}
		
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		obj.setUpdatedOn(Instant.now());
		return productBillingRepository.save(obj);
	}
	
	public Optional<ProductBilling> partialUpdateProductBilling(ProductBilling obj){
		logger.info("Update product billing partialy. Id: {}", obj.getId());
		if(!productBillingRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ProductBilling", "idnotfound");
		}
		if(Objects.isNull(obj.getProduct()) || (!Objects.isNull(obj.getProduct()) && obj.getProduct().getId() < 0)) {
			throw new BadRequestAlertException("Invalid product id", "ProductBilling", "idnotfound");
		}
		if(Objects.isNull(obj.getDeploymentEnvironment()) || (!Objects.isNull(obj.getDeploymentEnvironment()) && obj.getDeploymentEnvironment().getId() < 0)) {
			throw new BadRequestAlertException("Invalid deployment-environment id", "ProductBilling", "idnotfound");
		}
		if(Objects.isNull(obj.getDepartment()) || (!Objects.isNull(obj.getDepartment()) && obj.getDepartment().getId() < 0)) {
			throw new BadRequestAlertException("Invalid department id", "ProductBilling", "idnotfound");
		}
		
		Optional<ProductBilling> result = productBillingRepository.findById(obj.getId())
			.map(existingObj ->{
				if(obj.getAmount() != null) {
					existingObj.setAmount(obj.getAmount());
				}
				if(!StringUtils.isBlank(obj.getDescription())) {
					existingObj.setDescription(obj.getDescription());
				}
				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus().toUpperCase());
				}
				existingObj.setProduct(obj.getProduct());
				existingObj.setDeploymentEnvironment(obj.getDeploymentEnvironment());
				existingObj.setDepartment(obj.getDepartment());
				existingObj.updatedOn(Instant.now());
				return existingObj;
			})
			.map(productBillingRepository::save);
		return result;
	}
	
	public List<ProductBilling> searchAllProductBilling(Map<String, String> obj) {
		logger.info("Search product billing");
		ProductBilling cld = new ProductBilling();
		boolean isFilter = false;
		
		if(!StringUtils.isBlank(obj.get("id"))) {
			cld.setId(Long.parseLong(obj.get("id")));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("description"))) {
			cld.setDescription(obj.get("description"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("status"))) {
			cld.setStatus(obj.get("status").toUpperCase());
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("productId"))) {
			Optional<Product> opd = productService.getProduct(Long.parseLong(obj.get("productId")));
			if(opd.isPresent()) {
				cld.setProduct(opd.get());
				isFilter = true;
			}else {
				return Collections.emptyList();
			}
		}
		
		if(!StringUtils.isBlank(obj.get("deploymentEnvironmentId"))) {
			Optional<DeploymentEnvironment> ode = deploymentEnvironmentService.getDeploymentEnvironment(Long.parseLong(obj.get("deploymentEnvironmentId")));
			if(ode.isPresent()) {
				cld.setDeploymentEnvironment(ode.get());
				isFilter = true;
			}else {
				return Collections.emptyList();
			}
		}
		if(!StringUtils.isBlank(obj.get("departmentId"))) {
			Optional<Department> opd = departmentService.getDepartment(Long.parseLong(obj.get("departmentId")));
			if(opd.isPresent()) {
				cld.setDepartment(opd.get());
				isFilter = true;
			}else {
				return Collections.emptyList();
			}
		}
		
		List<ProductBilling> list = null;
		if(isFilter) {
			list = productBillingRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = productBillingRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	
}
