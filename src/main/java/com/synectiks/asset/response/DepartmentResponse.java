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
  
}
