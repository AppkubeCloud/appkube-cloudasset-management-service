package com.synectiks.asset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceBillingDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(ServiceBillingDTO.class);
    ServiceBillingDTO serviceBillingDTO1 = new ServiceBillingDTO();
    serviceBillingDTO1.setId(1L);
    ServiceBillingDTO serviceBillingDTO2 = new ServiceBillingDTO();
    assertThat(serviceBillingDTO1).isNotEqualTo(serviceBillingDTO2);
    serviceBillingDTO2.setId(serviceBillingDTO1.getId());
    assertThat(serviceBillingDTO1).isEqualTo(serviceBillingDTO2);
    serviceBillingDTO2.setId(2L);
    assertThat(serviceBillingDTO1).isNotEqualTo(serviceBillingDTO2);
    serviceBillingDTO1.setId(null);
    assertThat(serviceBillingDTO1).isNotEqualTo(serviceBillingDTO2);
  }
}
