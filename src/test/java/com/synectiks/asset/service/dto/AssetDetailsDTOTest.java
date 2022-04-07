package com.synectiks.asset.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class AssetDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetDetailsDTO.class);
        AssetDetailsDTO assetDetailsDTO1 = new AssetDetailsDTO();
        assetDetailsDTO1.setId(1L);
        AssetDetailsDTO assetDetailsDTO2 = new AssetDetailsDTO();
        assertThat(assetDetailsDTO1).isNotEqualTo(assetDetailsDTO2);
        assetDetailsDTO2.setId(assetDetailsDTO1.getId());
        assertThat(assetDetailsDTO1).isEqualTo(assetDetailsDTO2);
        assetDetailsDTO2.setId(2L);
        assertThat(assetDetailsDTO1).isNotEqualTo(assetDetailsDTO2);
        assetDetailsDTO1.setId(null);
        assertThat(assetDetailsDTO1).isNotEqualTo(assetDetailsDTO2);
    }
}
