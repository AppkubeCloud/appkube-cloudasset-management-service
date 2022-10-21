package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.AccountServices;
import com.synectiks.asset.domain.Catalogue;
import com.synectiks.asset.domain.Dashboard;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.domain.ServiceProviderCloudAccount;
import com.synectiks.asset.repository.ServiceDetailRepository;
import com.synectiks.asset.response.AccountTree;
import com.synectiks.asset.response.App;
import com.synectiks.asset.response.AvailabilityResponse;
import com.synectiks.asset.response.BusinessService;
import com.synectiks.asset.response.Cluster;
import com.synectiks.asset.response.CommonService;
import com.synectiks.asset.response.Data;
import com.synectiks.asset.response.DataProtectionResponse;
import com.synectiks.asset.response.Environment;
import com.synectiks.asset.response.PerformanceResponse;
import com.synectiks.asset.response.Product;
import com.synectiks.asset.response.SecurityResponse;
import com.synectiks.asset.response.ServiceDetailReportResponse;
import com.synectiks.asset.response.UserExperianceResponse;
import com.synectiks.asset.response.Vpc;
import com.synectiks.asset.response.catalogue.CloudDashboard;
import com.synectiks.asset.util.JacsonNodeAndMapConvertUtil;
import com.synectiks.asset.util.RandomUtil;
import com.synectiks.asset.util.Utils;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ServiceDetailService {

	private static final Logger logger = LoggerFactory.getLogger(ServiceDetailService.class);

	@Autowired
	private ServiceDetailRepository serviceDetailJsonRepository;

	@Autowired
	private AccountServicesService accountServicesService;

	@Autowired
	private CatalogueService catalogueService;

	@Autowired
	private AwsService awsService;

	@Autowired
	private ServiceProviderCloudAccountService serviceProviderCloudAccountService;

	@Autowired
	private ProxyGrafanaApiService proxyGrafanaApiService;

	@Autowired
	private JacsonNodeAndMapConvertUtil jacsonNodeAndMapConvertUtil;

	public Optional<ServiceDetail> getServiceDetail(Long id) {
		logger.info("Get service detail by id: {}", id);
		return serviceDetailJsonRepository.findById(id);
	}

	public List<ServiceDetail> getAllServiceDetail() {
		logger.info("Get all service detail");
		return serviceDetailJsonRepository.findAll(Sort.by(Direction.DESC, "id"));
	}

	public Optional<ServiceDetail> deleteServiceDetail(Long id) {
		logger.info("Delete service detail by id: {}", id);
		Optional<ServiceDetail> oObj = getServiceDetail(id);
		if (!oObj.isPresent()) {
			logger.warn("Id {} not found. Service detail cannot be deleted", id);
			return oObj;
		}
		serviceDetailJsonRepository.deleteById(id);
		transformServiceDetailsListToTree();
		return oObj;
	}

	public ServiceDetail createServiceDetail(ServiceDetail obj) {
		logger.info("Create new service detail");
		ServiceDetail sd = serviceDetailJsonRepository.save(obj);
//		transformServiceDetailsListToTree();
		return sd;
	}

	public ServiceDetail updateServiceDetail(ServiceDetail obj) {
		logger.info("Update service detail. Id: {}", obj.getId());
		if (!serviceDetailJsonRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceDetail", "idnotfound");
		}
		ServiceDetail sd = serviceDetailJsonRepository.save(obj);
//		transformServiceDetailsListToTree();
		return sd;
	}

	public ServiceDetail updateServiceDetailSlaJson(ServiceDetail obj) {
		logger.info("Update service detail sla_json. Id: {}", obj.getId());
		if (!serviceDetailJsonRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceDetail", "idnotfound");
		}
		ServiceDetail sd = serviceDetailJsonRepository.save(obj);
		return sd;
	}

	public Optional<ServiceDetail> partialUpdateServiceDetail(ServiceDetail obj) {
		logger.info("Update service detail partialy. Id: {}", obj.getId());
		if (!serviceDetailJsonRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceDetail", "idnotfound");
		}
		Optional<ServiceDetail> result = serviceDetailJsonRepository.findById(obj.getId()).map(existingObj -> {
			return existingObj;
		}).map(serviceDetailJsonRepository::save);
		transformServiceDetailsListToTree();
		return result;
	}

	public void createBulkData(ObjectNode objNode) throws IOException {
		JsonNode objArray = objNode.get("services");
		for (JsonNode node : objArray) {
			ServiceDetail sd = ServiceDetail.builder()
					.metadata_json(jacsonNodeAndMapConvertUtil.convertJsonNodeToMap(node)).build();
			createServiceDetail(sd);
		}
		transformServiceDetailsListToTree();
	}

	public ServiceDetailReportResponse searchServiceDetailWithFilter(Map<String, String> obj) {
		logger.info("Search service detail with filter");
		ServiceDetailReportResponse resp = ServiceDetailReportResponse.builder().build();

		List<ServiceDetail> list = serviceDetailJsonRepository.findAll();

		if (obj.size() == 0) {
			resp.setServices(list);
			resp.setTotal(list.size());
			return resp;
		}

		List<ServiceDetail> list2 = list;
		if (obj.containsKey("name")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("name")).equals(obj.get("name")))
					.collect(Collectors.toList());
		}
		if (obj.containsKey("description")) {
			list2 = list2.stream()
					.filter(sd -> ((String) sd.getMetadata_json().get("description")).equals(obj.get("description")))
					.collect(Collectors.toList());
		}
		if (obj.containsKey("serviceType")) {
			list2 = list2.stream().filter(
					sd -> ((String) sd.getMetadata_json().get("serviceType")).equalsIgnoreCase(obj.get("serviceType")))
					.collect(Collectors.toList());
		}
		if (obj.containsKey("associatedOU")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedOU"))
					.equalsIgnoreCase(obj.get("associatedOU"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedEnv")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedEnv"))
					.equalsIgnoreCase(obj.get("associatedEnv"))).collect(Collectors.toList());
		}
		if (obj.containsKey("serviceNature")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("serviceNature"))
					.equalsIgnoreCase(obj.get("serviceNature"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedDept")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedDept"))
					.equalsIgnoreCase(obj.get("associatedDept"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedCluster")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedCluster"))
					.equalsIgnoreCase(obj.get("associatedCluster"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedProduct")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedProduct"))
					.equalsIgnoreCase(obj.get("associatedProduct"))).collect(Collectors.toList());
		}
		if (obj.containsKey("serviceHostingType")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("serviceHostingType"))
					.equalsIgnoreCase(obj.get("serviceHostingType"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedLandingZone")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedLandingZone"))
					.equalsIgnoreCase(obj.get("associatedLandingZone"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedCommonService")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedCommonService"))
					.equalsIgnoreCase(obj.get("associatedCommonService"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedCloudElementId")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedCloudElementId"))
					.equals(obj.get("associatedCloudElementId"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedProductEnclave")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedProductEnclave"))
					.equalsIgnoreCase(obj.get("associatedProductEnclave"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedBusinessService")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedBusinessService"))
					.equalsIgnoreCase(obj.get("associatedBusinessService"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedClusterNamespace")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedClusterNamespace"))
					.equalsIgnoreCase(obj.get("associatedClusterNamespace"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedGlobalServiceLocation")) {
			list2 = list2.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedGlobalServiceLocation"))
					.equalsIgnoreCase(obj.get("associatedGlobalServiceLocation"))).collect(Collectors.toList());
		}
		if (obj.containsKey("associatedManagedCloudServiceLocation")) {
			list2 = list2.stream()
					.filter(sd -> ((String) sd.getMetadata_json().get("associatedManagedCloudServiceLocation"))
							.equalsIgnoreCase(obj.get("associatedManagedCloudServiceLocation")))
					.collect(Collectors.toList());
		}
		resp.setServices(list2);
		resp.setTotal(list2.size());
		return resp;
	}

	@Transactional
	public List<AccountTree> transformServiceDetailsListToTree() {
		logger.info("Transforming service details to account specific tree");
		List<ServiceDetail> listSd = getAllServiceDetail();
		Map<String, List<ServiceDetail>> acMap = filterAccountSpecificList(listSd);
		List<AccountTree> treeList = filterVpcs(acMap);
		filterClusters(acMap, treeList);
		filterProducts(acMap, treeList);
		filterEnvironments(acMap, treeList);
		filterServiceNature(acMap, treeList);
		filterAppAndDataServices(acMap, treeList);
		logger.info("Service detail transformation completed. Now updating account services");
		logger.debug("Cleaning up account services");
		for (AccountServices as : accountServicesService.getAllAccountServices()) {
			accountServicesService.deleteAccountServices(as.getId());
		}
		logger.debug("Account service clean up completed. Now updating account services");
		Gson gson = new Gson();
		for (AccountTree at : treeList) {
			AccountServices as = AccountServices.builder().accountId(at.getAccount()).build();
			Map<String, Object> attributes = gson.fromJson(gson.toJson(at), Map.class);
			as.setAccount_services_json(attributes);
			accountServicesService.createAccountServices(as);
		}
		logger.info("Account services updated completed");
		return treeList;
	}

	private void filterAppAndDataServices(Map<String, List<ServiceDetail>> acMap, List<AccountTree> treeList) {
		for (AccountTree account : treeList) {
			for (Vpc vpc : account.getVpcs()) {
				for (Cluster cluster : vpc.getClusters()) {
					for (Product product : cluster.getProducts()) {
						for (Environment environment : product.getEnvironments()) {
							if (environment.getServices() != null) {
								com.synectiks.asset.response.Service service = environment.getServices();

								if (service.getBusiness() != null) {
									for (BusinessService bs : service.getBusiness()) {
										List<App> appList = new ArrayList<>();
										List<Data> dataList = new ArrayList<>();
										
										bs.setApp(appList);
										bs.setData(dataList);

										Map<String, String> obj = new HashMap<>();
										obj.put("associatedLandingZone", account.getAccount());
										obj.put("associatedProductEnclave", account.getAccount()+"-"+vpc.getName());
										if(cluster.getName().toLowerCase().startsWith("cluster".toLowerCase())) {
											obj.put("serviceHostingType", "Cluster");
										}else {
											obj.put("serviceHostingType", cluster.getName());
										}
										obj.put("associatedProduct", product.getName());
										obj.put("associatedEnv", environment.getName());
										obj.put("serviceNature", "Business");
										obj.put("associatedBusinessService", bs.getName());
										obj.put("serviceType", "App");
										ServiceDetailReportResponse resp = searchServiceDetailWithFilter(obj);
										if(resp.getServices() != null && resp.getServices().size() > 0) {
											for(ServiceDetail sdObj: resp.getServices()) {
												App app = buildApp(sdObj, environment.getName());
												bs.getApp().add(app);
											}
										}
										obj.put("serviceType", "Data");
										resp = searchServiceDetailWithFilter(obj);
										if(resp.getServices() != null && resp.getServices().size() > 0) {
											for(ServiceDetail sdObj: resp.getServices()) {
												Data data = buildData(sdObj, environment.getName());
												bs.getData().add(data);
											}
										}
									}
								}
								
								if (service.getCommon() != null) {
									for (CommonService cs : service.getCommon()) {
											List<App> appList = new ArrayList<>();
											List<Data> dataList = new ArrayList<>();
										
											cs.setApp(appList);
											cs.setData(dataList);
											
											Map<String, String> obj = new HashMap<>();
											obj.put("associatedLandingZone", account.getAccount());
											obj.put("associatedProductEnclave", account.getAccount()+"-"+vpc.getName());
											if(cluster.getName().toLowerCase().startsWith("cluster".toLowerCase())) {
												obj.put("serviceHostingType", "Cluster");
											}else {
												obj.put("serviceHostingType", cluster.getName());
											}
											obj.put("associatedProduct", product.getName());
											obj.put("associatedEnv", environment.getName());
											obj.put("serviceNature", "Common");
											obj.put("associatedCommonService", cs.getName());
											obj.put("serviceType", "App");
											ServiceDetailReportResponse resp = searchServiceDetailWithFilter(obj);
											if(resp.getServices() != null && resp.getServices().size() > 0) {
												for(ServiceDetail sdObj: resp.getServices()) {
													App app = buildApp(sdObj, environment.getName());
													cs.getApp().add(app);
												}
											}
											obj.put("serviceType", "Data");
											resp = searchServiceDetailWithFilter(obj);
											if(resp.getServices() != null && resp.getServices().size() > 0) {
												for(ServiceDetail sdObj: resp.getServices()) {
													Data data = buildData(sdObj, environment.getName());
													cs.getData().add(data);
												}
											}
										}
									}
								}

							}
						}
					}
				}
			}
		}
	

	private Data buildData(ServiceDetail sd, String envName) {
		Data data = Data.builder().id(envName + "_" + (String) sd.getMetadata_json().get("name")).dbid(sd.getId())
				.name((String) sd.getMetadata_json().get("name")).serviceDetailId(sd.getId())
				.description((String) sd.getMetadata_json().get("description"))
				.associatedCloudElement((String) sd.getMetadata_json().get("associatedCloudElement"))
				.associatedClusterNamespace((String) sd.getMetadata_json().get("associatedClusterNamespace"))
				.associatedManagedCloudServiceLocation(
						(String) sd.getMetadata_json().get("associatedManagedCloudServiceLocation"))
				.associatedGlobalServiceLocation((String) sd.getMetadata_json().get("associatedGlobalServiceLocation"))
				.serviceHostingType((String) sd.getMetadata_json().get("serviceHostingType"))
				.associatedCloudElementId((String) sd.getMetadata_json().get("associatedCloudElementId"))
				.associatedOU((String) sd.getMetadata_json().get("associatedOU"))
				.associatedDept((String) sd.getMetadata_json().get("associatedDept"))
				.associatedProduct((String) sd.getMetadata_json().get("associatedProduct"))
				.associatedEnv((String) sd.getMetadata_json().get("associatedEnv"))
				.serviceType((String) sd.getMetadata_json().get("serviceType"))

				.performance(PerformanceResponse.builder()
						.score(sd.getMetadata_json().get("performance") != null
								? (Integer) ((Map) sd.getMetadata_json().get("performance")).get("score")
								: 0)
						.build())
				.availability(AvailabilityResponse.builder()
						.score(sd.getMetadata_json().get("availability") != null
								? (Integer) ((Map) sd.getMetadata_json().get("availability")).get("score")
								: 0)
						.build())
				.security(SecurityResponse.builder()
						.score(sd.getMetadata_json().get("security") != null
								? (Integer) ((Map) sd.getMetadata_json().get("security")).get("score")
								: 0)
						.build())
				.dataProtection(DataProtectionResponse.builder()
						.score(sd.getMetadata_json().get("dataProtection") != null
								? (Integer) ((Map) sd.getMetadata_json().get("dataProtection")).get("score")
								: 0)
						.build())
				.userExperiance(UserExperianceResponse.builder()
						.score(sd.getMetadata_json().get("userExperiance") != null
								? (Integer) ((Map) sd.getMetadata_json().get("userExperiance")).get("score")
								: 0)
						.build())

				.build();
		return data;
	}

	private App buildApp(ServiceDetail sd, String envName) {
		App app = App.builder().id(envName + "_" + (String) sd.getMetadata_json().get("name")).dbid(sd.getId())
				.name((String) sd.getMetadata_json().get("name")).serviceDetailId(sd.getId())
				.description((String) sd.getMetadata_json().get("description"))
				.associatedCloudElement((String) sd.getMetadata_json().get("associatedCloudElement"))
				.associatedClusterNamespace((String) sd.getMetadata_json().get("associatedClusterNamespace"))
				.associatedManagedCloudServiceLocation(
						(String) sd.getMetadata_json().get("associatedManagedCloudServiceLocation"))
				.associatedGlobalServiceLocation((String) sd.getMetadata_json().get("associatedGlobalServiceLocation"))
				.serviceHostingType((String) sd.getMetadata_json().get("serviceHostingType"))
				.associatedCloudElementId((String) sd.getMetadata_json().get("associatedCloudElementId"))

				.associatedOU((String) sd.getMetadata_json().get("associatedOU"))
				.associatedDept((String) sd.getMetadata_json().get("associatedDept"))
				.associatedProduct((String) sd.getMetadata_json().get("associatedProduct"))
				.associatedEnv((String) sd.getMetadata_json().get("associatedEnv"))
				.serviceType((String) sd.getMetadata_json().get("serviceType"))
				.performance(PerformanceResponse.builder()
						.score(sd.getMetadata_json().get("performance") != null
								? (Integer) ((Map) sd.getMetadata_json().get("performance")).get("score")
								: 0)
						.build())
				.availability(AvailabilityResponse.builder()
						.score(sd.getMetadata_json().get("availability") != null
								? (Integer) ((Map) sd.getMetadata_json().get("availability")).get("score")
								: 0)
						.build())
				.security(SecurityResponse.builder()
						.score(sd.getMetadata_json().get("security") != null
								? (Integer) ((Map) sd.getMetadata_json().get("security")).get("score")
								: 0)
						.build())
				.dataProtection(DataProtectionResponse.builder()
						.score(sd.getMetadata_json().get("dataProtection") != null
								? (Integer) ((Map) sd.getMetadata_json().get("dataProtection")).get("score")
								: 0)
						.build())
				.userExperiance(UserExperianceResponse.builder()
						.score(sd.getMetadata_json().get("userExperiance") != null
								? (Integer) ((Map) sd.getMetadata_json().get("userExperiance")).get("score")
								: 0)
						.build())

				.build();
		return app;
	}

	private void filterServiceNature(Map<String, List<ServiceDetail>> acMap, List<AccountTree> treeList) {
		for (AccountTree account : treeList) {
			for (Vpc vpc : account.getVpcs()) {
				for (Cluster cluster : vpc.getClusters()) {
					for (Product product : cluster.getProducts()) {
						for (Environment environment : product.getEnvironments()) {
							com.synectiks.asset.response.Service service = com.synectiks.asset.response.Service
									.builder().build();
							List<BusinessService> businessServiceList = new ArrayList<>();
							List<CommonService> commonServiceList = new ArrayList<>();
							service.setBusiness(businessServiceList);
							service.setCommon(commonServiceList);
							Map<String, BusinessService> bsMap = new HashMap();
							Map<String, CommonService> csMap = new HashMap();

							for (Map.Entry<String, List<ServiceDetail>> entry : acMap.entrySet()) {
								if (entry.getKey().equals(account.getAccount())) {
									for (ServiceDetail sd : entry.getValue()) {
										String vpcName = (String) sd.getMetadata_json().get("associatedProductEnclave");
										String clusterName = (String) sd.getMetadata_json().get("associatedCluster");
										String productName = (String) sd.getMetadata_json().get("associatedProduct");
										String envName = (String) sd.getMetadata_json().get("associatedEnv");
										if (!StringUtils.isBlank(vpcName)) {
											
											if("Cluster".equalsIgnoreCase((String) sd.getMetadata_json().get("serviceHostingType"))) {
												if (vpcName.substring(vpcName.indexOf("-") + 1)
														.equalsIgnoreCase(vpc.getName())
														&& clusterName.substring(clusterName.lastIndexOf("-") + 1)
																.equalsIgnoreCase(cluster.getName())
														&& productName.equalsIgnoreCase(product.getName())
														&& envName.equalsIgnoreCase(environment.getName())) {
													String serviceNature = (String) sd.getMetadata_json()
															.get("serviceNature");
													if (serviceNature.equalsIgnoreCase("Business")) {
														String associatedBusinessService = (String) sd.getMetadata_json()
																.get("associatedBusinessService");
//														String description = (String)sd.getMetadata_json().get("description");
														String associatedOU = (String) sd.getMetadata_json()
																.get("associatedOU");
														String associatedDept = (String) sd.getMetadata_json()
																.get("associatedDept");

														BusinessService bs = BusinessService.builder()
																.name(associatedBusinessService)
//																.description(description)
																.associatedOU(associatedOU).associatedDept(associatedDept)
																.build();
//														service.getBusiness().add(bs);
														bsMap.put(associatedBusinessService, bs);
													} else if (serviceNature.equalsIgnoreCase("Common")) {
														String associatedCommonService = (String) sd.getMetadata_json()
																.get("associatedCommonService");
//														String description = (String)sd.getMetadata_json().get("description");
														String associatedOU = (String) sd.getMetadata_json()
																.get("associatedOU");
														String associatedDept = (String) sd.getMetadata_json()
																.get("associatedDept");

														CommonService cs = CommonService.builder()
																.name(associatedCommonService)
//																.description(description)
																.associatedOU(associatedOU).associatedDept(associatedDept)
																.build();
//														service.getCommon().add(cs);
														csMap.put(associatedCommonService, cs);
													}
												}
											}else {
												if (vpcName.substring(vpcName.indexOf("-") + 1)
														.equalsIgnoreCase(vpc.getName())
														&& productName.equalsIgnoreCase(product.getName())
														&& envName.equalsIgnoreCase(environment.getName())) {
													String serviceNature = (String) sd.getMetadata_json()
															.get("serviceNature");
													if (serviceNature.equalsIgnoreCase("Business")) {
														String associatedBusinessService = (String) sd.getMetadata_json()
																.get("associatedBusinessService");
//														String description = (String)sd.getMetadata_json().get("description");
														String associatedOU = (String) sd.getMetadata_json()
																.get("associatedOU");
														String associatedDept = (String) sd.getMetadata_json()
																.get("associatedDept");

														BusinessService bs = BusinessService.builder()
																.name(associatedBusinessService)
//																.description(description)
																.associatedOU(associatedOU).associatedDept(associatedDept)
																.build();
//														service.getBusiness().add(bs);
														bsMap.put(associatedBusinessService, bs);
													} else if (serviceNature.equalsIgnoreCase("Common")) {
														String associatedCommonService = (String) sd.getMetadata_json()
																.get("associatedCommonService");
//														String description = (String)sd.getMetadata_json().get("description");
														String associatedOU = (String) sd.getMetadata_json()
																.get("associatedOU");
														String associatedDept = (String) sd.getMetadata_json()
																.get("associatedDept");

														CommonService cs = CommonService.builder()
																.name(associatedCommonService)
//																.description(description)
																.associatedOU(associatedOU).associatedDept(associatedDept)
																.build();
//														service.getCommon().add(cs);
														csMap.put(associatedCommonService, cs);
													}
												}
											}
											
											
//											if (vpcName.substring(vpcName.indexOf("-") + 1)
//													.equalsIgnoreCase(vpc.getName())
//													&& clusterName.substring(clusterName.lastIndexOf("-") + 1)
//															.equalsIgnoreCase(cluster.getName())
//													&& productName.equalsIgnoreCase(product.getName())
//													&& envName.equalsIgnoreCase(environment.getName())) {
//
//												String serviceNature = (String) sd.getMetadata_json()
//														.get("serviceNature");
//												if (serviceNature.equalsIgnoreCase("Business")) {
//													String associatedBusinessService = (String) sd.getMetadata_json()
//															.get("associatedBusinessService");
////													String description = (String)sd.getMetadata_json().get("description");
//													String associatedOU = (String) sd.getMetadata_json()
//															.get("associatedOU");
//													String associatedDept = (String) sd.getMetadata_json()
//															.get("associatedDept");
//
//													BusinessService bs = BusinessService.builder()
//															.name(associatedBusinessService)
////															.description(description)
//															.associatedOU(associatedOU).associatedDept(associatedDept)
//															.build();
////													service.getBusiness().add(bs);
//													bsMap.put(associatedBusinessService, bs);
//												} else if (serviceNature.equalsIgnoreCase("Common")) {
//													String associatedCommonService = (String) sd.getMetadata_json()
//															.get("associatedCommonService");
////													String description = (String)sd.getMetadata_json().get("description");
//													String associatedOU = (String) sd.getMetadata_json()
//															.get("associatedOU");
//													String associatedDept = (String) sd.getMetadata_json()
//															.get("associatedDept");
//
//													CommonService cs = CommonService.builder()
//															.name(associatedCommonService)
////															.description(description)
//															.associatedOU(associatedOU).associatedDept(associatedDept)
//															.build();
////													service.getCommon().add(cs);
//													csMap.put(associatedCommonService, cs);
//												}
//
//											}
										}
									}
								}
							}
							for (Map.Entry<String, BusinessService> entry : bsMap.entrySet()) {
								service.getBusiness().add(entry.getValue());
							}
							for (Map.Entry<String, CommonService> entry : csMap.entrySet()) {
								service.getCommon().add(entry.getValue());
							}
							environment.setServices(service);
						}
					}
				}
			}
		}
	}

	private void filterEnvironments(Map<String, List<ServiceDetail>> acMap, List<AccountTree> treeList) {
		for (AccountTree account : treeList) {
			for (Vpc vpc : account.getVpcs()) {
				for (Cluster cluster : vpc.getClusters()) {
					for (Product product : cluster.getProducts()) {
						List<Environment> environmentList = new ArrayList<>();
						for (Map.Entry<String, List<ServiceDetail>> entry : acMap.entrySet()) {
							if (entry.getKey().equals(account.getAccount())) {
								for (ServiceDetail sd : entry.getValue()) {
									String vpcName = (String) sd.getMetadata_json().get("associatedProductEnclave");
									String clusterName = (String) sd.getMetadata_json().get("associatedCluster");
									String productName = (String) sd.getMetadata_json().get("associatedProduct");
									if (!StringUtils.isBlank(vpcName)) {
										if("Cluster".equalsIgnoreCase((String) sd.getMetadata_json().get("serviceHostingType"))) {
											if (vpcName.substring(vpcName.indexOf("-") + 1).equalsIgnoreCase(vpc.getName())
													&& clusterName.substring(clusterName.lastIndexOf("-") + 1)
															.equalsIgnoreCase(cluster.getName())
													&& productName.equalsIgnoreCase(product.getName())) {
												String envName = (String) sd.getMetadata_json().get("associatedEnv");
												Environment env = Environment.builder().name(envName).build();
												if (!environmentList.contains(env)) {
													environmentList.add(env);
												}
											}
										}else {
											if (vpcName.substring(vpcName.indexOf("-") + 1).equalsIgnoreCase(vpc.getName())
													&& productName.equalsIgnoreCase(product.getName())) {
												String envName = (String) sd.getMetadata_json().get("associatedEnv");
												Environment env = Environment.builder().name(envName).build();
												if (!environmentList.contains(env)) {
													environmentList.add(env);
												}
											}
											
										}
										
//										if (vpcName.substring(vpcName.indexOf("-") + 1).equalsIgnoreCase(vpc.getName())
//												&& clusterName.substring(clusterName.lastIndexOf("-") + 1)
//														.equalsIgnoreCase(cluster.getName())
//												&& productName.equalsIgnoreCase(product.getName())) {
//											String envName = (String) sd.getMetadata_json().get("associatedEnv");
//											Environment env = Environment.builder().name(envName).build();
//											if (!environmentList.contains(env)) {
//												environmentList.add(env);
//											}
//										}
									}
								}
							}
						}
						product.setEnvironments(environmentList);
					}
				}
			}
		}
	}

	private void filterProducts(Map<String, List<ServiceDetail>> acMap, List<AccountTree> treeList) {
		for (AccountTree account : treeList) {
			for (Vpc vpc : account.getVpcs()) {
				for (Cluster cluster : vpc.getClusters()) {
					List<Product> productList = new ArrayList<>();
					for (Map.Entry<String, List<ServiceDetail>> entry : acMap.entrySet()) {
						if (entry.getKey().equals(account.getAccount())) {
							for (ServiceDetail sd : entry.getValue()) {
								String vpcName = (String) sd.getMetadata_json().get("associatedProductEnclave");
								String clusterName = (String) sd.getMetadata_json().get("associatedCluster");
								
								if (!StringUtils.isBlank(vpcName)) {
									if("Cluster".equalsIgnoreCase((String) sd.getMetadata_json().get("serviceHostingType"))) {
										if (vpcName.substring(vpcName.indexOf("-") + 1).equalsIgnoreCase(vpc.getName())
												&& clusterName.substring(clusterName.lastIndexOf("-") + 1)
														.equalsIgnoreCase(cluster.getName())) {
											String productName = (String) sd.getMetadata_json().get("associatedProduct");
											Product product = Product.builder().name(productName).build();
											if (!productList.contains(product)) {
												productList.add(product);
											}
										}
									}else {
										if (vpcName.substring(vpcName.indexOf("-") + 1).equalsIgnoreCase(vpc.getName())) {
											String productName = (String) sd.getMetadata_json().get("associatedProduct");
											Product product = Product.builder().name(productName).build();
											if (!productList.contains(product)) {
												productList.add(product);
											}
										}
										
									}
								}
								
								
//								if (!StringUtils.isBlank(vpcName)) {
//									if (vpcName.substring(vpcName.indexOf("-") + 1).equalsIgnoreCase(vpc.getName())
//											&& clusterName.substring(clusterName.lastIndexOf("-") + 1)
//													.equalsIgnoreCase(cluster.getName())) {
//										String productName = (String) sd.getMetadata_json().get("associatedProduct");
//										Product product = Product.builder().name(productName).build();
//										if (!productList.contains(product)) {
//											productList.add(product);
//										}
//									}
//								}
							}
						}
					}
					cluster.setProducts(productList);
				}
			}
		}
	}

	private void filterClusters(Map<String, List<ServiceDetail>> acMap, List<AccountTree> treeList) {
		for (Map.Entry<String, List<ServiceDetail>> entry : acMap.entrySet()) {
			for (AccountTree at : treeList) {
				if (entry.getKey().equals(at.getAccount())) {
					for (Vpc vpc : at.getVpcs()) {
						List<Cluster> clusterList = new ArrayList<>();
						for (ServiceDetail sd : entry.getValue()) {
							String vpcName = (String) sd.getMetadata_json().get("associatedProductEnclave");
							if (!StringUtils.isBlank(vpcName)) {
								if("Cluster".equalsIgnoreCase((String) sd.getMetadata_json().get("serviceHostingType"))) {
									if (vpcName.substring(vpcName.indexOf("-") + 1).equalsIgnoreCase(vpc.getName())) {
										String clusterName = (String) sd.getMetadata_json().get("associatedCluster");
										Cluster cl = Cluster.builder().name(clusterName.substring(clusterName.lastIndexOf("-") + 1)).build();
										if (!clusterList.contains(cl)) {
											clusterList.add(cl);
										}
									}
								}else {
									String clusterName = (String) sd.getMetadata_json().get("serviceHostingType");
									Cluster cl = Cluster.builder().name(clusterName).build();
									if (!clusterList.contains(cl)) {
										clusterList.add(cl);
									}
								}
								
							}
						}
						vpc.setClusters(clusterList);
					}
				}
			}
		}
	}

	private List<AccountTree> filterVpcs(Map<String, List<ServiceDetail>> acMap) {
		List<AccountTree> treeList = new ArrayList<>();
		for (Map.Entry<String, List<ServiceDetail>> entry : acMap.entrySet()) {
			AccountTree tree = new AccountTree();
			tree.setAccount(entry.getKey());
			List<Vpc> vpcList = new ArrayList<>();
			for (ServiceDetail vpc : entry.getValue()) {
				String name = (String) vpc.getMetadata_json().get("associatedProductEnclave");
				if (!StringUtils.isBlank(name)) {
					Vpc v = new Vpc();
					v.setName(name.substring(name.indexOf("-") + 1));
					if (!vpcList.contains(v)) {
						vpcList.add(v);
					}
				}
			}
			tree.setVpcs(vpcList);
			treeList.add(tree);
		}
		return treeList;
	}

	private Map<String, List<ServiceDetail>> filterAccountSpecificList(List<ServiceDetail> listSd) {
		Map<String, List<ServiceDetail>> acMap = new HashMap<>();
		for (ServiceDetail sd : listSd) {
			if (acMap.containsKey((String) sd.getMetadata_json().get("associatedLandingZone"))) {
				acMap.get((String) sd.getMetadata_json().get("associatedLandingZone")).add(sd);
			} else {
				List<ServiceDetail> list = new ArrayList<>();
				list.add(sd);
				acMap.put((String) sd.getMetadata_json().get("associatedLandingZone"), list);
			}
		}
		return acMap;
	}

	public void enableMonitoring(Map<String, String> obj) throws Exception {
		logger.info("Start auto deployment of dashboards for each service");
		Map<String, List<ServiceDetail>> acMap = filterAccountSpecificList(
				searchServiceDetailWithFilter(obj).getServices());
		Map<String, String> criteriaMap = new HashMap<>();
		Map<String, String> cdCriteriaMap = new HashMap<>();

		ObjectMapper mapper = Constants.instantiateMapper();

		Map<String, String> searchMap = new HashMap<>();
		ServiceProviderCloudAccount spca = serviceProviderCloudAccountService
				.searchAllServiceProviderCloudAccount(searchMap).get(0);
		AmazonS3 s3Client = Utils.getAmazonS3Client(spca.getAccessKey(), spca.getSecretKey(), spca.getRegion());
		if (s3Client == null) {
			throw new BadRequestAlertException("AWS S3 client connection failed", "Dashboard",
					"aws.s3.connection.failed");
		}

		for (Map.Entry<String, List<ServiceDetail>> entry : acMap.entrySet()) {
			String accountId = entry.getKey();
			for (ServiceDetail sd : entry.getValue()) {
				
				ObjectNode viewJsonNode = mapper.createObjectNode();
				viewJsonNode.put("serviceId", String.valueOf(sd.getId()));
				viewJsonNode.put(Constants.PERFORMANCE, mapper.createArrayNode());
				viewJsonNode.put(Constants.AVAILABILITY, mapper.createArrayNode());
				viewJsonNode.put(Constants.RELIABILITY, mapper.createArrayNode());
				viewJsonNode.put(Constants.ENDUSAGE, mapper.createArrayNode());
				viewJsonNode.put(Constants.SECURITY, mapper.createArrayNode());
				viewJsonNode.put(Constants.COMPLIANCE, mapper.createArrayNode());
				viewJsonNode.put(Constants.ALERTS, mapper.createArrayNode());

				criteriaMap.clear();
				criteriaMap.put("associatedCloudElementType",
						(String) sd.getMetadata_json().get("associatedCloudElement"));
				Catalogue catalogue = catalogueService.searchCatalogue(criteriaMap);
				List<CloudDashboard> cloudElementSpeficCloudDashBoards = catalogue.getDetails().getOps()
						.getCloudDashBoards();

				for (CloudDashboard cd : cloudElementSpeficCloudDashBoards) {
					try {
						boolean isDashboardExists = false;
						JsonNode dsInstanceJson = proxyGrafanaApiService
								.getGrafanaDatasourceByAccountIdAndInputType(accountId, cd.getAssociatedDataSourceType());
						if (dsInstanceJson.isArray() && dsInstanceJson.isEmpty()) {
							logger.warn("Datasource not configured in grafana. Dashboard cannot be imported");
							continue;
						}
						// check if dashboard exists
						String serviceName = null;
						if(sd.getMetadata_json().get("serviceNature") != null && "Common".equalsIgnoreCase((String)sd.getMetadata_json().get("serviceNature"))) {
							serviceName = (String) sd.getMetadata_json().get("associatedCommonService");
						}else {
							serviceName = (String) sd.getMetadata_json().get("associatedBusinessService");
						}
						isDashboardExists = checkDashboardExists(sd, serviceName, accountId, cd.getAssociatedCloudElementType(), cd.getAssociatedSLAType(), mapper);
						
						if(isDashboardExists) {
							logger.warn("Dashboard already exists. Skipping import");
							continue;
						}
						cdCriteriaMap.clear();
						cdCriteriaMap.put("dataSourceName", cd.getAssociatedDataSourceType());
						cdCriteriaMap.put("associatedCloudElementType", cd.getAssociatedCloudElementType());
						cdCriteriaMap.put("associatedSLAType", cd.getAssociatedSLAType());
						cdCriteriaMap.put("jsonLocation", cd.getJsonLocation());
						cdCriteriaMap.put("associatedCloud", cd.getAssociatedCloud());
						cdCriteriaMap.put("accountId", accountId);
						cdCriteriaMap.put("associatedCloudElementId",
								(String) sd.getMetadata_json().get("associatedCloudElementId"));
	
						Dashboard dashboard = awsService.getDashboardFromAwsS3(cdCriteriaMap, s3Client, dsInstanceJson);
						ObjectNode dashboardNode = (ObjectNode) mapper.readTree(dashboard.getData());
						dashboardNode.put("id", 0);
						dashboardNode.put("uid", "");
						String slug = dashboard.getInputType() + "_" + dashboard.getElementType() + "_"
								+ RandomUtil.randomAlphabeticString();
						dashboardNode.put("slug", slug);
						dashboardNode.put("title", slug);
	
						ObjectNode dataJs = mapper.createObjectNode();
						dataJs.put("Dashboard", dashboardNode);
						dataJs.put("UserId", 0);
						dataJs.put("Overwrite", false);
						dataJs.put("Message", "");
						dataJs.put("OrgId", 1);
						dataJs.put("PluginId", "");
						dataJs.put("FolderId", 0);
						dataJs.put("IsFolder", false);
						dataJs.put("ServiceId", String.valueOf(sd.getId()));
	
						ObjectNode respNode = proxyGrafanaApiService.importDashboardInGrafana(dataJs);
						
	//					respNode.put("dashboardCatalogueId", cd.getId());
						respNode.put("accountId", accountId);
						respNode.put("cloudElement", cd.getAssociatedCloudElementType());
						respNode.put("cloudElementId", (String) sd.getMetadata_json().get("associatedCloudElementId"));
						respNode.put("url", respNode.get("url").asText());
						
						respNode.put("associatedOU", (String) sd.getMetadata_json().get("associatedOU"));
						respNode.put("associatedDept", (String) sd.getMetadata_json().get("associatedDept"));
						respNode.put("associatedProduct", (String) sd.getMetadata_json().get("associatedProduct"));
						respNode.put("associatedEnv", (String) sd.getMetadata_json().get("associatedEnv"));
						respNode.put("serviceType", (String) sd.getMetadata_json().get("serviceType"));
						respNode.put("serviceNature", (String) sd.getMetadata_json().get("serviceNature"));
						if(sd.getMetadata_json().get("serviceNature") != null && "Common".equalsIgnoreCase((String)sd.getMetadata_json().get("serviceNature"))) {
							respNode.put("serviceName", (String) sd.getMetadata_json().get("associatedCommonService"));
						}else {
							respNode.put("serviceName", (String) sd.getMetadata_json().get("associatedBusinessService"));
						}
						respNode.put("serviceInstance", (String) sd.getMetadata_json().get("name"));
						
						ArrayNode jnArray = (ArrayNode) viewJsonNode.get(cd.getAssociatedSLAType().toLowerCase());
						jnArray.add(respNode);
					}catch(Exception e) {
						logger.warn("Error : "+e.getMessage());
					}
				}

				// update array here
				updateViewJson(viewJsonNode);
				
			}
		}
	}

	private boolean checkDashboardExists(ServiceDetail sd, String serviceName, String accountId, 
			String cloudElement, String associatedSLAType, ObjectMapper mapper) {
		
		String associatedOU =(String) sd.getMetadata_json().get("associatedOU");
		String associatedDept =(String) sd.getMetadata_json().get("associatedDept");
		String associatedProduct = (String) sd.getMetadata_json().get("associatedProduct");
		String associatedEnv = (String) sd.getMetadata_json().get("associatedEnv");
		String serviceType = (String) sd.getMetadata_json().get("serviceType");
		String serviceNature = (String) sd.getMetadata_json().get("serviceNature");
		String serviceInstance = (String) sd.getMetadata_json().get("name");
		String cloudElementId = (String) sd.getMetadata_json().get("associatedCloudElementId");
		
		if(sd.getView_json() == null) {
			return false;
		}else if(sd.getView_json().get(associatedSLAType.toLowerCase()) == null) {
			return false;
		}else if(sd.getView_json().get(associatedSLAType.toLowerCase()) != null 
				&& ((List)sd.getView_json().get(associatedSLAType.toLowerCase())).isEmpty()) {
			return false;
		}
		if(mapper == null) {
			mapper = Constants.instantiateMapper();
		}
		List jsonNodeList = (List)sd.getView_json().get(associatedSLAType.toLowerCase());
		for (Object objNode : jsonNodeList) {
			Map node = (Map)objNode;
			if(associatedOU.equalsIgnoreCase((String)node.get("associatedOU"))
					&& associatedDept.equalsIgnoreCase((String)node.get("associatedDept"))
					&& associatedProduct.equalsIgnoreCase((String)node.get("associatedProduct"))
					&& associatedEnv.equalsIgnoreCase((String)node.get("associatedEnv"))
					&& serviceType.equalsIgnoreCase((String)node.get("serviceType"))
					&& serviceNature.equalsIgnoreCase((String)node.get("serviceNature"))
					&& serviceName.equalsIgnoreCase((String)node.get("serviceName"))
					&& serviceInstance.equalsIgnoreCase((String)node.get("serviceInstance"))
					&& accountId.equalsIgnoreCase((String)node.get("accountId"))
					&& cloudElement.equalsIgnoreCase((String)node.get("cloudElement"))) {
				if(("API-Gateway".equalsIgnoreCase((String)node.get("cloudElement")) || 
						"Dynamodb".equalsIgnoreCase((String)node.get("cloudElement")))
						&& cloudElementId.equalsIgnoreCase((String)node.get("cloudElementId"))) {
						return true;
				}
			}
		}
		return false;
	}
	
	public void updateViewJson(ObjectNode objectNode) throws JsonParseException, JsonMappingException, IOException {
		Long serviceId = Long.parseLong(objectNode.get("serviceId").asText());
		ObjectMapper mapper = Constants.instantiateMapper();
		for (String keyType : Constants.DASHBOARD_TYPE) {
			JsonNode jsonNode = objectNode.get(keyType.toLowerCase());

			if (jsonNode != null) {
				Optional<ServiceDetail> osd = getServiceDetail(serviceId);
				if (osd.isPresent()) {
					ServiceDetail sd = osd.get();
					if (sd.getView_json() == null) {
						logger.info("Adding new view json");
						ObjectNode ob = createViewJson(objectNode.get(Constants.SERVICE_ID).asText(), jsonNode, keyType, mapper);
						sd.setView_json(jacsonNodeAndMapConvertUtil.convertObjectNodeToMap(ob));
					} else {
						logger.info("Updating view json");
						Map<String, Object> viewJsonMap = sd.getView_json();
						updateViewJson(jsonNode, objectNode.get(Constants.SERVICE_ID).asText(), viewJsonMap, keyType, mapper);
						sd.setView_json(viewJsonMap);
					}
					updateServiceDetail(sd);
				}
			}
		}
	}

	private ObjectNode createViewJson(String serviceId, JsonNode jsonNode, String key, ObjectMapper mapper) {
		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put(Constants.SERVICE_ID, serviceId);
		for (String dashBoardTypeKey : Constants.DASHBOARD_TYPE_KEYS) {
			// created empty array in json
			objectNode.putArray(dashBoardTypeKey);
		}

		List<ObjectNode> list = constructViewJson(jsonNode, mapper);
		ArrayNode an = objectNode.putArray(key);
		for (ObjectNode obj : list) {
			an.add((JsonNode) obj);
		}
		return objectNode;
	}

	private List<ObjectNode> constructViewJson(JsonNode jsonNode, ObjectMapper mapper) {
		List<ObjectNode> list = new ArrayList<>();
		if (mapper == null) {
			mapper = Constants.instantiateMapper();
		}
		for (JsonNode node : jsonNode) {
			ObjectNode on = mapper.createObjectNode();
			for (String key : Constants.VIEW_JSON_KEYS) {
				on.put(key, (node.get(key) != null ? node.get(key).asText() : null));
			}
			list.add(on);
		}
		return list;
	}

	public void updateViewJson(JsonNode jsonNode, String serviceId, Map<String, Object> viewJsonMap, String key,
			ObjectMapper mapper) {
		List<ObjectNode> newList = constructViewJson(jsonNode, mapper);
		List existingList = (List) viewJsonMap.get(key);
		for (ObjectNode obj : newList) {
			existingList.add((JsonNode) obj);
		}
		viewJsonMap.put(key, existingList);
	}

}
