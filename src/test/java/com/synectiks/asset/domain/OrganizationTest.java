package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.business.domain.Organization;
import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(Organization.class);
    Organization organization1 = new Organization();
    organization1.setId(1L);
    Organization organization2 = new Organization();
    organization2.setId(organization1.getId());
    assertThat(organization1).isEqualTo(organization2);
    organization2.setId(2L);
    assertThat(organization1).isNotEqualTo(organization2);
    organization1.setId(null);
    assertThat(organization1).isNotEqualTo(organization2);
  }
}
