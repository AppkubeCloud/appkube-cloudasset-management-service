package com.synectiks.asset.response;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlaJsonResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private String serviceId;
  
  
  public static SlaJsonResponse from(String serviceId, JsonNode jsonNode, String key) {
	  SlaJsonResponse vrj = SlaJsonResponse.builder().serviceId(serviceId).build();
	 
	  return vrj;
  }
  
}
