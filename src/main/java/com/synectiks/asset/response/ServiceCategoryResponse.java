package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

import com.synectiks.asset.domain.ServiceCategory;

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
public class ServiceCategoryResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String name;
  private String description;
  private String status;
  private List<ServiceResponse> serviceList;
  
  public static ServiceCategoryResponse from(ServiceCategory serviceCategory) {
	  return ServiceCategoryResponse.builder()
			  .id(serviceCategory.getId())
			  .name(serviceCategory.getName())
			  .description(serviceCategory.getDescription())
			  .status(serviceCategory.getStatus())
			  .build();
  }

}
