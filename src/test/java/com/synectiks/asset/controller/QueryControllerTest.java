package com.synectiks.asset.controller;

import com.synectiks.asset.business.domain.Department;
import com.synectiks.asset.business.domain.Organization;
import com.synectiks.asset.business.service.OrganizationService;
import com.synectiks.asset.repository.OrganizationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

//@RunWith(SerenityRunner.class)
public class QueryControllerTest {

    @Mock
    OrganizationService organizationService;

    @InjectMocks
    QueryController queryController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getOrganizationDepartmentTest() throws IOException {

        // For return
        Set<Department> departments = new HashSet<>();
        departments.add(new Department());

        Organization returnOrganization = new Organization();
        returnOrganization.setId(1l);
        returnOrganization.setDepartments(departments);

        // Mocking
        when(organizationService.findOne(1l)).thenReturn(Optional.of(returnOrganization));

        // Calling controller
        ResponseEntity<Set<Department>> resList = queryController.getOrganizationDepartment(1l);

        // assertions
        assertEquals(200, resList.getStatusCode().value());
    }


}
