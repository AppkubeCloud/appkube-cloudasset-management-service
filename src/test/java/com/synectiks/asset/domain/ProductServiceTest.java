package com.synectiks.asset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.synectiks.asset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ProductService.class);
    ProductService productService1 = new ProductService();
    productService1.setId(1L);
    ProductService productService2 = new ProductService();
    productService2.setId(productService1.getId());
    assertThat(productService1).isEqualTo(productService2);
    productService2.setId(2L);
    assertThat(productService1).isNotEqualTo(productService2);
    productService1.setId(null);
    assertThat(productService1).isNotEqualTo(productService2);
  }
}
