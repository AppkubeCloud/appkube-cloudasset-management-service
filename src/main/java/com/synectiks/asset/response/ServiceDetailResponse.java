package com.synectiks.asset.response;

import java.io.Serializable;

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
}
