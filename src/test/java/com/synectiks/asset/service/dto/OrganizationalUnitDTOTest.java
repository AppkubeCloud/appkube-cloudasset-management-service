package com.synectiks.asset.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class OrganizationalUnitDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationalUnitDTO.class);
        OrganizationalUnitDTO organizationalUnitDTO1 = new OrganizationalUnitDTO();
        organizationalUnitDTO1.setId(1L);
        OrganizationalUnitDTO organizationalUnitDTO2 = new OrganizationalUnitDTO();
        assertThat(organizationalUnitDTO1).isNotEqualTo(organizationalUnitDTO2);
        organizationalUnitDTO2.setId(organizationalUnitDTO1.getId());
        assertThat(organizationalUnitDTO1).isEqualTo(organizationalUnitDTO2);
        organizationalUnitDTO2.setId(2L);
        assertThat(organizationalUnitDTO1).isNotEqualTo(organizationalUnitDTO2);
        organizationalUnitDTO1.setId(null);
        assertThat(organizationalUnitDTO1).isNotEqualTo(organizationalUnitDTO2);
    }
}
