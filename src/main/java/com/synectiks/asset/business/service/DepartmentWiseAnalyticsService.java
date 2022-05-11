package com.synectiks.asset.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Department;
import com.synectiks.asset.domain.DeploymentEnvironment;
import com.synectiks.asset.domain.Product;
import com.synectiks.asset.domain.ProductBilling;
import com.synectiks.asset.domain.ProductDeployment;
import com.synectiks.asset.domain.ServiceBilling;
import com.synectiks.asset.domain.ServiceCategory;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.domain.ServiceTag;
import com.synectiks.asset.domain.ServiceTagLink;
import com.synectiks.asset.domain.Services;
import com.synectiks.asset.response.AvailabilityResponse;
import com.synectiks.asset.response.DataProtectionResponse;
import com.synectiks.asset.response.DepartmentResponse;
import com.synectiks.asset.response.DepartmentWiseAnaliticResponse;
import com.synectiks.asset.response.DeploymentEnvironmentResponse;
import com.synectiks.asset.response.OrganizationResponse;
import com.synectiks.asset.response.PerformanceResponse;
import com.synectiks.asset.response.ProductBillingResponse;
import com.synectiks.asset.response.ProductResponse;
import com.synectiks.asset.response.SecurityResponse;
import com.synectiks.asset.response.ServiceBillingResponse;
import com.synectiks.asset.response.ServiceCategoryResponse;
import com.synectiks.asset.response.ServiceDetailReportResponse;
import com.synectiks.asset.response.ServiceNameResponse;
import com.synectiks.asset.response.ServiceResponse;
import com.synectiks.asset.response.ServiceTagLinkResponse;
import com.synectiks.asset.response.ServiceTagResponse;
import com.synectiks.asset.response.UserExperianceResponse;
import com.synectiks.asset.util.RandomUtil;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class DepartmentWiseAnalyticsService {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentWiseAnalyticsService.class);
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	DepartmentProductService departmentProductService;
	
	@Autowired
	ProductDeploymentService productDeploymentService;
	
	@Autowired
	ProductServicesService productServicesService;
	
	@Autowired
	ServiceBillingService serviceBillingService;
	
	@Autowired
	ProductBillingService productBillingService;
	
	@Autowired
	ServiceCategoryService serviceCategoryService;
	
	@Autowired
	ServiceTagService serviceTagService;
	
	@Autowired
	ServiceTagLinkService serviceTagLinkService;
	
	@Autowired
	ServiceDetailService serviceDetailService;
	
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
	

	public DepartmentWiseAnaliticResponse getDepartmentWiseStats(Map<String, String> obj) {
		
		if(StringUtils.isBlank(obj.get("orgId"))) {
			throw new BadRequestAlertException("Entity not found", "Organization", "idnotfound");
		}
		OrganizationResponse org = OrganizationResponse.from(organizationService.getOrgById(Long.parseLong(obj.get("orgId"))));
		
		List<Department> departmentList = departmentService.searchAllDepartment(obj);
		
		List<DepartmentResponse> departmentResponseList = new ArrayList<>();
		for(Department department: departmentList) {
			DepartmentResponse deptResp = DepartmentResponse.from(department);
			departmentResponseList.add(deptResp);
			
			List<Product> productList = departmentProductService.getAllProductsOfDepartment(department);
			List<ProductResponse> productResponseList = new ArrayList<>();
			for(Product product: productList) {
				ProductResponse productResponse = ProductResponse.from(product);
				productResponseList.add(productResponse);
				
				List<ProductDeployment> productDeploymentList = productDeploymentService.getDeploymentEnvironmentOfProduct(department, product);
				List<DeploymentEnvironmentResponse> deploymentEnvironmentResponseList = new ArrayList<>();
				Map<String, String> productBillSearchMap = new HashMap<>();
				for(ProductDeployment productDeployment: productDeploymentList) {
					DeploymentEnvironmentResponse depEnvResp = DeploymentEnvironmentResponse.from(productDeployment.getDeploymentEnvironment());
					depEnvResp.setServiceCategoryList(getServiceCategoryList());
					
					productBillSearchMap.clear();
					productBillSearchMap.put("departmentId", String.valueOf(department.getId())); 
					productBillSearchMap.put("productId", String.valueOf(product.getId()));
					productBillSearchMap.put("deploymentEnvironmentId", String.valueOf(productDeployment.getDeploymentEnvironment().getId()));
					List<ProductBilling> productBillingList = productBillingService.searchAllProductBilling(productBillSearchMap);
					if(productBillingList.size() > 0) {
						depEnvResp.setProductBilling(ProductBillingResponse.from(productBillingList.get(0)));
					}else {
						depEnvResp.setProductBilling(ProductBillingResponse.builder().amount(0D).build());
					}
					
					deploymentEnvironmentResponseList.add(depEnvResp);
					
					List<Services> productServiceList = productServicesService.getServicesList(product, productDeployment.getDeploymentEnvironment());
//					List<ServiceResponse> serviceRespList = new ArrayList<>();
					
					Map<String, String> searchMap = new HashMap<>();
					for(Services services: productServiceList) {
						filterServices(searchMap, department, product, depEnvResp.getServiceCategoryList(), services);
//						searchMap.clear();
//						ServiceResponse serviceResponse = ServiceResponse.from(services);
//						serviceRespList.add(serviceResponse);
//						searchMap.put("departmentId", String.valueOf(department.getId())); 
//						searchMap.put("productId", String.valueOf(product.getId()));
//						searchMap.put("servicesId", String.valueOf(services.getId()));
//						List<ServiceBilling> serviceBillingList =  serviceBillingService.searchAllServiceBilling(searchMap);
//						if(serviceBillingList.size() > 0) {
//							serviceResponse.setServiceBilling(ServiceBillingResponse.from(serviceBillingList.get(0)));
//						}else {
//							serviceResponse.setServiceBilling(ServiceBillingResponse.builder().amount(0D).build());
//						}
					}
//					depEnvResp.setServiceList(serviceRespList);
					
				}
				productResponse.setDeploymentEnvironmentList(deploymentEnvironmentResponseList);
			}
			deptResp.setProductList(productResponseList);
			deptResp.setTotalProduct(productResponseList.size());
		}
		org.setTotalDepartment(departmentResponseList.size());
		org.setDepartmentList(departmentResponseList);
		
		return DepartmentWiseAnaliticResponse.builder().organization(org).build();
//		List<Department> departmentList = departmentService.searchAllDepartment(obj);
		
	}
	
	private List<ServiceCategoryResponse> getServiceCategoryList(){
		List<ServiceCategoryResponse> srvCatRespList = new ArrayList<>();
		Map<String, String> reqMap = new HashMap<>();
		reqMap.put("status", Constants.ACTIVE);
		List<ServiceCategory> srvCatList = serviceCategoryService.searchAllServiceCategory(reqMap);
		for(ServiceCategory sc: srvCatList) {
			ServiceCategoryResponse scResp =ServiceCategoryResponse.from(sc);
//			scResp.setTagList(getTagList(sc));
			srvCatRespList.add(scResp);
		}
		return srvCatRespList;
	}
	
	private void filterServices(Map<String, String> searchMap, Department department, Product product, List<ServiceCategoryResponse> srvCatRespList, Services services) {
		searchMap.clear();
		searchMap.put("departmentId", String.valueOf(department.getId())); 
		searchMap.put("productId", String.valueOf(product.getId()));
		searchMap.put("servicesId", String.valueOf(services.getId()));
		List<ServiceBilling> serviceBillingList =  serviceBillingService.searchAllServiceBilling(searchMap);
		
		ServiceResponse serviceResponse = ServiceResponse.from(services);
//		serviceResponse.setTagList(getTagList()); // not here
//		serviceResponse.setPerformance(PerformanceResponse.builder().score(RandomUtil.getRandom()).build());
//		serviceResponse.setAvailability(AvailabilityResponse.builder().score(RandomUtil.getRandom()).build());
//		serviceResponse.setSecurity(SecurityResponse.builder().score(RandomUtil.getRandom()).build());
//		serviceResponse.setDataProtection(DataProtectionResponse.builder().score(RandomUtil.getRandom()).build());
//		serviceResponse.setUserExperiance(UserExperianceResponse.builder().score(RandomUtil.getRandom()).build());
		
		
		
		
		for(ServiceCategoryResponse scResp: srvCatRespList) {
//			if(services.getServiceCategory().getId().compareTo(scResp.getId()) == 0) {
//				if(scResp.getServiceList() == null) {
//					List<ServiceResponse> srvResp = new ArrayList<>();
//					srvResp.add(serviceResponse);
//					scResp.setServiceList(srvResp);
//				}else {
//					scResp.getServiceList().add(serviceResponse);
//				}
//				
//			}
			////
			int i=1;
			List<ServiceTagResponse> tagList = getTagList(scResp.getId());
			
//			for(ServiceTagResponse stResp: scResp.getTagList()) {
			for(ServiceTagResponse stResp: tagList) {
				
				searchMap.clear();
				searchMap.put("servicesId", String.valueOf(serviceResponse.getId())); 
				searchMap.put("serviceTagId", String.valueOf(stResp.getId()));
				List<ServiceTagLink> stlList = serviceTagLinkService.searchAllServiceTagLink(searchMap);
				if(stlList != null && stlList.size() > 0) {
					ServiceTagLink stl = stlList.get(0);
					ServiceTagLinkResponse serviceTagLinkResponse = ServiceTagLinkResponse.from(stl);
					serviceTagLinkResponse.setName(stl.getServices().getName()+ "-" +stl.getServiceTag().getTagName()+"-"+i);
					serviceTagLinkResponse.setPerformance(PerformanceResponse.builder().score(RandomUtil.getRandom()).build());
					serviceTagLinkResponse.setAvailability(AvailabilityResponse.builder().score(RandomUtil.getRandom()).build());
					serviceTagLinkResponse.setSecurity(SecurityResponse.builder().score(RandomUtil.getRandom()).build());
					serviceTagLinkResponse.setDataProtection(DataProtectionResponse.builder().score(RandomUtil.getRandom()).build());
					serviceTagLinkResponse.setUserExperiance(UserExperianceResponse.builder().score(RandomUtil.getRandom()).build());
					if(serviceBillingList.size() > 0) {
						serviceTagLinkResponse.setServiceBilling(ServiceBillingResponse.from(serviceBillingList.get(0)));
					}else {
						serviceTagLinkResponse.setServiceBilling(ServiceBillingResponse.builder().amount(0D).build());
					}
					if(stResp.getServiceList() == null) {
						List<ServiceTagLinkResponse> srvRespList = new ArrayList<>();
						srvRespList.add(serviceTagLinkResponse);
						stResp.setServiceList(srvRespList);
					}else {
						stResp.getServiceList().add(serviceTagLinkResponse);
					}
					
					////
					ServiceNameResponse serviceNameResponse = ServiceNameResponse.from(stl);
					
					// setting tag list in searciceNameList
//					serviceNameResponse.setTagList(scResp.getTagList());
					serviceNameResponse.setTagList(tagList);
					
					
					if(scResp.getServiceNameList() == null) {
						List<ServiceNameResponse> snRespList = new ArrayList<>();
						snRespList.add(serviceNameResponse);
						scResp.setServiceNameList(snRespList);
					}else {
						if(!scResp.getServiceNameList().contains(serviceNameResponse)) {
							scResp.getServiceNameList().add(serviceNameResponse);
						}
						
					}
				}
				++i;
			}
		}
	}

	// service category specific tags
	private List<ServiceTagResponse> getTagList(ServiceCategory sc) {
		return getTagList(sc.getId());
	}
	// service category specific tags
	private List<ServiceTagResponse> getTagList(Long scId) {
		List<ServiceTagResponse> srvTagRespList = new ArrayList<>();
		Map<String, String> reqMap = new HashMap<>();
		reqMap.put("status", Constants.ACTIVE);
		reqMap.put("serviceCategoryId", String.valueOf(scId));
		
		List<ServiceTag> srvTagList = serviceTagService.searchAllServiceTag(reqMap);
		for(ServiceTag st: srvTagList) {
			srvTagRespList.add(ServiceTagResponse.from(st));
		}
		return srvTagRespList;
	}
	// unique tags
	private List<ServiceTagResponse> getTagList() {
		List<ServiceTagResponse> srvTagRespList = new ArrayList<>();
		List<ServiceTag> srvTagList = serviceTagService.searchAllServiceTag(new HashMap());
		Map<String, ServiceTagResponse> map = new HashMap<>();
		for(ServiceTag st: srvTagList) {
			map.put(st.getTagName(), ServiceTagResponse.from(st)) ;
			
		}
		for(Map.Entry<String, ServiceTagResponse> entry: map.entrySet()) {
			srvTagRespList.add(entry.getValue());
		}
		return srvTagRespList;
	}
	
	
	// department wise analytics by service.json
	public DepartmentWiseAnaliticResponse getAnalyticalDataFromJson() {
		
		ServiceDetailReportResponse sdr = serviceDetailService.searchAllServiceDetail(new HashMap());
		OrganizationResponse org = null;
		
		//unique list of all the departments 
		Map<String, String> depMap = new HashMap<>();
		for(ServiceDetail sd: sdr.getServices()) {
			org = OrganizationResponse.builder().name(sd.getDetails().getAssociatedOU()).build();
			depMap.put(sd.getDetails().getAssociatedDept(), sd.getDetails().getAssociatedDept());
		}
		
		List<DepartmentResponse> departmentResponseList = new ArrayList<>();
		
		Map<String, String> productSearch = new HashMap<>();
		for(Map.Entry<String, String> entry: depMap.entrySet()) {
			// creating department response
			DepartmentResponse deptResp = DepartmentResponse.from(null,entry.getKey(),null);
			departmentResponseList.add(deptResp);
			
			// getting product of the department
			productSearch.clear();
			productSearch.put("associatedDept", entry.getKey());
			ServiceDetailReportResponse productJson = serviceDetailService.searchAllServiceDetail(productSearch);
			Map<String, String> uniqueProductMap = new HashMap<>();
			for(ServiceDetail prdSd: productJson.getServices() ) {
				uniqueProductMap.put(prdSd.getDetails().getAssociatedProduct(), prdSd.getDetails().getAssociatedProduct());
			}
			
			List<ProductResponse> productResponseList = new ArrayList<>();
			for(Map.Entry<String, String> prd: uniqueProductMap.entrySet()) {
				ProductResponse productResponse = ProductResponse.from(null, prd.getKey(), null, null);
				productResponseList.add(productResponse);
			}
			deptResp.setProductList(productResponseList);
		}
		
		for(DepartmentResponse depResp: departmentResponseList) {
			Map<String, String> depEnvSearch = new HashMap<>();
			for(ProductResponse prdResp: depResp.getProductList()) {
				depEnvSearch.clear();
				depEnvSearch.put("associatedDept", depResp.getName());
				depEnvSearch.put("associatedProduct", prdResp.getName());
				ServiceDetailReportResponse depEnvJson = serviceDetailService.searchAllServiceDetail(depEnvSearch);
				
				Map<String, String> depEnvMap = new HashMap<>(); // filter all deployment environment of this product
//				Map<String, String> serviceCatMap = new HashMap<>(); // filter all categories of this product
				for(ServiceDetail depEnvSd: depEnvJson.getServices()) {
					depEnvMap.put(depEnvSd.getDetails().getAssociatedEnv(), depEnvSd.getDetails().getAssociatedEnv());
//					serviceCatMap.put(depEnvSd.getDetails().getServiceNature(), depEnvSd.getDetails().getServiceNature());
				}
				
				// getting all the services of a department, product and deployment environment
				List<DeploymentEnvironmentResponse> deploymentEnvironmentResponseList = new ArrayList<>();
				for(Map.Entry<String, String> depEnvEntry: depEnvMap.entrySet()) {
					DeploymentEnvironmentResponse depEnvResp = DeploymentEnvironmentResponse.from(null, depEnvEntry.getKey());
					ProductBillingResponse pbr = ProductBillingResponse.from(null, RandomUtil.getRandom().doubleValue(), null);
					depEnvResp.setProductBilling(pbr);
					deploymentEnvironmentResponseList.add(depEnvResp);
					
					depEnvSearch.put("associatedEnv", depEnvEntry.getKey());
					// list of services related to a department, product and deployment environment
					ServiceDetailReportResponse serivesOfdepPrdDepEnvJson = serviceDetailService.searchAllServiceDetail(depEnvSearch);
					
					Map<String, String> serviceCatMap = new HashMap<>(); // filter all categories of this product
					Map<String, String> serviceTagMap = new HashMap<>(); // filter all tags of this product
					for(ServiceDetail depEnvSpecificSd: serivesOfdepPrdDepEnvJson.getServices()) {
						serviceCatMap.put(depEnvSpecificSd.getDetails().getServiceNature(), depEnvSpecificSd.getDetails().getServiceNature());
						serviceTagMap.put(depEnvSpecificSd.getDetails().getServiceType(), depEnvSpecificSd.getDetails().getServiceType());
					}
					List<ServiceCategoryResponse> srvCatRespList = new ArrayList<>();
					for(Map.Entry<String, String> srvNtrEntry: serviceCatMap.entrySet()) {
						ServiceCategoryResponse scr = ServiceCategoryResponse.from(null, srvNtrEntry.getKey(), null, null);
						srvCatRespList.add(scr);
					}
					depEnvResp.setServiceCategoryList(srvCatRespList);
					
					List<ServiceTagResponse> srvTagRespList = new ArrayList<>();
					for(Map.Entry<String, String> srvTagEntry: serviceTagMap.entrySet()) {
						ServiceTagResponse str = ServiceTagResponse.from(null, srvTagEntry.getKey(), null);
						srvTagRespList.add(str);
					}
					
					Map<String, String> srvSearchMap = new HashMap<>();
					
//					for(ServiceCategoryResponse scResp: srvCatRespList) {
//						for(ServiceTagResponse str: srvTagRespList) {
//							srvSearchMap.clear();
//							srvSearchMap.put("associatedDept", depResp.getName());
//							srvSearchMap.put("associatedProduct", prdResp.getName());
//							srvSearchMap.put("associatedEnv", depEnvEntry.getKey());
//							srvSearchMap.put("serviceNature", scResp.getName());
//							srvSearchMap.put("serviceType", str.getTagName());
//							ServiceDetailReportResponse finalServices = serviceDetailService.searchAllServiceDetail(srvSearchMap);
//							for(ServiceDetail fsd: finalServices.getServices()) {
//								ServiceTagLinkResponse serviceTagLinkResponse = ServiceTagLinkResponse.from(fsd.getId(), fsd.getDetails().getName(), fsd.getDetails().getServiceHostingType(), fsd.getDetails().getDescription(), null);
//								serviceTagLinkResponse.setPerformance(PerformanceResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setAvailability(AvailabilityResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setSecurity(SecurityResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setDataProtection(DataProtectionResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setUserExperiance(UserExperianceResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setServiceBilling(ServiceBillingResponse.from(fsd.getId(), 0D, null));
//								if(str.getServiceList() == null) {
//									List<ServiceTagLinkResponse> srvRespList = new ArrayList<>();
//									srvRespList.add(serviceTagLinkResponse);
//									str.setServiceList(srvRespList);
//								}else {
//									str.getServiceList().add(serviceTagLinkResponse);
//								}
//								
//								ServiceNameResponse serviceNameResponse = ServiceNameResponse.from(fsd.getId(), fsd.getDetails().getName());
//								serviceNameResponse.setTagList(srvTagRespList);
//								if(scResp.getServiceNameList() == null) {
//									List<ServiceNameResponse> snRespList = new ArrayList<>();
//									snRespList.add(serviceNameResponse);
//									scResp.setServiceNameList(snRespList);
//								}else {
//									if(!scResp.getServiceNameList().contains(serviceNameResponse)) {
//										scResp.getServiceNameList().add(serviceNameResponse);
//									}
//								}
//							}
//						}
//					}
					
					
					for(ServiceDetail depEnvSpecificSd: serivesOfdepPrdDepEnvJson.getServices()) {
						for(ServiceCategoryResponse scResp: srvCatRespList) {
							if(scResp.getName().equalsIgnoreCase(depEnvSpecificSd.getDetails().getServiceNature())) {
								ServiceNameResponse serviceNameResponse = ServiceNameResponse.from(depEnvSpecificSd.getId(), depEnvSpecificSd.getDetails().getName());
								serviceNameResponse.setTagList(srvTagRespList);
								if(scResp.getServiceNameList() == null) {
									List<ServiceNameResponse> snRespList = new ArrayList<>();
									snRespList.add(serviceNameResponse);
									scResp.setServiceNameList(snRespList);
								}else {
									if(!scResp.getServiceNameList().contains(serviceNameResponse)) {
										scResp.getServiceNameList().add(serviceNameResponse);
									}
								}
							}
							// one more time
							for(ServiceTagResponse str: srvTagRespList) {
								if(str.getTagName().equalsIgnoreCase(depEnvSpecificSd.getDetails().getServiceType())) {
									ServiceTagLinkResponse serviceTagLinkResponse = ServiceTagLinkResponse.from(depEnvSpecificSd);
									serviceTagLinkResponse.setPerformance(PerformanceResponse.builder().score(RandomUtil.getRandom()).build());
									serviceTagLinkResponse.setAvailability(AvailabilityResponse.builder().score(RandomUtil.getRandom()).build());
									serviceTagLinkResponse.setSecurity(SecurityResponse.builder().score(RandomUtil.getRandom()).build());
									serviceTagLinkResponse.setDataProtection(DataProtectionResponse.builder().score(RandomUtil.getRandom()).build());
									serviceTagLinkResponse.setUserExperiance(UserExperianceResponse.builder().score(RandomUtil.getRandom()).build());
									serviceTagLinkResponse.setServiceBilling(ServiceBillingResponse.from(depEnvSpecificSd.getId(), RandomUtil.getRandom().doubleValue(), null));
									if(str.getServiceList() == null) {
										List<ServiceTagLinkResponse> srvRespList = new ArrayList<>();
										srvRespList.add(serviceTagLinkResponse);
										str.setServiceList(srvRespList);
									}else {
//										str.getServiceList().add(serviceTagLinkResponse);
									}
								}
							}

							
						}
//						for(ServiceTagResponse str: srvTagRespList) {
//							if(str.getTagName().equalsIgnoreCase(depEnvSpecificSd.getDetails().getServiceType())) {
//								ServiceTagLinkResponse serviceTagLinkResponse = ServiceTagLinkResponse.from(depEnvSpecificSd.getId(), depEnvSpecificSd.getDetails().getName(), depEnvSpecificSd.getDetails().getServiceHostingType(), depEnvSpecificSd.getDetails().getDescription(), null);
//								serviceTagLinkResponse.setPerformance(PerformanceResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setAvailability(AvailabilityResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setSecurity(SecurityResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setDataProtection(DataProtectionResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setUserExperiance(UserExperianceResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setServiceBilling(ServiceBillingResponse.from(depEnvSpecificSd.getId(), RandomUtil.getRandom().doubleValue(), null));
//								if(str.getServiceList() == null) {
//									List<ServiceTagLinkResponse> srvRespList = new ArrayList<>();
//									srvRespList.add(serviceTagLinkResponse);
//									str.setServiceList(srvRespList);
//								}else {
//									str.getServiceList().add(serviceTagLinkResponse);
//								}
//							}
//						}
					}
					
//					for(ServiceDetail depEnvSpecificSd: serivesOfdepPrdDepEnvJson.getServices()) {
//						for(ServiceTagResponse str: srvTagRespList) {
//							if(str.getTagName().equalsIgnoreCase(depEnvSpecificSd.getDetails().getServiceType())) {
//								ServiceResponse serviceResponse = ServiceResponse.from(depEnvSpecificSd.getId(), depEnvSpecificSd.getDetails().getName(), depEnvSpecificSd.getDetails().getDescription(), null);
//								ServiceTagLinkResponse serviceTagLinkResponse = ServiceTagLinkResponse.from(depEnvSpecificSd.getId(), depEnvSpecificSd.getDetails().getName(), depEnvSpecificSd.getDetails().getServiceHostingType(), depEnvSpecificSd.getDetails().getDescription(), null);
//								serviceTagLinkResponse.setPerformance(PerformanceResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setAvailability(AvailabilityResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setSecurity(SecurityResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setDataProtection(DataProtectionResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setUserExperiance(UserExperianceResponse.builder().score(RandomUtil.getRandom()).build());
//								serviceTagLinkResponse.setServiceBilling(ServiceBillingResponse.from(depEnvSpecificSd.getId(), RandomUtil.getRandom().doubleValue(), null));
//								if(str.getServiceList() == null) {
//									List<ServiceTagLinkResponse> srvRespList = new ArrayList<>();
//									srvRespList.add(serviceTagLinkResponse);
//									str.setServiceList(srvRespList);
//								}else {
//									str.getServiceList().add(serviceTagLinkResponse);
//								}
//							}
//							
//						}
//					}
					
					
				}
				prdResp.setDeploymentEnvironmentList(deploymentEnvironmentResponseList);
			}
		}
		org.setTotalDepartment(departmentResponseList.size());
		org.setDepartmentList(departmentResponseList);
		return DepartmentWiseAnaliticResponse.builder().organization(org).build();
	}

}
