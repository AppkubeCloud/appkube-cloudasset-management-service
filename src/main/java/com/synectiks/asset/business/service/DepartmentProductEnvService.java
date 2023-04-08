package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synectiks.asset.business.domain.Department;
import com.synectiks.asset.business.domain.DepartmentProductEnv;
import com.synectiks.asset.business.domain.Product;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.DepartmentProductEnvRepository;
import com.synectiks.asset.repository.ProductRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

@Service
public class DepartmentProductEnvService {
	
	private final Logger logger = LoggerFactory.getLogger(DepartmentProductEnvService.class);
	
//	@Autowired
//	private ProductService productService;
//	
//	@Autowired
//	private CloudEnvironmentService cloudEnvironmentService;
//	
//	@Autowired
//	private ProductServicesService productServicesService;
	
	@Autowired
	private DepartmentProductEnvRepository departmentProductEnvRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;
	
    public DepartmentProductEnv save(DepartmentProductEnv departmentProductEnv) {
        logger.debug("Request to save departmentProductEnv : {}", departmentProductEnv);
        return departmentProductEnvRepository.save(departmentProductEnv);
    }

    public Optional<DepartmentProductEnv> partialUpdate(DepartmentProductEnv departmentProductEnv) {
        logger.debug("Request to partially update departmentProductEnv : {}", departmentProductEnv);

        return departmentProductEnvRepository
            .findById(departmentProductEnv.getId())
            .map(existingDepartmentProduct -> {
                if (departmentProductEnv.getCreatedOn() != null) {
                    existingDepartmentProduct.setCreatedOn(departmentProductEnv.getCreatedOn());
                }
                if (departmentProductEnv.getUpdatedOn() != null) {
                    existingDepartmentProduct.setUpdatedOn(departmentProductEnv.getUpdatedOn());
                }
                if (departmentProductEnv.getUpdatedBy() != null) {
                    existingDepartmentProduct.setUpdatedBy(departmentProductEnv.getUpdatedBy());
                }
                if (departmentProductEnv.getCreatedBy() != null) {
                    existingDepartmentProduct.setCreatedBy(departmentProductEnv.getCreatedBy());
                }
                if (departmentProductEnv.getDepartmentId() != null) {
                    existingDepartmentProduct.setDepartmentId(departmentProductEnv.getDepartmentId());
                }
                if (departmentProductEnv.getProductId() != null) {
                    existingDepartmentProduct.setProductId(departmentProductEnv.getProductId());
                }
                if (departmentProductEnv.getDeploymentEnvironmentId() != null) {
                    existingDepartmentProduct.setDeploymentEnvironmentId(departmentProductEnv.getDeploymentEnvironmentId());
                }
                if (departmentProductEnv.getLandingZone() != null) {
                    existingDepartmentProduct.setLandingZone(departmentProductEnv.getLandingZone());
                }
                return existingDepartmentProduct;
            })
            .map(departmentProductEnvRepository::save);
    }

    @Transactional(readOnly = true)
    public List<DepartmentProductEnv> findAll() {
        logger.debug("Request to get all departmentProductEnv");
        return departmentProductEnvRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DepartmentProductEnv> findOne(Long id) {
        logger.debug("Request to get departmentProductEnv : {}", id);
        return departmentProductEnvRepository.findById(id);
    }

    public void delete(Long id) {
        logger.debug("Request to delete departmentProductEnv : {}", id);
        departmentProductEnvRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
	public List<DepartmentProductEnv> search(Map<String, String> filter) throws IOException {
		logger.debug("Request to get all departmentProductEnv on given filters");
		
		DepartmentProductEnv departmentProductEnv = jsonAndObjectConverterUtil.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, DepartmentProductEnv.class);

		// unset default value if createdOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.CREATED_ON))) {
			departmentProductEnv.setCreatedOn(null);
		}
		// unset default value if updatedOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.UPDATED_ON))) {
			departmentProductEnv.setUpdatedOn(null);
		}
		
		return departmentProductEnvRepository.findAll(Example.of(departmentProductEnv), Sort.by(Sort.Direction.DESC, "id"));
	}
    
	public List<Product> getProducts(Map<String, String> filter, Department department) throws IOException {
		logger.debug("Getting products of given department");
		if(filter == null) {
			filter = new HashMap<>();
			filter.put("departmentId", String.valueOf(department.getId()));
		}
		
		List<DepartmentProductEnv> list = search(filter);
		List<Long> idList = new ArrayList<>();
		for(DepartmentProductEnv dpe: list) {
			idList.add(dpe.getProductId());
		}
		return findAllById(idList);
	}
	
	public List<Product> findAllById(List<Long> idList){
		return productRepository.findAllById(idList);
	}
	
