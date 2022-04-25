package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

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
public class ServiceDetailReportingResponse implements Serializable {

  private static final long serialVersionUID = 1L;
  private List<ServiceDetailReportResponse> services;

  public static ServiceDetailReportingResponse from(List<ServiceDetailReportResponse> services) {
	  ServiceDetailReportingResponse sdr = ServiceDetailReportingResponse.builder()
			  .build();
	  sdr.setServices(services);
	  return sdr;
  }
  
}
