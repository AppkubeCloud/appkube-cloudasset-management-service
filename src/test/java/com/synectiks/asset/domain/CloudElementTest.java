package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CloudElementTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(CloudElement.class);
    CloudElement cloudElement1 = new CloudElement();
    cloudElement1.setId(1L);
    CloudElement cloudElement2 = new CloudElement();
    cloudElement2.setId(cloudElement1.getId());
    assertThat(cloudElement1).isEqualTo(cloudElement2);
    cloudElement2.setId(2L);
    assertThat(cloudElement1).isNotEqualTo(cloudElement2);
    cloudElement1.setId(null);
    assertThat(cloudElement1).isNotEqualTo(cloudElement2);
  }
}
