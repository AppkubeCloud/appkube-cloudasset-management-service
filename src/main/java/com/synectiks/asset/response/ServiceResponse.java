package com.synectiks.asset.response;

import java.io.Serializable;

import com.synectiks.asset.domain.Services;

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
public class ServiceResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String name;
  private String description;
  private String type;
  private String status;
  private ServiceBillingResponse serviceBilling;
  
  public static ServiceResponse from(Services services) {
	  return ServiceResponse.builder()
			  .id(services.getId())
			  .name(services.getName())
			  .description(services.getDescription())
			  .type(services.getType())
			  .status(services.getStatus())
			  .build();
  }
}
