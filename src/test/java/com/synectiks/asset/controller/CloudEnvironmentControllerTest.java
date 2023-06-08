package com.synectiks.asset.controller;

import com.synectiks.asset.business.domain.CloudEnvironment;
import com.synectiks.asset.business.domain.Department;
import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.business.service.DepartmentService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.CloudEnvironmentRepository;
import com.synectiks.asset.response.query.EnvironmentCountsDto;
import com.synectiks.asset.response.query.EnvironmentDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

//@RunWith(SerenityRunner.class)
public class CloudEnvironmentControllerTest {

    @Mock
    DepartmentService departmentService;

    @Mock
    CloudEnvironmentService cloudEnvironmentService;

    @Mock
    CloudEnvironmentRepository cloudEnvironmentRepository;

    @InjectMocks
    CloudEnvironmentController cloudEnvironmentController;

    @Autowired
    EnvironmentCountsDto environmentCountsDto;

    @Autowired
    EnvironmentDto environmentDto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //    @Title("Add new CloudEnvironment")?
    @Test
    public void createCloudEnvironmentTest() throws URISyntaxException, IOException {

        // For return
        CloudEnvironment returnCloudEnvironment = new CloudEnvironment();
        returnCloudEnvironment.setId(1l);

        // To create
        Department department = new Department();
        CloudEnvironment cloudEnvironment = new CloudEnvironment();

        // Set data to cloudEnvironment
        cloudEnvironment.setDepartment(department);
        cloudEnvironment.setCloud(Constants.AWS);
        cloudEnvironment.setRoleArn("arn:aws:iam::0000000000:role/CrossAccount");

        // Mocking
        when(cloudEnvironmentService.save(cloudEnvironment)).thenReturn(returnCloudEnvironment);
        when(cloudEnvironmentService.findOne(2L)).thenReturn(Optional.of(cloudEnvironment));

        // Calling controller
        ResponseEntity<CloudEnvironment> res = cloudEnvironmentController.createCloudEnvironment(cloudEnvironment);

        // assertions
        assertEquals(201, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an CloudEnvironment")
    @Test
    public void updateCloudEnvironmentTest() throws URISyntaxException {

        // Update input
        CloudEnvironment updateCloudEnvironment = new CloudEnvironment();
        updateCloudEnvironment.setId(1l);

        // For return
        CloudEnvironment returnCloudEnvironment = new CloudEnvironment();
        returnCloudEnvironment.setId(1l);

        // Mocking
        when(cloudEnvironmentService.save(updateCloudEnvironment)).thenReturn(returnCloudEnvironment);
        when(cloudEnvironmentRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<CloudEnvironment> res = cloudEnvironmentController.updateCloudEnvironment(1l, updateCloudEnvironment);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an CloudEnvironment")
    @Test
    public void partialUpdateCloudEnvironmentTest() throws URISyntaxException, IOException {

        // Update input
        CloudEnvironment updateCloudEnvironment = new CloudEnvironment();
        updateCloudEnvironment.setRoleArn("arn:aws:iam::0000000000:role/CrossAccount");
        updateCloudEnvironment.setId(1l);

        // For return
        Optional<CloudEnvironment> returnCloudEnvironment = Optional.of(new CloudEnvironment());

        // Mocking
        when(cloudEnvironmentService.partialUpdate(updateCloudEnvironment)).thenReturn(returnCloudEnvironment);
        when(cloudEnvironmentRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<CloudEnvironment> res = cloudEnvironmentController.partialUpdateCloudEnvironment(1l, updateCloudEnvironment);

        // assertions
        assertEquals(200, res.getStatusCode().value());
    }

    @Test
    public void getAllCloudEnvironmentsTest() throws IOException {

        // For return
        CloudEnvironment returnCloudEnvironment = new CloudEnvironment();
        returnCloudEnvironment.setId(1l);
        List<CloudEnvironment> list = new ArrayList<>();
        list.add(returnCloudEnvironment);

        // Mocking
        when(cloudEnvironmentService.findAll()).thenReturn(list);

        // Calling controller
        List<CloudEnvironment> resList = cloudEnvironmentController.getAllCloudEnvironments();

        // assertions
        assertEquals(1l, resList.get(0).getId());
    }

    @Test
    public void getCloudEnvironmentsTest() throws IOException {

        // For return
        CloudEnvironment returnCloudEnvironment = new CloudEnvironment();
        returnCloudEnvironment.setId(1l);

        // Mocking
        when(cloudEnvironmentService.findOne(1l)).thenReturn(Optional.of(returnCloudEnvironment));

        // Calling controller
        ResponseEntity<CloudEnvironment> res = cloudEnvironmentController.getCloudEnvironment(1l);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    @Test
    public void deleteCloudEnvironmentsTest() throws IOException {

        // Mocking
        doNothing().when(cloudEnvironmentService).delete(1l);

        // Calling controller
        ResponseEntity<Void> res = cloudEnvironmentController.deleteCloudEnvironment(1l);

        // assertions
        assertEquals(204, res.getStatusCode().value());
    }

    @Test
    public void searchCloudEnvironmentsTest() throws IOException {

        // For return
        CloudEnvironment returnCloudEnvironment = new CloudEnvironment();
        returnCloudEnvironment.setId(1l);
        List<CloudEnvironment> list = new ArrayList<>();
        list.add(returnCloudEnvironment);

        // Controller input
        Map<String, String> map = new HashMap<>();

        // Mocking
        when(cloudEnvironmentService.search(map)).thenReturn(list);

        // Calling controller
        List<CloudEnvironment> resList = cloudEnvironmentController.search(map);

        // assertions
        assertEquals(1l, resList.get(0).getId());
    }


    @Test
    public void getCloudEnvironmentsCountTest() throws IOException, URISyntaxException {

        List<EnvironmentCountsDto> list = new ArrayList<>();
        list.add(environmentCountsDto);

        // Mocking
        when(cloudEnvironmentService.getEnvironmentCounts(1l)).thenReturn(list);

        // Calling controller
        ResponseEntity<List<EnvironmentCountsDto>> resList = cloudEnvironmentController.getEnvironmentCounts(1l);

        // assertions
        assertEquals(200, resList.getStatusCode().value());
    }

    @Test
    public void getCloudEnvironmentsCountsTest() throws IOException, URISyntaxException {

        // Mocking
        when(cloudEnvironmentService.getEnvironmentCounts(Constants.AWS, 1l)).thenReturn(environmentCountsDto);

        // Calling controller
        ResponseEntity<EnvironmentCountsDto> resList = cloudEnvironmentController.getEnvironmentCounts(Constants.AWS, 1l);

        // assertions
        assertEquals(200, resList.getStatusCode().value());
    }

    @Test
    public void getEnvironmentsSummeryTest() throws IOException, URISyntaxException {

        // To return
        List<EnvironmentDto> list = new ArrayList<>();
        list.add(environmentDto);

        // Mocking
        when(cloudEnvironmentService.getEnvironmentSummary( 1l)).thenReturn(list);

        // Calling controller
        ResponseEntity<List<EnvironmentDto>> resList = cloudEnvironmentController.getEnvironmentSummary( 1l);

        // assertions
        assertEquals(200, resList.getStatusCode().value());
    }

    @Test
    public void getEnvironmentSummaryTest() throws IOException, URISyntaxException {

        // To return
        List<EnvironmentDto> list = new ArrayList<>();
        list.add(environmentDto);

        // Mocking
        when(cloudEnvironmentService.getEnvironmentSummary(Constants.AWS, 1l)).thenReturn(list);

        // Calling controller
        ResponseEntity<List<EnvironmentDto>> resList = cloudEnvironmentController.getEnvironmentSummary(Constants.AWS, 1l);

        // assertions
        assertEquals(200, resList.getStatusCode().value());
    }

}
