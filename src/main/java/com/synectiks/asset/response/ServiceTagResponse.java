package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

import com.synectiks.asset.domain.ServiceTag;
import com.synectiks.asset.domain.ServiceTagLink;

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
public class ServiceTagResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String tagName;
  private String status;
  private List<ServiceTagLinkResponse> serviceList;
  
  public static ServiceTagResponse from(ServiceTag st) {
	  return ServiceTagResponse.builder()
			  .id(st.getId())
			  .tagName(st.getTagName())
			  .status(st.getStatus())
			  .build();
  }
}