package com.synectiks.asset.controller;

import com.synectiks.asset.business.domain.DeploymentEnvironment;
import com.synectiks.asset.business.service.DeploymentEnvironmentService;
import com.synectiks.asset.repository.DeploymentEnvironmentRepository;
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
public class DeploymentEnvironmentControllerTest {

    @Mock
    DeploymentEnvironmentService deploymentEnvironmentService;

    @Mock
    DeploymentEnvironmentRepository deploymentEnvironmentRepository;

    @InjectMocks
    DeploymentEnvironmentController deploymentEnvironmentController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //    @Title("Add new DeploymentEnvironment")?
    @Test
    public void createDeploymentEnvironmentTest() throws URISyntaxException {

        // For return
        DeploymentEnvironment returnDeploymentEnvironment = new DeploymentEnvironment();
        returnDeploymentEnvironment.setId(1l);

        DeploymentEnvironment DeploymentEnvironment = new DeploymentEnvironment();
        DeploymentEnvironment.setName("demo");

        // Mocking
        when(deploymentEnvironmentService.save(DeploymentEnvironment)).thenReturn(returnDeploymentEnvironment);

        // Calling controller
        ResponseEntity<DeploymentEnvironment> res = deploymentEnvironmentController.create(DeploymentEnvironment);

        // assertions
        assertEquals(201, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an DeploymentEnvironment")
    @Test
    public void updateDeploymentEnvironmentTest() throws URISyntaxException {

        // Update input
        DeploymentEnvironment updateDeploymentEnvironment = new DeploymentEnvironment();
        updateDeploymentEnvironment.setId(1l);
        updateDeploymentEnvironment.setName("DEMO");

        // For return
        DeploymentEnvironment returnDeploymentEnvironment = new DeploymentEnvironment();
        returnDeploymentEnvironment.setId(1l);

        // Mocking
        when(deploymentEnvironmentService.save(updateDeploymentEnvironment)).thenReturn(returnDeploymentEnvironment);
        when(deploymentEnvironmentRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<DeploymentEnvironment> res = deploymentEnvironmentController.update(1l, updateDeploymentEnvironment);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an DeploymentEnvironment")
    @Test
    public void partialUpdateDeploymentEnvironmentTest() throws URISyntaxException {

        // Update input
        DeploymentEnvironment updateDeploymentEnvironment = new DeploymentEnvironment();
        updateDeploymentEnvironment.setId(1l);
        updateDeploymentEnvironment.setName("DEMO");

        // For return
        Optional<DeploymentEnvironment> returnDeploymentEnvironment = Optional.of(new DeploymentEnvironment());

        // Mocking
        when(deploymentEnvironmentService.partialUpdate(updateDeploymentEnvironment)).thenReturn(returnDeploymentEnvironment);
        when(deploymentEnvironmentRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<DeploymentEnvironment> res = deploymentEnvironmentController.partialUpdate(1l, updateDeploymentEnvironment);

        // assertions
        assertEquals(200, res.getStatusCode().value());
    }

    @Test
    public void getAllDeploymentEnvironmentsTest() throws IOException {

        // For return
        DeploymentEnvironment returnDeploymentEnvironment = new DeploymentEnvironment();
        returnDeploymentEnvironment.setId(1l);
        List<DeploymentEnvironment> list = new ArrayList<>();
        list.add(returnDeploymentEnvironment);

        // Mocking
        when(deploymentEnvironmentService.findAll()).thenReturn(list);

        // Calling controller
        List<DeploymentEnvironment> resList = deploymentEnvironmentController.getAll();

        // assertions
        assertEquals(1l, resList.get(0).getId());
    }

    @Test
    public void getDeploymentEnvironmentsTest() throws IOException {

        // For return
        Optional<DeploymentEnvironment> returnDeploymentEnvironment = Optional.of(new DeploymentEnvironment(1l, "demo", null));

        // Mocking
        when(deploymentEnvironmentService.findOne(1l)).thenReturn(returnDeploymentEnvironment);

        // Calling controller
        ResponseEntity<DeploymentEnvironment> res = deploymentEnvironmentController.get(1l);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    @Test
    public void deleteDeploymentEnvironmentsTest() throws IOException {

        // Mocking
        doNothing().when(deploymentEnvironmentService).delete(1l);

        // Calling controller
        ResponseEntity<Void> res = deploymentEnvironmentController.delete(1l);

        // assertions
        assertEquals(204, res.getStatusCode().value());
    }

    @Test
    public void searchDeploymentEnvironmentsTest() throws IOException {

        // For return
        DeploymentEnvironment returnDeploymentEnvironment = new DeploymentEnvironment();
        returnDeploymentEnvironment.setId(1l);
        List<DeploymentEnvironment> list = new ArrayList<>();
        list.add(returnDeploymentEnvironment);

        // Controller input
        Map<String, String> map = new HashMap<>();

        // Mocking
        when(deploymentEnvironmentService.search(map)).thenReturn(list);

        // Calling controller
        List<DeploymentEnvironment> resList = deploymentEnvironmentController.search(map);

        // assertions
        assertEquals(1l, resList.get(0).getId());
    }
}