//	public Department atachProduct(Long departmentid, Long cloudEnvironmentId, Long productId ){
//		logger.info("Attaching product to the department");
//		Optional<Department> oDp = departmentService.findOne(departmentid);
//		Optional<CloudEnvironment> oCle = cloudEnvironmentService.getCloudEnvironment(cloudEnvironmentId);
//		Optional<Product> op = productService.findOne(productId);
//		if(!oDp.isPresent()) {
//			throw new BadRequestAlertException("Department not found", "Department", "idnotfound");
//		}		
//		if(!oCle.isPresent()) {
//			throw new BadRequestAlertException("Cloud environment not found", "CloudEnvironment", "idnotfound");
//		}
//		if(!op.isPresent()) {
//			throw new BadRequestAlertException("Product not found", "Product", "idnotfound");
//		}
//		
//
//		DepartmentProductEnv dp = new DepartmentProductEnv();
//		dp.setProduct(op.get());
//		dp.setCloudEnvironment(oCle.get());
//		dp.setDepartment(oDp.get());
//		dp.setStatus(Constants.ACTIVE);
//		Instant instant = Instant.now();
//		dp.setDescription(oDp.get().getName() + " is associated with product "+op.get().getName());
//		dp.setCreatedOn(instant);
//		dp.setUpdatedOn(instant);
//		dp = departmentProductRepository.save(dp);
//		Department department =  dp.getDepartment();
////		department.setProductList(getAllProductsOfDepartment(dp.getDepartment()));
//		return department;
//	}
//	
//	public boolean detachProduct(Long departmentid, Long cloudEnvironmentId, Long productId){
//		logger.info("Detaching product from a department");
//		Optional<Department> oDp = departmentService.findOne(departmentid);
//		Optional<CloudEnvironment> oCle = cloudEnvironmentService.getCloudEnvironment(cloudEnvironmentId);
//		Optional<Product> op = productService.findOne(productId);
//		if(!oDp.isPresent()) {
//			throw new BadRequestAlertException("Department not found", "Department", "idnotfound");
//		}		
//		if(!oCle.isPresent()) {
//			throw new BadRequestAlertException("Cloud environment not found", "CloudEnvironment", "idnotfound");
//		}
//		if(!op.isPresent()) {
//			throw new BadRequestAlertException("Product not found", "Product", "idnotfound");
//		}
//		
//		DepartmentProductEnv dp = new DepartmentProductEnv();
//		dp.setProduct(op.get());
//		dp.setCloudEnvironment(oCle.get());
//		dp.setDepartment(oDp.get());
//		
//		Optional<DepartmentProductEnv> odp = departmentProductRepository.findOne(Example.of(dp));
//		if(odp.isPresent()) {
//			departmentProductRepository.deleteById(odp.get().getId());
//			logger.info("Product deleted from department successfully");
//			return true;
//		}
//		logger.warn("Product deletion from department failed");
//		return false;
//	}
//	
//	public List<Product> getAllProductsOfDepartment(Department department) {
//		DepartmentProductEnv dp = new DepartmentProductEnv();
//		dp.setDepartment(department);
//		List<Product> productList = new ArrayList<>();
//		List<DepartmentProductEnv> listDp = departmentProductRepository.findAll(Example.of(dp),  Sort.by(Direction.DESC, "id"));
//		for(DepartmentProductEnv d: listDp) {
//			Product product = d.getProduct();
////			product.setServiceList(productServicesService.getAllServicesOfProduct(product));
//			productList.add(product);
//		}
//		return productList;
//	}
//
//	public List<Product> getAllProducts(Department department, CloudEnvironment cloudEnvironment) {
//		DepartmentProductEnv dp = new DepartmentProductEnv();
//		dp.setDepartment(department);
//		dp.setCloudEnvironment(cloudEnvironment);
//		List<Product> productList = new ArrayList<>();
//		List<DepartmentProductEnv> listDp = departmentProductRepository.findAll(Example.of(dp),  Sort.by(Direction.DESC, "id"));
//		for(DepartmentProductEnv d: listDp) {
//			Product product = d.getProduct();
////			product.setServiceList(productServicesService.getAllServicesOfProduct(product));
//			productList.add(product);
//		}
//		return productList;
//	}
//	
//	public List<Product> getAllProductsOfDepartment(Long departmentId) {
//		Optional<Department> od = departmentService.findOne(departmentId);
//		if(!od.isPresent()) {
//			return Collections.emptyList();
//		}
//		return getAllProductsOfDepartment(od.get());
//	}
//	
//	public List<Product> getAllProducts(Long departmentId, Long cloudEnvId) {
//		Optional<Department> od = departmentService.findOne(departmentId);
//		Optional<CloudEnvironment> oce = cloudEnvironmentService.getCloudEnvironment(cloudEnvId);
//		if(!od.isPresent()) {
//			return Collections.emptyList();
//		}
//		if(!oce.isPresent()) {
//			return Collections.emptyList();
//		}
//		return getAllProducts(od.get(), oce.get());
//	}
	
}
