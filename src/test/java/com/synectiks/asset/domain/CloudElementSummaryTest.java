package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.business.domain.CloudElementSummary;
import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CloudElementSummaryTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(CloudElementSummary.class);
    CloudElementSummary cloudElement1 = new CloudElementSummary();
    cloudElement1.setId(1L);
    CloudElementSummary cloudElement2 = new CloudElementSummary();
    cloudElement2.setId(cloudElement1.getId());
    assertThat(cloudElement1).isEqualTo(cloudElement2);
    cloudElement2.setId(2L);
    assertThat(cloudElement1).isNotEqualTo(cloudElement2);
    cloudElement1.setId(null);
    assertThat(cloudElement1).isNotEqualTo(cloudElement2);
  }
}
