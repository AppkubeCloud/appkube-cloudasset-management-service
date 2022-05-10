package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.synectiks.asset.domain.Product;

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
public class ProductResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String name;
  private String description;
  private String status;
  
  private List<DeploymentEnvironmentResponse> deploymentEnvironmentList;
  
  public static ProductResponse from(Product product) {
	  return ProductResponse.builder()
			  .id(product.getId())
			  .name(product.getName())
			  .description(product.getDescription())
			  .status(product.getStatus())
			  .build();
  }
  
  public static ProductResponse from(Long id, String name, String description, String status) {
	  ProductResponse pr = ProductResponse.builder().build();
	  if(id != null) {
		  pr.setId(id);
	  }
	  if(!StringUtils.isBlank(name)) {
		  pr.setName(name);
	  }
	  if(!StringUtils.isBlank(description)) {
		  pr.setDescription(description);
	  }
	  if(!StringUtils.isBlank(status)) {
		  pr.setStatus(status);
	  }
	  return pr;
  }
}
