package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.synectiks.asset.domain.DeploymentEnvironment;

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
public class DeploymentEnvironmentResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String name;
  private ProductBillingResponse productBilling;
//  private List<ServiceResponse> serviceList;
  private List<ServiceCategoryResponse> serviceCategoryList;
  
  
  public static DeploymentEnvironmentResponse from(DeploymentEnvironment depEnv) {
	  return DeploymentEnvironmentResponse.builder()
			  .id(depEnv.getId())
			  .name(depEnv.getName())
			  .build();
  }
  
  public static DeploymentEnvironmentResponse from(Long id, String name) {
	  DeploymentEnvironmentResponse der = DeploymentEnvironmentResponse.builder().build();
	  if(id != null) {
		  der.setId(id);
	  }
	  if(!StringUtils.isBlank(name)) {
		  der.setName(name);
	  }
	  return der;
  }
}
