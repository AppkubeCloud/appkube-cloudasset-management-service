package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class ViewJsonResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private String serviceId;
  private List<PerformanceViewJsonResponse> performance;
  
  public static ViewJsonResponse from(String serviceId, JsonNode jsonNode, String key) {
	  ViewJsonResponse vrj = ViewJsonResponse.builder().serviceId(serviceId).build();
	  if(jsonNode.isArray()) {
		if("performance".equalsIgnoreCase(key)) { 
			List<PerformanceViewJsonResponse> perf = new ArrayList<>();
			for(JsonNode node: jsonNode) {
				PerformanceViewJsonResponse pvj = PerformanceViewJsonResponse.from(node);
				perf.add(pvj);
			}
			vrj.setPerformance(perf);
		}
		
	  }
	  return vrj;
  }
  
  public static ViewJsonResponse updateFrom(JsonNode jsonNode, String serviceId, ViewJsonResponse viewJsonResponse, String key) {
	  ViewJsonResponse vrj = ViewJsonResponse.builder().serviceId(serviceId).build();
	  List<PerformanceViewJsonResponse> newPerfList = new ArrayList<>();
	  if(jsonNode.isArray()) {
		for(JsonNode node: jsonNode) {
			PerformanceViewJsonResponse pvj = PerformanceViewJsonResponse.from(node);
			newPerfList.add(pvj);
		}
	  }
	  List<PerformanceViewJsonResponse> existingViewJsonList = viewJsonResponse.getPerformance();
	  for(PerformanceViewJsonResponse p: newPerfList) {
		  existingViewJsonList.add(p);
	  }
	  vrj.setPerformance(existingViewJsonList);
	  return vrj;
  }
  
}
