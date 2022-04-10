package com.synectiks.asset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductBillingDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(ProductBillingDTO.class);
    ProductBillingDTO productBillingDTO1 = new ProductBillingDTO();
    productBillingDTO1.setId(1L);
    ProductBillingDTO productBillingDTO2 = new ProductBillingDTO();
    assertThat(productBillingDTO1).isNotEqualTo(productBillingDTO2);
    productBillingDTO2.setId(productBillingDTO1.getId());
    assertThat(productBillingDTO1).isEqualTo(productBillingDTO2);
    productBillingDTO2.setId(2L);
    assertThat(productBillingDTO1).isNotEqualTo(productBillingDTO2);
    productBillingDTO1.setId(null);
    assertThat(productBillingDTO1).isNotEqualTo(productBillingDTO2);
  }
}
