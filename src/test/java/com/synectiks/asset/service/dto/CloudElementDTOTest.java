package com.synectiks.asset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CloudElementDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(CloudElementDTO.class);
    CloudElementDTO cloudElementDTO1 = new CloudElementDTO();
    cloudElementDTO1.setId(1L);
    CloudElementDTO cloudElementDTO2 = new CloudElementDTO();
    assertThat(cloudElementDTO1).isNotEqualTo(cloudElementDTO2);
    cloudElementDTO2.setId(cloudElementDTO1.getId());
    assertThat(cloudElementDTO1).isEqualTo(cloudElementDTO2);
    cloudElementDTO2.setId(2L);
    assertThat(cloudElementDTO1).isNotEqualTo(cloudElementDTO2);
    cloudElementDTO1.setId(null);
    assertThat(cloudElementDTO1).isNotEqualTo(cloudElementDTO2);
  }
}
