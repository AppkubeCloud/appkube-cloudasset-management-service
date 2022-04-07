package com.synectiks.asset.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class InputsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InputsDTO.class);
        InputsDTO inputsDTO1 = new InputsDTO();
        inputsDTO1.setId(1L);
        InputsDTO inputsDTO2 = new InputsDTO();
        assertThat(inputsDTO1).isNotEqualTo(inputsDTO2);
        inputsDTO2.setId(inputsDTO1.getId());
        assertThat(inputsDTO1).isEqualTo(inputsDTO2);
        inputsDTO2.setId(2L);
        assertThat(inputsDTO1).isNotEqualTo(inputsDTO2);
        inputsDTO1.setId(null);
        assertThat(inputsDTO1).isNotEqualTo(inputsDTO2);
    }
}
