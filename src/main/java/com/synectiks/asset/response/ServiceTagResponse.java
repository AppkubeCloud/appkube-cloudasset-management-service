package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
  
  public static ServiceTagResponse from(Long id, String name, String status) {
	  ServiceTagResponse str = ServiceTagResponse.builder().build();
	  if(id != null) {
		  str.setId(id);
	  }
	  if(!StringUtils.isBlank(name)) {
		  str.setTagName(name);
	  }
	  if(!StringUtils.isBlank(status)) {
		  str.setStatus(status);
	  }
	  return str;
  }
}