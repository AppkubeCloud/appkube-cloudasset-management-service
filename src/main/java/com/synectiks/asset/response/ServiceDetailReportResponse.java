package com.synectiks.asset.response;

import java.io.Serializable;

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

  private Long id;
  private ServiceDetailResponse details;
  
  public static ServiceDetailReportResponse from(ServiceDetail sd) {
	  ServiceDetailReportResponse sdr = ServiceDetailReportResponse.builder()
			  .id(sd.getId())
			  .build();
	  sdr.setDetails(ServiceDetailResponse.from(sd));
	  return sdr;
  }
  
  public static ServiceDetailReportResponse from(Long id, ServiceDetailResponse sd) {
	  ServiceDetailReportResponse sdr = ServiceDetailReportResponse.builder()
			  .build();
	  sdr.setId(id);
	  sdr.setDetails(sd);
	  return sdr;
  }
}
