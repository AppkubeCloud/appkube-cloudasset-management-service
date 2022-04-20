package com.synectiks.asset.response;

import java.io.Serializable;

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
 
  private ServiceBillingResponse serviceBilling;
  private PerformanceResponse performance;
  private AvailabilityResponse availability;
  private SecurityResponse security;
  private DataProtectionResponse dataProtection;
  private UserExperianceResponse userExperiance;
  
  public static ServiceTagLinkResponse from (ServiceTagLink stl) {
	  return ServiceTagLinkResponse.builder()
			  .id(stl.getId())
			  .name(stl.getServices().getName())
			  .description(stl.getDescription())
			  .status(stl.getStatus())
			  .build();
  }
}
