package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductDeploymentTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ProductDeployment.class);
    ProductDeployment productDeployment1 = new ProductDeployment();
    productDeployment1.setId(1L);
    ProductDeployment productDeployment2 = new ProductDeployment();
    productDeployment2.setId(productDeployment1.getId());
    assertThat(productDeployment1).isEqualTo(productDeployment2);
    productDeployment2.setId(2L);
    assertThat(productDeployment1).isNotEqualTo(productDeployment2);
    productDeployment1.setId(null);
    assertThat(productDeployment1).isNotEqualTo(productDeployment2);
  }
}
