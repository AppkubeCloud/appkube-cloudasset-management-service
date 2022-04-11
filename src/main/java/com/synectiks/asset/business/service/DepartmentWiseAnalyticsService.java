package com.synectiks.asset.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synectiks.asset.domain.Department;
import com.synectiks.asset.domain.DeploymentEnvironment;
import com.synectiks.asset.domain.Product;
import com.synectiks.asset.domain.ProductDeployment;
import com.synectiks.asset.domain.Services;

@Service
public class DepartmentWiseAnalyticsService {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentWiseAnalyticsService.class);
		
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	DepartmentProductService departmentProductService;
	
	@Autowired
	ProductDeploymentService productDeploymentService;
	
	@Autowired
	ProductServicesService productServicesService;
	
	
	public Optional<Department> getDepartment(Long id) {
		return departmentService.getDepartment(id);
	}
	
	public List<Department> searchDepartmentWiseStats(Map<String, String> obj) {
		//search all department
		List<Department> departmentList = departmentService.searchAllDepartment(obj);
		for(Department dp: departmentList) {
			//search all product of each department
			dp.setProductList(departmentProductService.getAllProductsOfDepartment(dp));
			for(Product prd: dp.getProductList()) {
				//search deployment environment of each product
				List<ProductDeployment> pdEnvList = productDeploymentService.getDeploymentEnvironmentOfProduct(dp, prd);
				//search services belongs to each product
				List<Services> serviceList = productServicesService.getAllServicesOfProduct(prd);
				//set deployment environment of each product
				List<DeploymentEnvironment> depEnvList = new ArrayList<>();
				for(ProductDeployment pde: pdEnvList) {
					DeploymentEnvironment depEnv = pde.getDeploymentEnvironment();
					//set service list of a product in the deployment environment
					depEnv.setServiceList(serviceList);
					depEnvList.add(depEnv);
				}
				prd.setDeploymentEnvironmentList(depEnvList);
				
			}
		}
		
		return departmentList;
	}
	

	
}
