package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

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
}
