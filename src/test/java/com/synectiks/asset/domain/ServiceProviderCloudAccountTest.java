package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceProviderCloudAccountTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ServiceProviderCloudAccount.class);
    ServiceProviderCloudAccount serviceProviderCloudAccount1 = new ServiceProviderCloudAccount();
    serviceProviderCloudAccount1.setId(1L);
    ServiceProviderCloudAccount serviceProviderCloudAccount2 = new ServiceProviderCloudAccount();
    serviceProviderCloudAccount2.setId(serviceProviderCloudAccount1.getId());
    assertThat(serviceProviderCloudAccount1).isEqualTo(serviceProviderCloudAccount2);
    serviceProviderCloudAccount2.setId(2L);
    assertThat(serviceProviderCloudAccount1).isNotEqualTo(serviceProviderCloudAccount2);
    serviceProviderCloudAccount1.setId(null);
    assertThat(serviceProviderCloudAccount1).isNotEqualTo(serviceProviderCloudAccount2);
  }
}
