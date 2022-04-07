package com.synectiks.asset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductEnclaveDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(ProductEnclaveDTO.class);
    ProductEnclaveDTO productEnclaveDTO1 = new ProductEnclaveDTO();
    productEnclaveDTO1.setId(1L);
    ProductEnclaveDTO productEnclaveDTO2 = new ProductEnclaveDTO();
    assertThat(productEnclaveDTO1).isNotEqualTo(productEnclaveDTO2);
    productEnclaveDTO2.setId(productEnclaveDTO1.getId());
    assertThat(productEnclaveDTO1).isEqualTo(productEnclaveDTO2);
    productEnclaveDTO2.setId(2L);
    assertThat(productEnclaveDTO1).isNotEqualTo(productEnclaveDTO2);
    productEnclaveDTO1.setId(null);
    assertThat(productEnclaveDTO1).isNotEqualTo(productEnclaveDTO2);
  }
}
