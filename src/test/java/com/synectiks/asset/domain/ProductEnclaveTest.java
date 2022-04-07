package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductEnclaveTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ProductEnclave.class);
    ProductEnclave productEnclave1 = new ProductEnclave();
    productEnclave1.setId(1L);
    ProductEnclave productEnclave2 = new ProductEnclave();
    productEnclave2.setId(productEnclave1.getId());
    assertThat(productEnclave1).isEqualTo(productEnclave2);
    productEnclave2.setId(2L);
    assertThat(productEnclave1).isNotEqualTo(productEnclave2);
    productEnclave1.setId(null);
    assertThat(productEnclave1).isNotEqualTo(productEnclave2);
  }
}
