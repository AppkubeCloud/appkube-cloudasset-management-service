package com.synectiks.asset.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class CloudAssetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CloudAssetDTO.class);
        CloudAssetDTO cloudAssetDTO1 = new CloudAssetDTO();
        cloudAssetDTO1.setId(1L);
        CloudAssetDTO cloudAssetDTO2 = new CloudAssetDTO();
        assertThat(cloudAssetDTO1).isNotEqualTo(cloudAssetDTO2);
        cloudAssetDTO2.setId(cloudAssetDTO1.getId());
        assertThat(cloudAssetDTO1).isEqualTo(cloudAssetDTO2);
        cloudAssetDTO2.setId(2L);
        assertThat(cloudAssetDTO1).isNotEqualTo(cloudAssetDTO2);
        cloudAssetDTO1.setId(null);
        assertThat(cloudAssetDTO1).isNotEqualTo(cloudAssetDTO2);
    }
}
