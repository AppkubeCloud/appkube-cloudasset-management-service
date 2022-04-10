package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceBillingTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ServiceBilling.class);
    ServiceBilling serviceBilling1 = new ServiceBilling();
    serviceBilling1.setId(1L);
    ServiceBilling serviceBilling2 = new ServiceBilling();
    serviceBilling2.setId(serviceBilling1.getId());
    assertThat(serviceBilling1).isEqualTo(serviceBilling2);
    serviceBilling2.setId(2L);
    assertThat(serviceBilling1).isNotEqualTo(serviceBilling2);
    serviceBilling1.setId(null);
    assertThat(serviceBilling1).isNotEqualTo(serviceBilling2);
  }
}
