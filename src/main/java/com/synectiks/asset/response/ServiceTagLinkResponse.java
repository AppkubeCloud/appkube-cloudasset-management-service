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
	    stlr.setName((String)sd.getMetadata_json().get("name"));
		stlr.setHostingType((String)sd.getMetadata_json().get("serviceHostingType"));
	    stlr.setDescription((String)sd.getMetadata_json().get("description"));
	    
	    stlr.setAssociatedOU((String)sd.getMetadata_json().get("associatedOU"));
	    stlr.setAssociatedDept((String)sd.getMetadata_json().get("associatedDept"));
	    stlr.setAssociatedProduct((String)sd.getMetadata_json().get("associatedProduct"));
	    stlr.setAssociatedEnv((String)sd.getMetadata_json().get("associatedEnv"));
	    stlr.setAssociatedLandingZone((String)sd.getMetadata_json().get("associatedLandingZone"));
	    stlr.setAssociatedProductEnclave((String)sd.getMetadata_json().get("associatedProductEnclave"));
	    stlr.setAssociatedCluster((String)sd.getMetadata_json().get("associatedCluster"));
	    stlr.setServiceNature((String)sd.getMetadata_json().get("serviceNature"));
	    stlr.setAssociatedCommonService((String)sd.getMetadata_json().get("associatedCommonService"));
	    stlr.setAssociatedBusinessService((String)sd.getMetadata_json().get("associatedBusinessService"));
	    stlr.setServiceType((String)sd.getMetadata_json().get("serviceType"));
	    stlr.setAssociatedClusterNamespace((String)sd.getMetadata_json().get("associatedClusterNamespace"));
	    stlr.setAssociatedManagedCloudServiceLocation((String)sd.getMetadata_json().get("associatedManagedCloudServiceLocation"));
	    stlr.setAssociatedCloudElementId((String)sd.getMetadata_json().get("associatedCloudElementId"));
	    stlr.setAssociatedGlobalServiceLocation((String)sd.getMetadata_json().get("associatedGlobalServiceLocation"));
	    
	  return stlr;
  }
  
}
