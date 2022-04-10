package com.synectiks.asset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartmentProductDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(DepartmentProductDTO.class);
    DepartmentProductDTO departmentProductDTO1 = new DepartmentProductDTO();
    departmentProductDTO1.setId(1L);
    DepartmentProductDTO departmentProductDTO2 = new DepartmentProductDTO();
    assertThat(departmentProductDTO1).isNotEqualTo(departmentProductDTO2);
    departmentProductDTO2.setId(departmentProductDTO1.getId());
    assertThat(departmentProductDTO1).isEqualTo(departmentProductDTO2);
    departmentProductDTO2.setId(2L);
    assertThat(departmentProductDTO1).isNotEqualTo(departmentProductDTO2);
    departmentProductDTO1.setId(null);
    assertThat(departmentProductDTO1).isNotEqualTo(departmentProductDTO2);
  }
}
