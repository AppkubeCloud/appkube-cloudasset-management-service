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
  private List<AllocatedDashboardViewJsonResponse> performance;
  private List<AllocatedDashboardViewJsonResponse> availability;
  private List<AllocatedDashboardViewJsonResponse> reliability;
  private List<AllocatedDashboardViewJsonResponse> endUsage;
  private List<AllocatedDashboardViewJsonResponse> security;
  private List<AllocatedDashboardViewJsonResponse> compliance;
  private List<AllocatedDashboardViewJsonResponse> alerts;
  
  public static ViewJsonResponse from(String serviceId, JsonNode jsonNode, String key) {
	  ViewJsonResponse vrj = ViewJsonResponse.builder().serviceId(serviceId).build();
	  if(jsonNode.isArray()) {
		if("performance".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> list = new ArrayList<>();
			createViewJson(jsonNode, list);
			vrj.setPerformance(list);
		}
		if("availability".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> list = new ArrayList<>();
			createViewJson(jsonNode, list);
			vrj.setAvailability(list);
		}
		if("reliability".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> list = new ArrayList<>();
			createViewJson(jsonNode, list);
			vrj.setReliability(list);
		}
		if("endUsage".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> list = new ArrayList<>();
			createViewJson(jsonNode, list);
			vrj.setEndUsage(list);
		}
		if("security".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> list = new ArrayList<>();
			createViewJson(jsonNode, list);
			vrj.setSecurity(list);
		}
		if("compliance".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> list = new ArrayList<>();
			createViewJson(jsonNode, list);
			vrj.setCompliance(list);
		}
		if("alerts".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> list = new ArrayList<>();
			createViewJson(jsonNode, list);
			vrj.setAlerts(list);
		}
	  }
	  return vrj;
  }

  private static void createViewJson(JsonNode jsonNode, List<AllocatedDashboardViewJsonResponse> perf) {
	  for(JsonNode node: jsonNode) {
		  AllocatedDashboardViewJsonResponse pvj = AllocatedDashboardViewJsonResponse.from(node);
		  perf.add(pvj);
	  }
  }
  
  public static void updateFrom(JsonNode jsonNode, String serviceId, ViewJsonResponse viewJsonResponse, String key) {
//	  ViewJsonResponse vrj = ViewJsonResponse.builder().serviceId(serviceId).build();
	  List<AllocatedDashboardViewJsonResponse> newList = new ArrayList<>();
	  if(jsonNode.isArray()) {
		createViewJson(jsonNode, newList);
	  }
	 
	  if("performance".equalsIgnoreCase(key)) { 
		  List<AllocatedDashboardViewJsonResponse> existingViewJsonList = viewJsonResponse.getPerformance();
		  if(existingViewJsonList == null) {
			  existingViewJsonList = new ArrayList<>(); 
		  }
		  for(AllocatedDashboardViewJsonResponse p: newList) {
			  existingViewJsonList.add(p);
		  }
		  viewJsonResponse.setPerformance(existingViewJsonList);
	  }
	  if("availability".equalsIgnoreCase(key)) { 
		  List<AllocatedDashboardViewJsonResponse> existingViewJsonList = viewJsonResponse.getAvailability();
		  if(existingViewJsonList == null) {
			  existingViewJsonList = new ArrayList<>(); 
		  }
		  for(AllocatedDashboardViewJsonResponse p: newList) {
			  existingViewJsonList.add(p);
		  }
		  viewJsonResponse.setAvailability(existingViewJsonList);
	  }
	  if("reliability".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> existingViewJsonList = viewJsonResponse.getReliability();
			if(existingViewJsonList == null) {
				existingViewJsonList = new ArrayList<>(); 
			}
			for(AllocatedDashboardViewJsonResponse p: newList) {
				existingViewJsonList.add(p);
			}
			viewJsonResponse.setReliability(existingViewJsonList);
	  }
	  if("endUsage".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> existingViewJsonList = viewJsonResponse.getEndUsage();
			if(existingViewJsonList == null) {
				existingViewJsonList = new ArrayList<>(); 
			}
			for(AllocatedDashboardViewJsonResponse p: newList) {
				existingViewJsonList.add(p);
			}
			viewJsonResponse.setEndUsage(existingViewJsonList);
	  }
	  if("security".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> existingViewJsonList = viewJsonResponse.getSecurity();
			if(existingViewJsonList == null) {
				existingViewJsonList = new ArrayList<>(); 
			}
			for(AllocatedDashboardViewJsonResponse p: newList) {
				existingViewJsonList.add(p);
			}
			viewJsonResponse.setSecurity(existingViewJsonList);
	  }
	  if("compliance".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> existingViewJsonList = viewJsonResponse.getCompliance();
			if(existingViewJsonList == null) {
				existingViewJsonList = new ArrayList<>(); 
			}
			for(AllocatedDashboardViewJsonResponse p: newList) {
				existingViewJsonList.add(p);
			}
			viewJsonResponse.setCompliance(existingViewJsonList);
	  }
	  if("alerts".equalsIgnoreCase(key)) { 
			List<AllocatedDashboardViewJsonResponse> existingViewJsonList = viewJsonResponse.getAlerts();
			if(existingViewJsonList == null) {
				existingViewJsonList = new ArrayList<>(); 
			}
			for(AllocatedDashboardViewJsonResponse p: newList) {
				existingViewJsonList.add(p);
			}
			viewJsonResponse.setAlerts(existingViewJsonList);
	  }
  }
  
}
