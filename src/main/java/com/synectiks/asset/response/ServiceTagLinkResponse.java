package com.synectiks.asset.response;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

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
  
}
