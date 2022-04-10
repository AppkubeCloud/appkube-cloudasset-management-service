package com.synectiks.asset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductServiceDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(ProductServiceDTO.class);
    ProductServiceDTO productServiceDTO1 = new ProductServiceDTO();
    productServiceDTO1.setId(1L);
    ProductServiceDTO productServiceDTO2 = new ProductServiceDTO();
    assertThat(productServiceDTO1).isNotEqualTo(productServiceDTO2);
    productServiceDTO2.setId(productServiceDTO1.getId());
    assertThat(productServiceDTO1).isEqualTo(productServiceDTO2);
    productServiceDTO2.setId(2L);
    assertThat(productServiceDTO1).isNotEqualTo(productServiceDTO2);
    productServiceDTO1.setId(null);
    assertThat(productServiceDTO1).isNotEqualTo(productServiceDTO2);
  }
}
