package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

import com.synectiks.asset.domain.Department;

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
public class DepartmentWiseAnaliticResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrganizationResponse organization;
	
	
}
