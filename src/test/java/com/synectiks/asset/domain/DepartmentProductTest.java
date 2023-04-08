package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.business.domain.DepartmentProductEnv;
import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartmentProductTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(DepartmentProductEnv.class);
    DepartmentProductEnv departmentProduct1 = new DepartmentProductEnv();
    departmentProduct1.setId(1L);
    DepartmentProductEnv departmentProduct2 = new DepartmentProductEnv();
    departmentProduct2.setId(departmentProduct1.getId());
    assertThat(departmentProduct1).isEqualTo(departmentProduct2);
    departmentProduct2.setId(2L);
    assertThat(departmentProduct1).isNotEqualTo(departmentProduct2);
    departmentProduct1.setId(null);
    assertThat(departmentProduct1).isNotEqualTo(departmentProduct2);
  }
}
