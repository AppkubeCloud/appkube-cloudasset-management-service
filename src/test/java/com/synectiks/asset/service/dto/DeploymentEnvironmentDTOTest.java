package com.synectiks.asset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeploymentEnvironmentDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(DeploymentEnvironmentDTO.class);
    DeploymentEnvironmentDTO deploymentEnvironmentDTO1 = new DeploymentEnvironmentDTO();
    deploymentEnvironmentDTO1.setId(1L);
    DeploymentEnvironmentDTO deploymentEnvironmentDTO2 = new DeploymentEnvironmentDTO();
    assertThat(deploymentEnvironmentDTO1).isNotEqualTo(deploymentEnvironmentDTO2);
    deploymentEnvironmentDTO2.setId(deploymentEnvironmentDTO1.getId());
    assertThat(deploymentEnvironmentDTO1).isEqualTo(deploymentEnvironmentDTO2);
    deploymentEnvironmentDTO2.setId(2L);
    assertThat(deploymentEnvironmentDTO1).isNotEqualTo(deploymentEnvironmentDTO2);
    deploymentEnvironmentDTO1.setId(null);
    assertThat(deploymentEnvironmentDTO1).isNotEqualTo(deploymentEnvironmentDTO2);
  }
}
