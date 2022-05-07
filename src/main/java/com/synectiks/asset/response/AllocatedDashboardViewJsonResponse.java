package com.synectiks.asset.response;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

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
public class AllocatedDashboardViewJsonResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String slug;
	private String uid;
	private Long dashboardCatalogueId;
	
	public static AllocatedDashboardViewJsonResponse from(JsonNode node) {
		return AllocatedDashboardViewJsonResponse.builder()
				.id(node.get("id").asText())
				.slug(node.get("slug").asText())
				.uid(node.get("uid").asText())
				.dashboardCatalogueId(node.get("dashboardCatalogueId").asLong())
				.build();
	}
}
