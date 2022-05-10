package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
//  private List<ServiceResponse> serviceList;
  private List<ServiceNameResponse> serviceNameList;
//  private List<ServiceTagResponse> tagList;
  
  public static ServiceCategoryResponse from(ServiceCategory serviceCategory) {
	  return ServiceCategoryResponse.builder()
			  .id(serviceCategory.getId())
			  .name(serviceCategory.getName())
			  .description(serviceCategory.getDescription())
			  .status(serviceCategory.getStatus())
			  .build();
  }

  public static ServiceCategoryResponse from(Long id, String name, String description, String status) {
	  ServiceCategoryResponse scr = ServiceCategoryResponse.builder().build();
	  if(id != null) {
		  scr.setId(id);
	  }
	  if(!StringUtils.isBlank(name)) {
		  scr.setName(name);
	  }
	  if(!StringUtils.isBlank(description)) {
		  scr.setDescription(description);
	  }
	  if(!StringUtils.isBlank(status)) {
		  scr.setStatus(status);
	  }
	  return scr;
  }
}
