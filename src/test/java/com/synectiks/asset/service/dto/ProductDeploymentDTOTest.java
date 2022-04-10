package com.synectiks.asset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductDeploymentDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(ProductDeploymentDTO.class);
    ProductDeploymentDTO productDeploymentDTO1 = new ProductDeploymentDTO();
    productDeploymentDTO1.setId(1L);
    ProductDeploymentDTO productDeploymentDTO2 = new ProductDeploymentDTO();
    assertThat(productDeploymentDTO1).isNotEqualTo(productDeploymentDTO2);
    productDeploymentDTO2.setId(productDeploymentDTO1.getId());
    assertThat(productDeploymentDTO1).isEqualTo(productDeploymentDTO2);
    productDeploymentDTO2.setId(2L);
    assertThat(productDeploymentDTO1).isNotEqualTo(productDeploymentDTO2);
    productDeploymentDTO1.setId(null);
    assertThat(productDeploymentDTO1).isNotEqualTo(productDeploymentDTO2);
  }
}
