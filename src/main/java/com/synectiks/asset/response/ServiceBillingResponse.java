package com.synectiks.asset.response;

import java.io.Serializable;

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
}
