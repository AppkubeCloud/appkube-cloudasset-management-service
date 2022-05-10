package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
public class DepartmentResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String name;
  private String status;
  private int totalProduct;
  private List<ProductResponse> productList;
  
  public static DepartmentResponse from(Department department) {
	  return DepartmentResponse.builder()
			  .id(department.getId())
			  .name(department.getName())
			  .status(department.getStatus())
			  .build();
  }
  
  public static DepartmentResponse from(Long id, String name, String status) {
	  DepartmentResponse dr = DepartmentResponse.builder().build();
	  if(id != null) {
		  dr.setId(id);
	  }
	  if(!StringUtils.isBlank(name)) {
		  dr.setName(name);
	  }
	  if(!StringUtils.isBlank(status)) {
		  dr.setStatus(status);
	  }
	  return dr;
  }
  
}
