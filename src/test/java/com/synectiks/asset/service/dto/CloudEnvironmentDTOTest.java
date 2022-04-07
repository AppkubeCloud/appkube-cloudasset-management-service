package com.synectiks.asset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CloudEnvironmentDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(CloudEnvironmentDTO.class);
    CloudEnvironmentDTO cloudEnvironmentDTO1 = new CloudEnvironmentDTO();
    cloudEnvironmentDTO1.setId(1L);
    CloudEnvironmentDTO cloudEnvironmentDTO2 = new CloudEnvironmentDTO();
    assertThat(cloudEnvironmentDTO1).isNotEqualTo(cloudEnvironmentDTO2);
    cloudEnvironmentDTO2.setId(cloudEnvironmentDTO1.getId());
    assertThat(cloudEnvironmentDTO1).isEqualTo(cloudEnvironmentDTO2);
    cloudEnvironmentDTO2.setId(2L);
    assertThat(cloudEnvironmentDTO1).isNotEqualTo(cloudEnvironmentDTO2);
    cloudEnvironmentDTO1.setId(null);
    assertThat(cloudEnvironmentDTO1).isNotEqualTo(cloudEnvironmentDTO2);
  }
}
