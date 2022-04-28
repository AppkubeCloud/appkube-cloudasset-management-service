package com.synectiks.asset.response.catalogue;

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
public class CloudDashboard implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String description;
	private String associatedDataSourceType;
	private String associatedDataType;
	private String associatedSLAType;
	private String associatedCloud;
	private String associatedCloudElementType;
	private String jsonLocation;
}
  

