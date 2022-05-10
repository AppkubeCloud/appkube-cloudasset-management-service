package com.synectiks.asset.response;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.synectiks.asset.domain.ServiceBilling;

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
public class ServiceBillingResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private Double amount;
  private String status;
  
  public static ServiceBillingResponse from(ServiceBilling sb) {
	  return ServiceBillingResponse.builder()
			  .id(sb.getId())
			  .amount(sb.getAmount())
			  .status(sb.getStatus())
			  .build();
  }
  
  public static ServiceBillingResponse from(Long id, Double amount, String status) {
	  ServiceBillingResponse sbr = ServiceBillingResponse.builder().build();
	  if(id != null) {
		  sbr.setId(id);
	  }
	  if(amount != null) {
		  sbr.setAmount(amount);
	  }else {
		  sbr.setAmount(0D);
	  }
	  if(!StringUtils.isBlank(status)) {
		  sbr.setStatus(status);
	  }
	  return sbr;
  }
  
}
