package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartmentProductTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(DepartmentProduct.class);
    DepartmentProduct departmentProduct1 = new DepartmentProduct();
    departmentProduct1.setId(1L);
    DepartmentProduct departmentProduct2 = new DepartmentProduct();
    departmentProduct2.setId(departmentProduct1.getId());
    assertThat(departmentProduct1).isEqualTo(departmentProduct2);
    departmentProduct2.setId(2L);
    assertThat(departmentProduct1).isNotEqualTo(departmentProduct2);
    departmentProduct1.setId(null);
    assertThat(departmentProduct1).isNotEqualTo(departmentProduct2);
  }
}
