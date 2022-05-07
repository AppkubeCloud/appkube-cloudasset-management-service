package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

import com.synectiks.asset.domain.ServiceDetail;

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
public class ServiceDetailReportResponse implements Serializable {

  private static final long serialVersionUID = 1L;
  private Integer total;
  private List<ServiceDetail> services;

  public static ServiceDetailReportResponse from(List<ServiceDetail> services) {
	  ServiceDetailReportResponse sdr = ServiceDetailReportResponse.builder()
			  .build();
	  sdr.setServices(services);
	  return sdr;
  }
  
}
