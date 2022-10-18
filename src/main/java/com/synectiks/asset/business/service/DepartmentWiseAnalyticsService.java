package com.synectiks.asset.business.service;

import java.util.ArrayList;
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
	public DepartmentWiseAnaliticResponse getAnalyticalDataFromJson(Map<String, String> obj) {
		
		ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(obj);
		OrganizationResponse org = null;
		
		//unique list of all the departments 
		Map<String, String> depMap = new HashMap<>();
		for(ServiceDetail sd: sdr.getServices()) {
			org = OrganizationResponse.builder().name((String)sd.getMetadata_json().get("associatedOU")).build();
			depMap.put((String)sd.getMetadata_json().get("associatedDept"), (String)sd.getMetadata_json().get("associatedDept"));
		}
		
		// product list of each department
		List<DepartmentResponse> departmentResponseList = new ArrayList<>();
		Map<String, String> productSearch = new HashMap<>();
		for(Map.Entry<String, String> entry: depMap.entrySet()) {  
			DepartmentResponse deptResp = DepartmentResponse.from(null,entry.getKey(),null);
			departmentResponseList.add(deptResp);
			
			productSearch.clear();
			productSearch.put("associatedDept", entry.getKey());
			ServiceDetailReportResponse productJson = serviceDetailService.searchServiceDetailWithFilter(productSearch);
			Map<String, String> uniqueProductMap = new HashMap<>();
			for(ServiceDetail prdSd: productJson.getServices() ) {
				uniqueProductMap.put((String)prdSd.getMetadata_json().get("associatedProduct"), (String)prdSd.getMetadata_json().get("associatedProduct"));
			}
			
			List<ProductResponse> productResponseList = new ArrayList<>();
			for(Map.Entry<String, String> prd: uniqueProductMap.entrySet()) {
				ProductResponse productResponse = ProductResponse.from(null, prd.getKey(), null, null);
				productResponseList.add(productResponse);
			}
			deptResp.setProductList(productResponseList);
		}
		
		// deployment environment of each product
		for(DepartmentResponse depResp: departmentResponseList) {
			Map<String, String> depEnvSearch = new HashMap<>();
			for(ProductResponse prdResp: depResp.getProductList()) {
				depEnvSearch.clear();
				depEnvSearch.put("associatedDept", depResp.getName());
				depEnvSearch.put("associatedProduct", prdResp.getName());
				ServiceDetailReportResponse depEnvJson = serviceDetailService.searchServiceDetailWithFilter(depEnvSearch);
				Map<String, String> deploymentEnvironmentMap = new HashMap<>(); 
				for(ServiceDetail depEnvSd: depEnvJson.getServices()) {
					deploymentEnvironmentMap.put((String)depEnvSd.getMetadata_json().get("associatedEnv"), (String)depEnvSd.getMetadata_json().get("associatedEnv"));
				}
				List<DeploymentEnvironmentResponse> deploymentEnvironmentResponseList = new ArrayList<>();
				for(Map.Entry<String, String> depEnvEntry: deploymentEnvironmentMap.entrySet()) {
					DeploymentEnvironmentResponse depEnvResp = DeploymentEnvironmentResponse.from(null, depEnvEntry.getKey());
					ProductBillingResponse pbr = ProductBillingResponse.from(null, RandomUtil.getRandom().doubleValue(), null);
					depEnvResp.setProductBilling(pbr);
					deploymentEnvironmentResponseList.add(depEnvResp);
				}
				prdResp.setDeploymentEnvironmentList(deploymentEnvironmentResponseList);
			}
		}
		
		//service category (common/business) for each deployment environment
		for(DepartmentResponse depResp: departmentResponseList) {
			for(ProductResponse prdResp: depResp.getProductList()) {
				Map<String, String> queryMap = new HashMap<>();
				for(DeploymentEnvironmentResponse deploymentEnvironmentResponse: prdResp.getDeploymentEnvironmentList()) {
					queryMap.clear();
					queryMap.put("associatedDept", depResp.getName());
					queryMap.put("associatedProduct", prdResp.getName());
					queryMap.put("associatedEnv", deploymentEnvironmentResponse.getName());
					ServiceDetailReportResponse serivesOfdepPrdDepEnvJson = serviceDetailService.searchServiceDetailWithFilter(queryMap);
					
					Map<String, String> serviceCatMap = new HashMap<>(); // filter all categories of this product
					for(ServiceDetail depEnvSpecificSd: serivesOfdepPrdDepEnvJson.getServices()) {
						serviceCatMap.put((String)depEnvSpecificSd.getMetadata_json().get("serviceNature"), (String)depEnvSpecificSd.getMetadata_json().get("serviceNature"));
					}
					
					List<ServiceCategoryResponse> srvCatRespList = new ArrayList<>();
					for(Map.Entry<String, String> srvNtrEntry: serviceCatMap.entrySet()) {
						ServiceCategoryResponse scr = ServiceCategoryResponse.from(null, srvNtrEntry.getKey(), null, null);
						srvCatRespList.add(scr);
					}
					deploymentEnvironmentResponse.setServiceCategoryList(srvCatRespList);
				}
			}
		}
		
		//service name list for each service category
		for(DepartmentResponse depResp: departmentResponseList) {
			for(ProductResponse prdResp: depResp.getProductList()) {
				for(DeploymentEnvironmentResponse depEnvResp: prdResp.getDeploymentEnvironmentList()) {
					Map<String, String> queryMap = new HashMap<>();
					for(ServiceCategoryResponse serCatResp: depEnvResp.getServiceCategoryList()) {
						queryMap.clear();
						queryMap.put("associatedDept", depResp.getName());
						queryMap.put("associatedProduct", prdResp.getName());
						queryMap.put("associatedEnv", depEnvResp.getName());
						queryMap.put("serviceNature", serCatResp.getName());
						ServiceDetailReportResponse json = serviceDetailService.searchServiceDetailWithFilter(queryMap);
						for(ServiceDetail sd: json.getServices()) {
							ServiceNameResponse serviceNameResponse = null;
							if("Common".equalsIgnoreCase(serCatResp.getName())) {
								serviceNameResponse = ServiceNameResponse.from(null, (String)sd.getMetadata_json().get("associatedCommonService"));
							}else if("Business".equalsIgnoreCase(serCatResp.getName())) {
								serviceNameResponse = ServiceNameResponse.from(null, (String)sd.getMetadata_json().get("associatedBusinessService"));
							}
							if(serCatResp.getServiceNameList() == null) {
								List<ServiceNameResponse> snRespList = new ArrayList<>();
								snRespList.add(serviceNameResponse);
								serCatResp.setServiceNameList(snRespList);
							}else {
//								for(ServiceNameResponse snResp: serCatResp.getServiceNameList()) {
									if(!serCatResp.getServiceNameList().contains(serviceNameResponse)) {
										serCatResp.getServiceNameList().add(serviceNameResponse);
									}
//								}
							}
						}
					}
				}
			}
		}
		
		//Tag list for each service name
		for(DepartmentResponse depResp: departmentResponseList) {
			for(ProductResponse prdResp: depResp.getProductList()) {
				for(DeploymentEnvironmentResponse depEnvResp: prdResp.getDeploymentEnvironmentList()) {
					Map<String, String> queryMap = new HashMap<>();
					for(ServiceCategoryResponse serCatResp: depEnvResp.getServiceCategoryList()) {
						queryMap.clear();
						queryMap.put("associatedDept", depResp.getName());
						queryMap.put("associatedProduct", prdResp.getName());
						queryMap.put("associatedEnv", depEnvResp.getName());
						queryMap.put("serviceNature", serCatResp.getName());
						ServiceDetailReportResponse json = serviceDetailService.searchServiceDetailWithFilter(queryMap);
						for(ServiceNameResponse snResp: serCatResp.getServiceNameList()) {
							Map<String, String> serviceTagMap = new HashMap<>(); // filter all tags of this product
							for(ServiceDetail sd: json.getServices()) {
								serviceTagMap.put((String)sd.getMetadata_json().get("serviceType"), (String)sd.getMetadata_json().get("serviceType"));
							}
							snResp.setTagList(getTagList(serviceTagMap));
						}
					}
				}
			}
		}
		
		for(DepartmentResponse depResp: departmentResponseList) {
			for(ProductResponse prdResp: depResp.getProductList()) {
				for(DeploymentEnvironmentResponse depEnvResp: prdResp.getDeploymentEnvironmentList()) {
					for(ServiceCategoryResponse serCatResp: depEnvResp.getServiceCategoryList()) {
						for(ServiceNameResponse snResp: serCatResp.getServiceNameList()) {
							Map<String, String> leafQryMap = new HashMap<>();
							for(ServiceTagResponse tag: snResp.getTagList()) {
								leafQryMap.clear();
								leafQryMap.put("associatedDept", depResp.getName());
								leafQryMap.put("associatedProduct", prdResp.getName());
								leafQryMap.put("associatedEnv", depEnvResp.getName());
								leafQryMap.put("serviceNature", serCatResp.getName()); // common/business
								if("Common".equalsIgnoreCase(serCatResp.getName())) {
									leafQryMap.put("associatedCommonService", snResp.getName());
								}
								if("Business".equalsIgnoreCase(serCatResp.getName())) {
									leafQryMap.put("associatedBusinessService", snResp.getName());
								}
								leafQryMap.put("serviceType", tag.getTagName()); // app/data
								ServiceDetailReportResponse json = serviceDetailService.searchServiceDetailWithFilter(leafQryMap);
								for(ServiceDetail sd: json.getServices()) {
									if(depResp.getName().equals((String)sd.getMetadata_json().get("associatedDept"))
											&& prdResp.getName().equals((String)sd.getMetadata_json().get("associatedProduct"))
											&& depEnvResp.getName().equals((String)sd.getMetadata_json().get("associatedEnv"))
											&& serCatResp.getName().equals((String)sd.getMetadata_json().get("serviceNature"))
//											&& snResp.getName().equals(sd.getDetails().getAssociatedCommonService())
											&& tag.getTagName().equals((String)sd.getMetadata_json().get("serviceType"))) {
										ServiceTagLinkResponse serviceTagLinkResponse = ServiceTagLinkResponse.from(sd);
										serviceTagLinkResponse.setPerformance(PerformanceResponse.builder().score(RandomUtil.getRandom()).build());
										serviceTagLinkResponse.setAvailability(AvailabilityResponse.builder().score(RandomUtil.getRandom()).build());
										serviceTagLinkResponse.setSecurity(SecurityResponse.builder().score(RandomUtil.getRandom()).build());
										serviceTagLinkResponse.setDataProtection(DataProtectionResponse.builder().score(RandomUtil.getRandom()).build());
										serviceTagLinkResponse.setUserExperiance(UserExperianceResponse.builder().score(RandomUtil.getRandom()).build());
										serviceTagLinkResponse.setServiceBilling(ServiceBillingResponse.from(sd.getId(), RandomUtil.getRandom().doubleValue(), null));
										if(tag.getServiceList() == null) {
											List<ServiceTagLinkResponse> srvRespList = new ArrayList<>();
											srvRespList.add(serviceTagLinkResponse);
											tag.setServiceList(srvRespList);
										}else {
											tag.getServiceList().add(serviceTagLinkResponse);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
//		for(DepartmentResponse depResp: departmentResponseList) {
//			for(ProductResponse prdResp: depResp.getProductList()) {
//				for(DeploymentEnvironmentResponse deploymentEnvironmentResponse: prdResp.getDeploymentEnvironmentList()) {
//					
//					Map<String, String> leafQryMap = new HashMap<>();
//					//run it for common
//					for(ServiceCategoryResponse scResp: deploymentEnvironmentResponse.getServiceCategoryList()) {
//						if("Common".equalsIgnoreCase(scResp.getName())) {
//							filterServiceData(depResp, prdResp, deploymentEnvironmentResponse, leafQryMap, scResp);
//						}
//						if("Business".equalsIgnoreCase(scResp.getName())) {
//							filterServiceData(depResp, prdResp, deploymentEnvironmentResponse, leafQryMap, scResp);
//						}
//						
//					}
//					
//				}
//			}
//		}
		if(org == null) {
			org = OrganizationResponse.builder().build();
		}
		org.setTotalDepartment(departmentResponseList.size());
		org.setDepartmentList(departmentResponseList);
		return DepartmentWiseAnaliticResponse.builder().organization(org).build();
	}

//	private void populateServiceNameResponse(Map<String, String> serviceTagMap, ServiceCategoryResponse scResp,
//			ServiceNameResponse serviceNameResponse) {
//		if(serviceNameResponse.getTagList() == null) {
//			serviceNameResponse.setTagList(getTagList(serviceTagMap));
//		}
//		
//		if(scResp.getServiceNameList() == null) {
//			List<ServiceNameResponse> snRespList = new ArrayList<>();
//			snRespList.add(serviceNameResponse);
//			scResp.setServiceNameList(snRespList);
//		}else {
//			if(!scResp.getServiceNameList().contains(serviceNameResponse)) {
//				scResp.getServiceNameList().add(serviceNameResponse);
//			}
//		}
//	}

//	private void filterServiceData(DepartmentResponse depResp, ProductResponse prdResp,
//			DeploymentEnvironmentResponse deploymentEnvironmentResponse, Map<String, String> leafQryMap, ServiceCategoryResponse scResp) {
//		for(ServiceNameResponse serviceNameResponse: scResp.getServiceNameList()) {
//			// it runs fo app and data
//			for(ServiceTagResponse serviceTagResponse : serviceNameResponse.getTagList()) {
//				leafQryMap.clear();
//				leafQryMap.put("associatedDept", depResp.getName());
//				leafQryMap.put("associatedProduct", prdResp.getName());
//				leafQryMap.put("associatedEnv", deploymentEnvironmentResponse.getName());
//				leafQryMap.put("serviceNature", scResp.getName()); // common/business
//				leafQryMap.put("associatedCommonService", serviceNameResponse.getName());
//				leafQryMap.put("serviceType", serviceTagResponse.getTagName()); // app/data
//				ServiceDetailReportResponse appSrvList = serviceDetailService.searchAllServiceDetail(leafQryMap);
//				
//				if("App".equalsIgnoreCase(serviceTagResponse.getTagName())) {
//					createServiceList(depResp, prdResp, deploymentEnvironmentResponse, scResp, serviceNameResponse,
//							serviceTagResponse, appSrvList);
//					
//				}
//				if("Data".equalsIgnoreCase(serviceTagResponse.getTagName())) {
//					createServiceList(depResp, prdResp, deploymentEnvironmentResponse, scResp, serviceNameResponse,
//							serviceTagResponse, appSrvList);
//					
//				}
//			}
//		}
//	}

//	private void createServiceList(DepartmentResponse depResp, ProductResponse prdResp,
//			DeploymentEnvironmentResponse deploymentEnvironmentResponse, ServiceCategoryResponse scResp,
//			ServiceNameResponse serviceNameResponse, ServiceTagResponse serviceTagResponse,
//			ServiceDetailReportResponse appSrvList) {
//		for(ServiceDetail commonSd: appSrvList.getServices()) {
//			if(serviceNameResponse.getName().equals(commonSd.getDetails().getAssociatedCommonService())
//					&& depResp.getName().equals(commonSd.getDetails().getAssociatedDept())
//					&& prdResp.getName().equals(commonSd.getDetails().getAssociatedProduct())
//					&& deploymentEnvironmentResponse.getName().equals(commonSd.getDetails().getAssociatedEnv())
//					&& scResp.getName().equals(commonSd.getDetails().getServiceNature())
//					&& serviceTagResponse.getTagName().equals(commonSd.getDetails().getServiceType())) {
//				ServiceTagLinkResponse serviceTagLinkResponse = ServiceTagLinkResponse.from(commonSd);
//				serviceTagLinkResponse.setPerformance(PerformanceResponse.builder().score(RandomUtil.getRandom()).build());
//				serviceTagLinkResponse.setAvailability(AvailabilityResponse.builder().score(RandomUtil.getRandom()).build());
//				serviceTagLinkResponse.setSecurity(SecurityResponse.builder().score(RandomUtil.getRandom()).build());
//				serviceTagLinkResponse.setDataProtection(DataProtectionResponse.builder().score(RandomUtil.getRandom()).build());
//				serviceTagLinkResponse.setUserExperiance(UserExperianceResponse.builder().score(RandomUtil.getRandom()).build());
//				serviceTagLinkResponse.setServiceBilling(ServiceBillingResponse.from(commonSd.getId(), RandomUtil.getRandom().doubleValue(), null));
//				if(serviceTagResponse.getServiceList() == null) {
//					List<ServiceTagLinkResponse> srvRespList = new ArrayList<>();
//					srvRespList.add(serviceTagLinkResponse);
//					serviceTagResponse.setServiceList(srvRespList);
//				}else {
//					serviceTagResponse.getServiceList().add(serviceTagLinkResponse);
//				}
//			}
//			
//		}
//	}

	private List<ServiceTagResponse> getTagList(Map<String, String>serviceTagMap) {
		List<ServiceTagResponse> srvTagRespList = new ArrayList<>();
		for(Map.Entry<String, String> srvTagEntry: serviceTagMap.entrySet()) {
			ServiceTagResponse str = ServiceTagResponse.from(null, srvTagEntry.getKey(), null);
			srvTagRespList.add(str);
		}
		return srvTagRespList;
	}
}
