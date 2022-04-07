package com.synectiks.asset.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class InputConfigDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InputConfigDTO.class);
        InputConfigDTO inputConfigDTO1 = new InputConfigDTO();
        inputConfigDTO1.setId(1L);
        InputConfigDTO inputConfigDTO2 = new InputConfigDTO();
        assertThat(inputConfigDTO1).isNotEqualTo(inputConfigDTO2);
        inputConfigDTO2.setId(inputConfigDTO1.getId());
        assertThat(inputConfigDTO1).isEqualTo(inputConfigDTO2);
        inputConfigDTO2.setId(2L);
        assertThat(inputConfigDTO1).isNotEqualTo(inputConfigDTO2);
        inputConfigDTO1.setId(null);
        assertThat(inputConfigDTO1).isNotEqualTo(inputConfigDTO2);
    }
}
