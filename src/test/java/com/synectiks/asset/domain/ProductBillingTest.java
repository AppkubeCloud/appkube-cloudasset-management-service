package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductBillingTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ProductBilling.class);
    ProductBilling productBilling1 = new ProductBilling();
    productBilling1.setId(1L);
    ProductBilling productBilling2 = new ProductBilling();
    productBilling2.setId(productBilling1.getId());
    assertThat(productBilling1).isEqualTo(productBilling2);
    productBilling2.setId(2L);
    assertThat(productBilling1).isNotEqualTo(productBilling2);
    productBilling1.setId(null);
    assertThat(productBilling1).isNotEqualTo(productBilling2);
  }
}
