package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CloudTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(Cloud.class);
    Cloud cloud1 = new Cloud();
    cloud1.setId(1L);
    Cloud cloud2 = new Cloud();
    cloud2.setId(cloud1.getId());
    assertThat(cloud1).isEqualTo(cloud2);
    cloud2.setId(2L);
    assertThat(cloud1).isNotEqualTo(cloud2);
    cloud1.setId(null);
    assertThat(cloud1).isNotEqualTo(cloud2);
  }
}
