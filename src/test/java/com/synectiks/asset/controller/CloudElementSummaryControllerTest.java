package com.synectiks.asset.controller;

import com.synectiks.asset.business.domain.CloudElementSummary;
import com.synectiks.asset.business.domain.CloudEnvironment;
import com.synectiks.asset.business.service.CloudElementSummaryService;
import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.repository.CloudElementSummaryRepository;
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
import static org.mockito.Mockito.when;

//@RunWith(SerenityRunner.class)
public class CloudElementSummaryControllerTest {

    @Mock
    CloudElementSummaryService cloudElementSummaryService;

    @Mock
    CloudEnvironmentService cloudEnvironmentService;

    @Mock
    CloudElementSummaryRepository CloudElementSummaryRepository;

    @InjectMocks
    CloudElementSummaryController CloudElementSummaryController;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //    @Title("Add new CloudElementSummary")?
    @Test
    public void createCloudElementSummaryTest() throws URISyntaxException {

        // For return
        CloudElementSummary returnCloudElementSummary = new CloudElementSummary();
        returnCloudElementSummary.setId(1l);

        // To create
        CloudElementSummary CloudElementSummary = new CloudElementSummary();
        CloudEnvironment cloudEnvironment = new CloudEnvironment();
        cloudEnvironment.setId(2l);

        CloudElementSummary.setCloudEnvironment(cloudEnvironment);

        // Mocking
        when(cloudElementSummaryService.createCloudElement(CloudElementSummary)).thenReturn(returnCloudElementSummary);
        when(cloudEnvironmentService.findOne(2L)).thenReturn(Optional.of(cloudEnvironment));

        // Calling controller
        ResponseEntity<CloudElementSummary> res = CloudElementSummaryController.createCloudElement(CloudElementSummary);

        // assertions
        assertEquals(201, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }


    //    @Title("Update an CloudElementSummary")
    @Test
    public void partialUpdateCloudElementSummaryTest() throws URISyntaxException {

        // Update input
        CloudElementSummary updateCloudElementSummary = new CloudElementSummary();
        updateCloudElementSummary.setId(1l);

        // For return
        Optional<CloudElementSummary> returnCloudElementSummary = Optional.of(new CloudElementSummary());
        returnCloudElementSummary.get().setId(2l);
        // Mocking
        when(cloudElementSummaryService.partialUpdateCloudElement(updateCloudElementSummary)).thenReturn(returnCloudElementSummary);
        when(CloudElementSummaryRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<Optional<CloudElementSummary>> res = CloudElementSummaryController.partialUpdateCloudElement(1l, updateCloudElementSummary);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(2l, res.getBody().get().getId());
    }

    @Test
    public void getAllCloudElementSummarysTest() throws IOException {

        // For return
        CloudElementSummary returnCloudElementSummary = new CloudElementSummary();
        returnCloudElementSummary.setId(1l);
        List<CloudElementSummary> list = new ArrayList<>();
        list.add(returnCloudElementSummary);

        // Mocking
        when(cloudElementSummaryService.getAllCloudElement()).thenReturn(list);

        // Calling controller
        ResponseEntity<List<CloudElementSummary>> resList = CloudElementSummaryController.getAllCloudElement();

        // assertions
        assertEquals(200, resList.getStatusCode().value());
        assertEquals(1l, resList.getBody().get(0).getId());
    }

    @Test
    public void getCloudElementSummaryTest() throws IOException {

        // For return
        CloudElementSummary returnCloudElementSummary = new CloudElementSummary();
        returnCloudElementSummary.setId(1l);

        // Mocking
        when(cloudElementSummaryService.getCloudElement(1l)).thenReturn(Optional.of(returnCloudElementSummary));

        // Calling controller
        ResponseEntity<CloudElementSummary> res = CloudElementSummaryController.getCloudElement(1l);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    @Test
    public void deleteCloudElementSummarysTest() throws IOException {

        // For return
        Optional<CloudElementSummary> returnCatalogue = Optional.of(new CloudElementSummary());

        // Mocking
        when(cloudElementSummaryService.deleteCloudElement(1l)).thenReturn(returnCatalogue);

        // Calling controller
        ResponseEntity<Optional<CloudElementSummary>> res = CloudElementSummaryController.deleteCloudElement(1l);

        // assertions
        assertEquals(200, res.getStatusCode().value());
    }

    @Test
    public void searchCloudElementSummaryTest() throws IOException {

        // For return
        CloudElementSummary returnCloudElementSummary = new CloudElementSummary();
        returnCloudElementSummary.setId(1l);
        List<CloudElementSummary> list = new ArrayList<>();
        list.add(returnCloudElementSummary);

        // Controller input
        Map<String, String> map = new HashMap<>();

        // Mocking
        when(cloudElementSummaryService.searchAllCloudElement(map)).thenReturn(list);

        // Calling controller
        ResponseEntity<List<CloudElementSummary>> resList = CloudElementSummaryController.searchAllCloudElement(map);

        // assertions
        assertEquals(200, resList.getStatusCode().value());
        assertEquals(1l, resList.getBody().get(0).getId());
    }
}
