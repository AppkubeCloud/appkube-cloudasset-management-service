package com.synectiks.asset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceProviderCloudAccountDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(ServiceProviderCloudAccountDTO.class);
    ServiceProviderCloudAccountDTO serviceProviderCloudAccountDTO1 = new ServiceProviderCloudAccountDTO();
    serviceProviderCloudAccountDTO1.setId(1L);
    ServiceProviderCloudAccountDTO serviceProviderCloudAccountDTO2 = new ServiceProviderCloudAccountDTO();
    assertThat(serviceProviderCloudAccountDTO1).isNotEqualTo(serviceProviderCloudAccountDTO2);
    serviceProviderCloudAccountDTO2.setId(serviceProviderCloudAccountDTO1.getId());
    assertThat(serviceProviderCloudAccountDTO1).isEqualTo(serviceProviderCloudAccountDTO2);
    serviceProviderCloudAccountDTO2.setId(2L);
    assertThat(serviceProviderCloudAccountDTO1).isNotEqualTo(serviceProviderCloudAccountDTO2);
    serviceProviderCloudAccountDTO1.setId(null);
    assertThat(serviceProviderCloudAccountDTO1).isNotEqualTo(serviceProviderCloudAccountDTO2);
  }
}
