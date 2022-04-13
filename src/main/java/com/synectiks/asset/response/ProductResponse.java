package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

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
}
