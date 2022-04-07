package com.synectiks.asset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CloudDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(CloudDTO.class);
    CloudDTO cloudDTO1 = new CloudDTO();
    cloudDTO1.setId(1L);
    CloudDTO cloudDTO2 = new CloudDTO();
    assertThat(cloudDTO1).isNotEqualTo(cloudDTO2);
    cloudDTO2.setId(cloudDTO1.getId());
    assertThat(cloudDTO1).isEqualTo(cloudDTO2);
    cloudDTO2.setId(2L);
    assertThat(cloudDTO1).isNotEqualTo(cloudDTO2);
    cloudDTO1.setId(null);
    assertThat(cloudDTO1).isNotEqualTo(cloudDTO2);
  }
}
