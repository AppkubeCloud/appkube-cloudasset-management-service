package com.synectiks.asset.response;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.synectiks.asset.domain.ProductBilling;

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
public class ProductBillingResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private Double amount;
  private String status;
  
  public static ProductBillingResponse from(ProductBilling pb) {
	  return ProductBillingResponse.builder()
			  .id(pb.getId())
			  .amount(pb.getAmount())
			  .status(pb.getStatus())
			  .build();
  }
  
  public static ProductBillingResponse from(Long id, Double amount, String status) {
	  ProductBillingResponse pbr = ProductBillingResponse.builder().build();
	  if(id != null) {
		  pbr.setId(id);
	  }
	  if(amount != null) {
		  pbr.setAmount(amount);
	  }else {
		  pbr.setAmount(0D);
	  }
	  if(!StringUtils.isBlank(status)) {
		  pbr.setStatus(status);
	  }
	  return pbr;
  }
}
