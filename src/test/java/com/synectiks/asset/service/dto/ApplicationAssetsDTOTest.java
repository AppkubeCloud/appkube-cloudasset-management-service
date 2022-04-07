package com.synectiks.asset.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class ApplicationAssetsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationAssetsDTO.class);
        ApplicationAssetsDTO applicationAssetsDTO1 = new ApplicationAssetsDTO();
        applicationAssetsDTO1.setId(1L);
        ApplicationAssetsDTO applicationAssetsDTO2 = new ApplicationAssetsDTO();
        assertThat(applicationAssetsDTO1).isNotEqualTo(applicationAssetsDTO2);
        applicationAssetsDTO2.setId(applicationAssetsDTO1.getId());
        assertThat(applicationAssetsDTO1).isEqualTo(applicationAssetsDTO2);
        applicationAssetsDTO2.setId(2L);
        assertThat(applicationAssetsDTO1).isNotEqualTo(applicationAssetsDTO2);
        applicationAssetsDTO1.setId(null);
        assertThat(applicationAssetsDTO1).isNotEqualTo(applicationAssetsDTO2);
    }
}
