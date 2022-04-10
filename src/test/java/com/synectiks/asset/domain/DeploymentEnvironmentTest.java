package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeploymentEnvironmentTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(DeploymentEnvironment.class);
    DeploymentEnvironment deploymentEnvironment1 = new DeploymentEnvironment();
    deploymentEnvironment1.setId(1L);
    DeploymentEnvironment deploymentEnvironment2 = new DeploymentEnvironment();
    deploymentEnvironment2.setId(deploymentEnvironment1.getId());
    assertThat(deploymentEnvironment1).isEqualTo(deploymentEnvironment2);
    deploymentEnvironment2.setId(2L);
    assertThat(deploymentEnvironment1).isNotEqualTo(deploymentEnvironment2);
    deploymentEnvironment1.setId(null);
    assertThat(deploymentEnvironment1).isNotEqualTo(deploymentEnvironment2);
  }
}
