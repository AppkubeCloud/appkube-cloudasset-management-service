package com.synectiks.asset.response.catalogue;

import java.io.Serializable;
import java.util.List;

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
public class AppBlocks implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CommonMicroservices> commonMicroservices;
	private List<Generators> generators;
	private List<Workflows> workflows;
}
