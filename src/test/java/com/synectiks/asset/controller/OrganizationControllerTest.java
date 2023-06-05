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
public class OrganizationControllerTest {

    @Mock
    OrganizationService organizationService;

    @Mock
    OrganizationRepository organizationRepository;

    @InjectMocks
    OrganizationController organizationController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //    @Title("Add new organization")?
    @Test
    public void createOrganizationTest() throws URISyntaxException {

//      For return
        Organization returnOrganization = new Organization();
        returnOrganization.setId(1l);

        Organization organization = new Organization();
        organization.setName("demo");

        // Mocking
        when(organizationService.save(organization)).thenReturn(returnOrganization);

        // Calling controller
        ResponseEntity<Organization> res = organizationController.createOrganization(organization);

        // assertions
        assertEquals(201, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an organization")
    @Test
    public void updateOrganizationTest() throws URISyntaxException {

        // Update input
        Organization updateOrganization = new Organization();
        updateOrganization.setId(1l);
        updateOrganization.setName("DEMO");

        // For return
        Organization returnOrganization = new Organization();
        returnOrganization.setId(1l);

        // Mocking
        when(organizationService.save(updateOrganization)).thenReturn(returnOrganization);
        when(organizationRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<Organization> res = organizationController.updateOrganization(1l, updateOrganization);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an organization")
    @Test
    public void partialUpdateOrganizationTest() throws URISyntaxException {

        // Update input
        Organization updateOrganization = new Organization();
        updateOrganization.setId(1l);
        updateOrganization.setName("DEMO");

        // For return
        Optional<Organization> returnOrganization = Optional.of(new Organization());

        // Mocking
        when(organizationService.partialUpdate(updateOrganization)).thenReturn(returnOrganization);
        when(organizationRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<Organization> res = organizationController.partialUpdateOrganization(1l, updateOrganization);

        // assertions
        assertEquals(200, res.getStatusCode().value());
    }

    @Test
    public void getAllOrganizationsTest() throws IOException {

        // For return
        Organization returnOrganization = new Organization();
        returnOrganization.setId(1l);
        List<Organization> list = new ArrayList<>();
        list.add(returnOrganization);

        // Mocking
        when(organizationService.findAll()).thenReturn(list);

        // Calling controller
        List<Organization> resList = organizationController.getAllOrganizations();

        // assertions
        assertEquals(1l, resList.get(0).getId());
    }

    @Test
    public void getOrganizationsTest() throws IOException {

        // For return
        Optional<Organization> returnOrganization = Optional.of(new Organization(1l, "demo", null));

        // Mocking
        when(organizationService.findOne(1l)).thenReturn(returnOrganization);

        // Calling controller
        ResponseEntity<Organization> res = organizationController.getOrganization(1l);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    @Test
    public void deleteOrganizationsTest() throws IOException {

        // Mocking
        doNothing().when(organizationService).delete(1l);

        // Calling controller
        ResponseEntity<Void> res = organizationController.deleteOrganization(1l);

        // assertions
        assertEquals(204, res.getStatusCode().value());
    }

    @Test
    public void searchOrganizationsTest() throws IOException {

        // For return
        Organization returnOrganization = new Organization();
        returnOrganization.setId(1l);
        List<Organization> list = new ArrayList<>();
        list.add(returnOrganization);

        // Controller input
        Map<String, String> map = new HashMap<>();

        // Mocking
        when(organizationService.search(map)).thenReturn(list);

        // Calling controller
        List<Organization> resList = organizationController.search(map);

        // assertions
        assertEquals(1l, resList.get(0).getId());
    }
}
