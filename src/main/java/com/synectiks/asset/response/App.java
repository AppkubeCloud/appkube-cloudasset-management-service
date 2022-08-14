package com.synectiks.asset.response;

import java.io.Serializable;

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
public class App implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Long serviceDetailId;
	private String description;
	private String associatedCloudElement;
	private String associatedClusterNamespace;
	private String associatedManagedCloudServiceLocation;
	private String associatedGlobalServiceLocation;
	private String serviceHostingType;
	private String associatedCloudElementId;
}
