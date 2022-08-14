package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.synectiks.asset.util.RandomUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDetailResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private String name;
  private String description;
  private String associatedOU;
  private String associatedDept;
  private String associatedProduct;
  private String associatedEnv;
  private String associatedLandingZone;
  private String associatedProductEnclave;
  private String associatedCluster;
  private String serviceNature; 
  private String associatedCommonService;
  private String associatedBusinessService;
  private String serviceType; 
  private String serviceHostingType;        
  private String associatedClusterNamespace;
  private String associatedManagedCloudServiceLocation;
  private String associatedCloudElementId;
  private String associatedCloudElement;
  private String associatedGlobalServiceLocation;  
//  private String status;
//  private Instant createdOn;
//  private Instant updatedOn;
//  private String updatedBy;
//  private String createdBy;
  private ServiceDetailStats stats;
  
  private PerformanceResponse performance;
  private AvailabilityResponse availability;
  private SecurityResponse security;
  private DataProtectionResponse dataProtection;
  private UserExperianceResponse userExperiance;
  
//  public static ServiceDetailResponse from(ServiceDetail sd) {
//	  return ServiceDetailResponse.builder()
//			  .name(sd.getName())
//			  .description("NA".equalsIgnoreCase(sd.getDescription()) ? "" : sd.getDescription())
//			  .associatedOU("NA".equalsIgnoreCase(sd.getAssociatedOU()) ? "": sd.getAssociatedOU())
//			  .associatedDept("NA".equalsIgnoreCase(sd.getAssociatedDept()) ? "" : sd.getAssociatedDept()) 
//			  .associatedProduct("NA".equalsIgnoreCase(sd.getAssociatedProduct()) ? "" : sd.getAssociatedProduct()) 
//			  .associatedEnv("NA".equalsIgnoreCase(sd.getAssociatedEnv()) ? "" : sd.getAssociatedEnv())
//			  .associatedLandingZone("NA".equalsIgnoreCase(sd.getAssociatedLandingZone()) ? "" : sd.getAssociatedLandingZone())
//			  .associatedProductEnclave("NA".equalsIgnoreCase(sd.getAssociatedProductEnclave()) ? "" : sd.getAssociatedProductEnclave())
//			  .associatedCluster("NA".equalsIgnoreCase(sd.getAssociatedCluster()) ? "" : sd.getAssociatedCluster()) 
//			  .serviceNature("NA".equalsIgnoreCase(sd.getServiceNature()) ? "" : sd.getServiceNature())
//			  .associatedCommonService("NA".equalsIgnoreCase(sd.getAssociatedCommonService()) ? "" : sd.getAssociatedCommonService())
//			  .associatedBusinessService("NA".equalsIgnoreCase(sd.getAssociatedBusinessService()) ? "" : sd.getAssociatedBusinessService())
//			  .serviceType("NA".equalsIgnoreCase(sd.getServiceType()) ? "" : sd.getServiceType())
//			  .serviceHostingType("NA".equalsIgnoreCase(sd.getServiceHostingType()) ? "" : sd.getServiceHostingType())
//			  .associatedClusterNamespace("NA".equalsIgnoreCase(sd.getAssociatedClusterNamespace()) ? "" : sd.getAssociatedClusterNamespace())
//			  .associatedManagedCloudServiceLocation("NA".equalsIgnoreCase(sd.getAssociatedManagedCloudServiceLocation()) ? "" : sd.getAssociatedManagedCloudServiceLocation())
//			  .associatedCloudElementId("NA".equalsIgnoreCase(sd.getAssociatedCloudElementId()) ? "" : sd.getAssociatedCloudElementId())
//			  .associatedGlobalServiceLocation("NA".equalsIgnoreCase(sd.getAssociatedGlobalServiceLocation()) ? "" : sd.getAssociatedGlobalServiceLocation())
//			  .build();
//  }
  
  public static ServiceDetailResponse from(JsonNode node) {
	  ServiceDetailResponse sdr = ServiceDetailResponse.builder()
	  .name(node.get("name").asText())
	  .description("NA".equalsIgnoreCase(node.get("description").asText()) ? "" : node.get("description").asText())
	  .associatedOU("NA".equalsIgnoreCase(node.get("associatedOU").asText()) ? "": node.get("associatedOU").asText())
	  .associatedDept("NA".equalsIgnoreCase(node.get("associatedDept").asText()) ? "": node.get("associatedDept").asText()) 
	  .associatedProduct("NA".equalsIgnoreCase(node.get("associatedProduct").asText()) ? "": node.get("associatedProduct").asText()) 
	  .associatedEnv("NA".equalsIgnoreCase(node.get("associatedEnv").asText()) ? "": node.get("associatedEnv").asText())
	  .associatedLandingZone("NA".equalsIgnoreCase(node.get("associatedLandingZone").asText()) ? "": node.get("associatedLandingZone").asText())
	  .associatedProductEnclave("NA".equalsIgnoreCase(node.get("associatedProductEnclave").asText()) ? "": node.get("associatedProductEnclave").asText())
	  .associatedCluster("NA".equalsIgnoreCase(node.get("associatedCluster").asText()) ? "": node.get("associatedCluster").asText()) 
	  .serviceNature("NA".equalsIgnoreCase(node.get("serviceNature").asText()) ? "": node.get("serviceNature").asText())
	  .associatedCommonService("NA".equalsIgnoreCase(node.get("associatedCommonService").asText()) ? "": node.get("associatedCommonService").asText())
	  .associatedBusinessService("NA".equalsIgnoreCase(node.get("associatedBusinessService").asText()) ? "": node.get("associatedBusinessService").asText())
	  .serviceType("NA".equalsIgnoreCase(node.get("serviceType").asText()) ? "": node.get("serviceType").asText())
	  .serviceHostingType("NA".equalsIgnoreCase(node.get("serviceHostingType").asText()) ? "": node.get("serviceHostingType").asText())
	  .associatedClusterNamespace("NA".equalsIgnoreCase(node.get("associatedClusterNamespace").asText()) ? "": node.get("associatedClusterNamespace").asText())
	  .associatedManagedCloudServiceLocation("NA".equalsIgnoreCase(node.get("associatedManagedCloudServiceLocation").asText()) ? "": node.get("associatedManagedCloudServiceLocation").asText())
	  .associatedCloudElementId("NA".equalsIgnoreCase(node.get("associatedCloudElementId").asText()) ? "": node.get("associatedCloudElementId").asText())
	  .associatedCloudElement("NA".equalsIgnoreCase(node.get("associatedCloudElement").asText()) ? "": node.get("associatedCloudElement").asText())
	  .associatedGlobalServiceLocation("NA".equalsIgnoreCase(node.get("associatedGlobalServiceLocation").asText()) ? "": node.get("associatedGlobalServiceLocation").asText())
	  .performance(PerformanceResponse.builder().score(RandomUtil.getRandom()).build())
	  .availability(AvailabilityResponse.builder().score(RandomUtil.getRandom()).build())
	  .security(SecurityResponse.builder().score(RandomUtil.getRandom()).build())
	  .dataProtection(DataProtectionResponse.builder().score(RandomUtil.getRandom()).build())
	  .userExperiance(UserExperianceResponse.builder().score(RandomUtil.getRandom()).build())
	  .build();
	  JsonNode statsNode = node.get("stats");
	  ServiceDetailStats sds = ServiceDetailStats.fromString(statsNode.get("totalCostSoFar").asText(), statsNode.get("lastDayCost").asText(), statsNode.get("lastWeekCost").asText(), statsNode.get("lastMonthCost").asText());
	  sdr.setStats(sds);
	  return sdr;
  }
  
  public static Map<String, Object> toMap(JsonNode node) {
	  Map<String, Object> sdr = new HashMap<>();
//	  ServiceDetailResponse sdr = ServiceDetailResponse.builder()
	  sdr.put("name",node.get("name").asText());
	  sdr.put("description","NA".equalsIgnoreCase(node.get("description").asText()) ? "" : node.get("description").asText());
	  sdr.put("associatedOU","NA".equalsIgnoreCase(node.get("associatedOU").asText()) ? "": node.get("associatedOU").asText());
	  sdr.put("associatedDept","NA".equalsIgnoreCase(node.get("associatedDept").asText()) ? "": node.get("associatedDept").asText()) ;
	  sdr.put("associatedProduct","NA".equalsIgnoreCase(node.get("associatedProduct").asText()) ? "": node.get("associatedProduct").asText()) ;
	  sdr.put("associatedEnv","NA".equalsIgnoreCase(node.get("associatedEnv").asText()) ? "": node.get("associatedEnv").asText());
	  sdr.put("associatedLandingZone","NA".equalsIgnoreCase(node.get("associatedLandingZone").asText()) ? "": node.get("associatedLandingZone").asText());
	  sdr.put("associatedProductEnclave","NA".equalsIgnoreCase(node.get("associatedProductEnclave").asText()) ? "": node.get("associatedProductEnclave").asText());
	  sdr.put("associatedCluster","NA".equalsIgnoreCase(node.get("associatedCluster").asText()) ? "": node.get("associatedCluster").asText()) ;
	  sdr.put("serviceNature","NA".equalsIgnoreCase(node.get("serviceNature").asText()) ? "": node.get("serviceNature").asText());
	  sdr.put("associatedCommonService","NA".equalsIgnoreCase(node.get("associatedCommonService").asText()) ? "": node.get("associatedCommonService").asText());
	  sdr.put("associatedBusinessService","NA".equalsIgnoreCase(node.get("associatedBusinessService").asText()) ? "": node.get("associatedBusinessService").asText());
	  sdr.put("serviceType", "NA".equalsIgnoreCase(node.get("serviceType").asText()) ? "": node.get("serviceType").asText());
	  sdr.put("serviceHostingType","NA".equalsIgnoreCase(node.get("serviceHostingType").asText()) ? "": node.get("serviceHostingType").asText());
	  sdr.put("associatedClusterNamespace","NA".equalsIgnoreCase(node.get("associatedClusterNamespace").asText()) ? "": node.get("associatedClusterNamespace").asText());
	  sdr.put("associatedManagedCloudServiceLocation","NA".equalsIgnoreCase(node.get("associatedManagedCloudServiceLocation").asText()) ? "": node.get("associatedManagedCloudServiceLocation").asText());
	  sdr.put("associatedCloudElementId","NA".equalsIgnoreCase(node.get("associatedCloudElementId").asText()) ? "": node.get("associatedCloudElementId").asText());
	  sdr.put("associatedCloudElement","NA".equalsIgnoreCase(node.get("associatedCloudElement").asText()) ? "": node.get("associatedCloudElement").asText());
	  sdr.put("associatedGlobalServiceLocation","NA".equalsIgnoreCase(node.get("associatedGlobalServiceLocation").asText()) ? "": node.get("associatedGlobalServiceLocation").asText());
	  
	  sdr.put("performance",(PerformanceResponse.builder().score(RandomUtil.getRandom()).build()));
	  sdr.put("availability",(AvailabilityResponse.builder().score(RandomUtil.getRandom()).build()));
	  sdr.put("security",(SecurityResponse.builder().score(RandomUtil.getRandom()).build()));
	  sdr.put("dataProtection",(DataProtectionResponse.builder().score(RandomUtil.getRandom()).build()));
	  sdr.put("userExperiance",(UserExperianceResponse.builder().score(RandomUtil.getRandom()).build()));
//	  .build();
//	  JsonNode statsNode = node.get("stats");
//	  ServiceDetailStats sds = ServiceDetailStats.fromString(statsNode.get("totalCostSoFar").asText(), statsNode.get("lastDayCost").asText(), statsNode.get("lastWeekCost").asText(), statsNode.get("lastMonthCost").asText());
//	  sdr.setStats(sds);
	  return sdr;
  }
  
}
