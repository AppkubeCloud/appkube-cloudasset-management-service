package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.repository.ServiceDetailRepository;
import com.synectiks.asset.response.AccountTree;
import com.synectiks.asset.response.App;
import com.synectiks.asset.response.BusinessService;
import com.synectiks.asset.response.Cluster;
import com.synectiks.asset.response.CommonService;
import com.synectiks.asset.response.Data;
import com.synectiks.asset.response.Environment;
import com.synectiks.asset.response.Product;
import com.synectiks.asset.response.ServiceDetailReportResponse;
import com.synectiks.asset.response.ServiceDetailResponse;
import com.synectiks.asset.response.Vpc;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ServiceDetailService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceDetailService.class);
		
	@Autowired
	ServiceDetailRepository serviceDetailJsonRepository;
	
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
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. Service detail cannot be deleted", id);
			return oObj;
		}
		serviceDetailJsonRepository.deleteById(id);
		return oObj;
	}
	
	public ServiceDetail createServiceDetail(ServiceDetail obj){
		logger.info("Create new service detail");
		return serviceDetailJsonRepository.save(obj);
	}
	
	public ServiceDetail updateServiceDetail(ServiceDetail obj){
		logger.info("Update service detail. Id: {}", obj.getId());
		if(!serviceDetailJsonRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceDetail", "idnotfound");
		}
		return serviceDetailJsonRepository.save(obj);
	}
	
	public Optional<ServiceDetail> partialUpdateServiceDetail(ServiceDetail obj){
		logger.info("Update service detail partialy. Id: {}", obj.getId());
		if(!serviceDetailJsonRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceDetail", "idnotfound");
		}
		Optional<ServiceDetail> result = serviceDetailJsonRepository.findById(obj.getId())
			.map(existingObj ->{
				return existingObj;
			})
			.map(serviceDetailJsonRepository::save);
		return result;
	}
	
	public ServiceDetailReportResponse searchAllServiceDetail(Map<String, String> obj) {
		logger.info("Search service detail json");
		Gson gson = new Gson(); 
		String json = gson.toJson(obj); 
		List<ServiceDetail> list = serviceDetailJsonRepository.findServiceDetails(json);
		ServiceDetailReportResponse resp = ServiceDetailReportResponse.builder().build();
		resp.setServices(list);
		resp.setTotal(list.size());
		return resp;
	}
	
	public void updateViewJson(JsonNode node) {
		String apiKey[] = {"performance","availability","reliability","endUsage","security","compliance","alerts"};
		for(String key: apiKey) {
			serviceDetailJsonRepository.updateViewJson(node.get("id").asText(), key);
		}
	}
	
	public void createBulkData(ObjectNode objNode) throws IOException {
		JsonNode objArray = objNode.get("services");
		for(JsonNode node: objArray) {
			ServiceDetail sd = ServiceDetail.builder().build();
			sd.setMetadata_json(ServiceDetailResponse.toMap(node));
			createServiceDetail(sd);
		}
	}
	
	public ServiceDetailReportResponse searchServiceDetailWithFilter(Map<String, String> obj) {
		logger.info("Search service detail with filter");
		ServiceDetailReportResponse resp = ServiceDetailReportResponse.builder().build();
		
		List<ServiceDetail> list = serviceDetailJsonRepository.findAll();
		
		if(obj.size() == 0 ) {
			resp.setServices(list);
			resp.setTotal(list.size());
			return resp;
		}
		
		List<ServiceDetail> list2 = list;
		if(obj.containsKey("name")) {
			list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("name")).equals(obj.get("name")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("description")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("description")).equals(obj.get("description")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("serviceType")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("serviceType")).equalsIgnoreCase(obj.get("serviceType")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedOU")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedOU")).equalsIgnoreCase(obj.get("associatedOU")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedEnv")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedEnv")).equalsIgnoreCase(obj.get("associatedEnv")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("serviceNature")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("serviceNature")).equalsIgnoreCase(obj.get("serviceNature")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedDept")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedDept")).equalsIgnoreCase(obj.get("associatedDept")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedCluster")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedCluster")).equalsIgnoreCase(obj.get("associatedCluster")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedProduct")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedProduct")).equalsIgnoreCase(obj.get("associatedProduct")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("serviceHostingType")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("serviceHostingType")).equalsIgnoreCase(obj.get("serviceHostingType")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedLandingZone")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedLandingZone")).equalsIgnoreCase(obj.get("associatedLandingZone")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedCommonService")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedCommonService")).equalsIgnoreCase(obj.get("associatedCommonService")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedCloudElementId")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedCloudElementId")).equals(obj.get("associatedCloudElementId")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedProductEnclave")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedProductEnclave")).equalsIgnoreCase(obj.get("associatedProductEnclave")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedBusinessService")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedBusinessService")).equalsIgnoreCase(obj.get("associatedBusinessService")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedClusterNamespace")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedClusterNamespace")).equalsIgnoreCase(obj.get("associatedClusterNamespace")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedGlobalServiceLocation")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedGlobalServiceLocation")).equalsIgnoreCase(obj.get("associatedGlobalServiceLocation")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedManagedCloudServiceLocation")) {
			 list2 = list2.stream()
					.filter(sd -> ((String)sd.getMetadata_json().get("associatedManagedCloudServiceLocation")).equalsIgnoreCase(obj.get("associatedManagedCloudServiceLocation")))
					.collect(Collectors.toList());
		}
		resp.setServices(list2);
		resp.setTotal(list2.size());
		return resp;
	}

	public List<AccountTree> transformServiceDetailsListToTree() {
		List<ServiceDetail> listSd = getAllServiceDetail();
		Map<String, List<ServiceDetail>> acMap = filterAccountSpecificList(listSd);
		List<AccountTree> treeList = filterVpcs(acMap);
		filterClusters(acMap, treeList);
		filterProducts(acMap, treeList);
		filterEnvironments(acMap, treeList);
		filterServiceNature(acMap, treeList);
		
		for(AccountTree account: treeList) {
			for(Vpc vpc: account.getVpcs()) {
				for(Cluster cluster: vpc.getClusters()) {
					for(Product product: cluster.getProducts()) {
						for(Environment environment: product.getEnvironments()) {
							if(environment.getServices() != null) {
								com.synectiks.asset.response.Service service = environment.getServices();
								if(service.getBusiness() != null) {
									for(BusinessService bs: service.getBusiness()) {
										for(Map.Entry<String, List<ServiceDetail>> entry: acMap.entrySet()) {
											if(entry.getKey().equals(account.getAccount())) {
												for(ServiceDetail sd: entry.getValue()) {
													String vpcName = (String)sd.getMetadata_json().get("associatedProductEnclave");
													String clusterName = (String)sd.getMetadata_json().get("associatedCluster");
													String productName = (String)sd.getMetadata_json().get("associatedProduct");
													String envName = (String)sd.getMetadata_json().get("associatedEnv");
													String associatedBusinessService = (String)sd.getMetadata_json().get("associatedBusinessService");
													
													if(!StringUtils.isBlank(vpcName)) {
														if(vpcName.substring(vpcName.indexOf("-")+1).equalsIgnoreCase(vpc.getName())
																&& clusterName.substring(clusterName.lastIndexOf("-")+1).equalsIgnoreCase(cluster.getName())
																&& productName.equalsIgnoreCase(product.getName())
																&& envName.equalsIgnoreCase(environment.getName())
																&& associatedBusinessService.equalsIgnoreCase(bs.getName())){
															String serviceType = (String)sd.getMetadata_json().get("serviceType");
															if(serviceType.equalsIgnoreCase("App")) {
																App app = App.builder()
																		.id(envName+"_"+(String)sd.getMetadata_json().get("name"))
																		.name((String)sd.getMetadata_json().get("name"))
																		.serviceDetailId(sd.getId())
																		.description((String)sd.getMetadata_json().get("description"))
																		.associatedCloudElement((String)sd.getMetadata_json().get("associatedCloudElement"))
																		.associatedClusterNamespace((String)sd.getMetadata_json().get("associatedClusterNamespace"))
																		.associatedManagedCloudServiceLocation((String)sd.getMetadata_json().get("associatedManagedCloudServiceLocation"))
																		.associatedGlobalServiceLocation((String)sd.getMetadata_json().get("associatedGlobalServiceLocation"))
																		.serviceHostingType((String)sd.getMetadata_json().get("serviceHostingType"))
																		.associatedCloudElementId((String)sd.getMetadata_json().get("associatedCloudElementId"))
																		.build();
																if(bs.getApp() == null) {
																	List<App> appList = new ArrayList<>();
																	appList.add(app);
																	bs.setApp(appList);
																}else {
																	bs.getApp().add(app);
																}
															}else if(serviceType.equalsIgnoreCase("Data")) {
																Data data = Data.builder()
																		.id(envName+"_"+(String)sd.getMetadata_json().get("name"))
																		.name((String)sd.getMetadata_json().get("name"))
																		.serviceDetailId(sd.getId())
																		.description((String)sd.getMetadata_json().get("description"))
																		.associatedCloudElement((String)sd.getMetadata_json().get("associatedCloudElement"))
																		.associatedClusterNamespace((String)sd.getMetadata_json().get("associatedClusterNamespace"))
																		.associatedManagedCloudServiceLocation((String)sd.getMetadata_json().get("associatedManagedCloudServiceLocation"))
																		.associatedGlobalServiceLocation((String)sd.getMetadata_json().get("associatedGlobalServiceLocation"))
																		.serviceHostingType((String)sd.getMetadata_json().get("serviceHostingType"))
																		.associatedCloudElementId((String)sd.getMetadata_json().get("associatedCloudElementId"))
																		.build();
																if(bs.getData() == null) {
																	List<Data> dataList = new ArrayList<>();
																	dataList.add(data);
																	bs.setData(dataList);
																}else {
																	bs.getData().add(data);
																}
															}
														}
													}
												}
											}
										}
									}
								}
								if(service.getCommon() != null) {
									for(CommonService cs: service.getCommon()) {
										
										for(Map.Entry<String, List<ServiceDetail>> entry: acMap.entrySet()) {
											if(entry.getKey().equals(account.getAccount())) {
												for(ServiceDetail sd: entry.getValue()) {

													String vpcName = (String)sd.getMetadata_json().get("associatedProductEnclave");
													String clusterName = (String)sd.getMetadata_json().get("associatedCluster");
													String productName = (String)sd.getMetadata_json().get("associatedProduct");
													String envName = (String)sd.getMetadata_json().get("associatedEnv");
													String associatedCommonService = (String)sd.getMetadata_json().get("associatedCommonService");
													
													if(!StringUtils.isBlank(vpcName)) {
														if(vpcName.substring(vpcName.indexOf("-")+1).equalsIgnoreCase(vpc.getName())
																&& clusterName.substring(clusterName.lastIndexOf("-")+1).equalsIgnoreCase(cluster.getName())
																&& productName.equalsIgnoreCase(product.getName())
																&& envName.equalsIgnoreCase(environment.getName())
																&& associatedCommonService.equalsIgnoreCase(cs.getName())){
															String serviceType = (String)sd.getMetadata_json().get("serviceType");
															if(serviceType.equalsIgnoreCase("App")) {
																if(cs.getApp() == null) {
																	List<App> appList = new ArrayList<>();
																	App app = App.builder().name((String)sd.getMetadata_json().get("name")).build();
																	appList.add(app);
																	cs.setApp(appList);
																}else {
																	App app = App.builder().name((String)sd.getMetadata_json().get("name")).build();
																	cs.getApp().add(app);
																}
															}else if(serviceType.equalsIgnoreCase("Data")) {
																if(cs.getData() == null) {
																	List<Data> dataList = new ArrayList<>();
																	Data data = Data.builder().name((String)sd.getMetadata_json().get("name")).build();
																	dataList.add(data);
																	cs.setData(dataList);
																}else {
																	Data data = Data.builder().name((String)sd.getMetadata_json().get("name")).build();
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
					}
				}
			}
		}
		
		return treeList;
	}

	private void filterServiceNature(Map<String, List<ServiceDetail>> acMap, List<AccountTree> treeList) {
		for(AccountTree account: treeList) {
			for(Vpc vpc: account.getVpcs()) {
				for(Cluster cluster: vpc.getClusters()) {
					for(Product product: cluster.getProducts()) {
						for(Environment environment: product.getEnvironments()) {
							com.synectiks.asset.response.Service service = com.synectiks.asset.response.Service.builder().build();
							List<BusinessService> businessServiceList = new ArrayList<>();
							List<CommonService> commonServiceList = new ArrayList<>();
							service.setBusiness(businessServiceList);
							service.setCommon(commonServiceList);
							
							for(Map.Entry<String, List<ServiceDetail>> entry: acMap.entrySet()) {
								if(entry.getKey().equals(account.getAccount())) {
									for(ServiceDetail sd: entry.getValue()) {
										String vpcName = (String)sd.getMetadata_json().get("associatedProductEnclave");
										String clusterName = (String)sd.getMetadata_json().get("associatedCluster");
										String productName = (String)sd.getMetadata_json().get("associatedProduct");
										String envName = (String)sd.getMetadata_json().get("associatedEnv");
										if(!StringUtils.isBlank(vpcName)) {
											if(vpcName.substring(vpcName.indexOf("-")+1).equalsIgnoreCase(vpc.getName())
													&& clusterName.substring(clusterName.lastIndexOf("-")+1).equalsIgnoreCase(cluster.getName())
													&& productName.equalsIgnoreCase(product.getName())
													&& envName.equalsIgnoreCase(environment.getName())){
												
												String serviceNature = (String)sd.getMetadata_json().get("serviceNature");
												if(serviceNature.equalsIgnoreCase("Business")) {
													String associatedBusinessService = (String)sd.getMetadata_json().get("associatedBusinessService");
													String description = (String)sd.getMetadata_json().get("description");
													String associatedOU = (String)sd.getMetadata_json().get("associatedOU");
													String associatedDept = (String)sd.getMetadata_json().get("associatedDept");
													
													BusinessService bs = BusinessService.builder()
															.name(associatedBusinessService)
															.description(description)
															.associatedOU(associatedOU)
															.associatedDept(associatedDept)
															.build();
														service.getBusiness().add(bs);
												}else if(serviceNature.equalsIgnoreCase("Common")) {
													String associatedCommonService = (String)sd.getMetadata_json().get("associatedCommonService");
													String description = (String)sd.getMetadata_json().get("description");
													String associatedOU = (String)sd.getMetadata_json().get("associatedOU");
													String associatedDept = (String)sd.getMetadata_json().get("associatedDept");
													
													CommonService cs = CommonService.builder()
															.name(associatedCommonService)
															.description(description)
															.associatedOU(associatedOU)
															.associatedDept(associatedDept)
															.build(); 
														service.getCommon().add(cs);
												}
												
											}
										}
									}
								}
							}
							environment.setServices(service);
						}
					}
				}
			}
		}
	}

	private void filterEnvironments(Map<String, List<ServiceDetail>> acMap, List<AccountTree> treeList) {
		for(AccountTree account: treeList) {
			for(Vpc vpc: account.getVpcs()) {
				for(Cluster cluster: vpc.getClusters()) {
					for(Product product: cluster.getProducts()) {
						List<Environment> environmentList = new ArrayList<>();
						for(Map.Entry<String, List<ServiceDetail>> entry: acMap.entrySet()) {
							if(entry.getKey().equals(account.getAccount())) {
								for(ServiceDetail sd: entry.getValue()) {
									String vpcName = (String)sd.getMetadata_json().get("associatedProductEnclave");
									String clusterName = (String)sd.getMetadata_json().get("associatedCluster");
									String productName = (String)sd.getMetadata_json().get("associatedProduct");
									if(!StringUtils.isBlank(vpcName)) {
										if(vpcName.substring(vpcName.indexOf("-")+1).equalsIgnoreCase(vpc.getName())
												&& clusterName.substring(clusterName.lastIndexOf("-")+1).equalsIgnoreCase(cluster.getName())
												&& productName.equalsIgnoreCase(product.getName())){
											String envName = (String)sd.getMetadata_json().get("associatedEnv");
											Environment env = Environment.builder().name(envName).build();
											if(!environmentList.contains(env)) {
												environmentList.add(env);
											}
										}	
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
		for(AccountTree account: treeList) {
			for(Vpc vpc: account.getVpcs()) {
				for(Cluster cluster: vpc.getClusters()) {
					List<Product> productList = new ArrayList<>();
					for(Map.Entry<String, List<ServiceDetail>> entry: acMap.entrySet()) {
						if(entry.getKey().equals(account.getAccount())) {
							for(ServiceDetail sd: entry.getValue()) {
								String vpcName = (String)sd.getMetadata_json().get("associatedProductEnclave");
								String clusterName = (String)sd.getMetadata_json().get("associatedCluster");
								if(!StringUtils.isBlank(vpcName)) {
									if(vpcName.substring(vpcName.indexOf("-")+1).equalsIgnoreCase(vpc.getName())
											&& clusterName.substring(clusterName.lastIndexOf("-")+1).equalsIgnoreCase(cluster.getName())){
										String productName = (String)sd.getMetadata_json().get("associatedProduct");
										Product product = Product.builder().name(productName).build();
										if(!productList.contains(product)) {
											productList.add(product);
										}
									}
								}
							}
						}
					}
					cluster.setProducts(productList);
				}
			}
		}
	}

	private void filterClusters(Map<String, List<ServiceDetail>> acMap, List<AccountTree> treeList) {
		for(Map.Entry<String, List<ServiceDetail>> entry: acMap.entrySet()) {
			for(AccountTree at: treeList) {
				if(entry.getKey().equals(at.getAccount())) {
					for(Vpc vpc: at.getVpcs()) {
						List<Cluster> clusterList = new ArrayList<>();
						for(ServiceDetail sd: entry.getValue()) {
							String vpcName = (String)sd.getMetadata_json().get("associatedProductEnclave");
							if(!StringUtils.isBlank(vpcName)) {
								if(vpcName.substring(vpcName.indexOf("-")+1).equalsIgnoreCase(vpc.getName())){
									String clusterName = (String)sd.getMetadata_json().get("associatedCluster");
									Cluster cl = new Cluster();
									cl.setName(clusterName.substring(clusterName.lastIndexOf("-")+1));
									if(!clusterList.contains(cl)) {
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
		for(Map.Entry<String, List<ServiceDetail>> entry: acMap.entrySet()) {
			AccountTree tree = new AccountTree();
			tree.setAccount(entry.getKey());
			List<Vpc> vpcList = new ArrayList<>();
			for(ServiceDetail vpc: entry.getValue()) {
				String name = (String)vpc.getMetadata_json().get("associatedProductEnclave");
				if(!StringUtils.isBlank(name)) {
					Vpc v = new Vpc();
					v.setName(name.substring(name.indexOf("-")+1));
					if(!vpcList.contains(v)) {
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
		for(ServiceDetail sd: listSd) {
			if(acMap.containsKey((String)sd.getMetadata_json().get("associatedLandingZone"))) {
				acMap.get((String)sd.getMetadata_json().get("associatedLandingZone")).add(sd);
			}else {
				List<ServiceDetail> list = new ArrayList<>();
				list.add(sd);
				acMap.put((String)sd.getMetadata_json().get("associatedLandingZone"), list);
			}
		}
		return acMap;
	}
	
//	private Map<String, List<String>> simplifyVpcMap(Map<String, List<ServiceDetail>> acMap) {
//	Map<String, List<String>> vpcMap = new HashMap<>();
//	
//	for(Map.Entry<String, List<ServiceDetail>> entry: acMap.entrySet()) {
//		for(ServiceDetail sd: entry.getValue()) {
//			if(vpcMap.containsKey(entry.getKey())) {
//				vpcMap.get(entry.getKey()).add((String)sd.getMetadata_json().get("associatedProductEnclave"));
//			}else {
//				List<String> vpcList = new ArrayList<>();
//				vpcList.add((String)sd.getMetadata_json().get("associatedProductEnclave"));
//				vpcMap.put(entry.getKey(), vpcList);
//			}
//		}
//	}
//	return vpcMap;
//}
}
