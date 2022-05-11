package com.synectiks.asset.response;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.domain.ServiceTagLink;

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
public class ServiceTagLinkResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String name;
  private String description;
  private String status;
  private String hostingType;
  
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
  private String associatedClusterNamespace;
  private String associatedManagedCloudServiceLocation;
  private String associatedCloudElementId;
  private String associatedGlobalServiceLocation;
	  
	  
  private ServiceBillingResponse serviceBilling;
  private PerformanceResponse performance;
  private AvailabilityResponse availability;
  private SecurityResponse security;
  private DataProtectionResponse dataProtection;
  private UserExperianceResponse userExperiance;
  
  public static ServiceTagLinkResponse from (ServiceTagLink stl) {
	  return ServiceTagLinkResponse.builder()
			  .id(stl.getId())
			  .name(stl.getName())
			  .hostingType(stl.getHostingType())
			  .description(stl.getDescription())
			  .status(stl.getStatus())
			  .build();
  }
  
  public static ServiceTagLinkResponse from (Long id, String name, String hostingType, String description, String status) {
	  ServiceTagLinkResponse stlr = ServiceTagLinkResponse.builder().build();
	  if(id != null) {
		  stlr.setId(id);
	  }
	  if(!StringUtils.isBlank(name)) {
		  stlr.setName(name);
	  }
	  if(!StringUtils.isBlank(hostingType)) {
		  stlr.setHostingType(hostingType);
	  }
	  if(!StringUtils.isBlank(description)) {
		  stlr.setDescription(description);
	  }
	  if(!StringUtils.isBlank(status)) {
		  stlr.setStatus(status);
	  }
	  return stlr;
  }
  
  public static ServiceTagLinkResponse from (ServiceDetail sd) {
	  ServiceTagLinkResponse stlr = ServiceTagLinkResponse.builder().build();
	    stlr.setId(sd.getId());
	    stlr.setName(sd.getDetails().getName());
		stlr.setHostingType(sd.getDetails().getServiceHostingType());
	    stlr.setDescription(sd.getDetails().getDescription());
	    
	    stlr.setAssociatedOU(sd.getDetails().getAssociatedOU());
	    stlr.setAssociatedDept(sd.getDetails().getAssociatedDept());
	    stlr.setAssociatedProduct(sd.getDetails().getAssociatedProduct());
	    stlr.setAssociatedEnv(sd.getDetails().getAssociatedEnv());
	    stlr.setAssociatedLandingZone(sd.getDetails().getAssociatedLandingZone());
	    stlr.setAssociatedProductEnclave(sd.getDetails().getAssociatedProductEnclave());
	    stlr.setAssociatedCluster(sd.getDetails().getAssociatedCluster());
	    stlr.setServiceNature(sd.getDetails().getServiceNature());
	    stlr.setAssociatedCommonService(sd.getDetails().getAssociatedCommonService());
	    stlr.setAssociatedBusinessService(sd.getDetails().getAssociatedBusinessService());
	    stlr.setServiceType(sd.getDetails().getServiceType());
	    stlr.setAssociatedClusterNamespace(sd.getDetails().getAssociatedClusterNamespace());
	    stlr.setAssociatedManagedCloudServiceLocation(sd.getDetails().getAssociatedManagedCloudServiceLocation());
	    stlr.setAssociatedCloudElementId(sd.getDetails().getAssociatedCloudElementId());
	    stlr.setAssociatedGlobalServiceLocation(sd.getDetails().getAssociatedGlobalServiceLocation());
	    
	  return stlr;
  }
  
}
