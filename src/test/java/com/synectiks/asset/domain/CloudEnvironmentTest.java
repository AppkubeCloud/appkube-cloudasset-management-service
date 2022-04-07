package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CloudEnvironmentTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(CloudEnvironment.class);
    CloudEnvironment cloudEnvironment1 = new CloudEnvironment();
    cloudEnvironment1.setId(1L);
    CloudEnvironment cloudEnvironment2 = new CloudEnvironment();
    cloudEnvironment2.setId(cloudEnvironment1.getId());
    assertThat(cloudEnvironment1).isEqualTo(cloudEnvironment2);
    cloudEnvironment2.setId(2L);
    assertThat(cloudEnvironment1).isNotEqualTo(cloudEnvironment2);
    cloudEnvironment1.setId(null);
    assertThat(cloudEnvironment1).isNotEqualTo(cloudEnvironment2);
  }
}
