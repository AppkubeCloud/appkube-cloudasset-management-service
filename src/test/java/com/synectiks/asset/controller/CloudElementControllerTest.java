package com.synectiks.asset.controller;

import com.synectiks.asset.business.domain.CloudElement;
import com.synectiks.asset.business.domain.CloudEnvironment;
import com.synectiks.asset.business.service.CloudElementService;
import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.repository.CloudElementRepository;
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
public class CloudElementControllerTest {

    @Mock
    CloudElementService cloudElementService;

    @Mock
    CloudEnvironmentService cloudEnvironmentService;

    @Mock
    CloudElementRepository cloudElementRepository;

    @InjectMocks
    CloudElementController cloudElementController;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //    @Title("Add new CloudElement")?
    @Test
    public void createCloudElementTest() throws URISyntaxException {

        // For return
        CloudElement returnCloudElement = new CloudElement();
        returnCloudElement.setId(1l);


//        returnCloudEnvironment.get().setId(2l);

        // To create
        CloudElement cloudElement = new CloudElement();
        CloudEnvironment cloudEnvironment = new CloudEnvironment();
        cloudEnvironment.setId(2l);

        cloudElement.setCloudEnvironment(cloudEnvironment);

        // Mocking
        when(cloudElementService.save(cloudElement)).thenReturn(returnCloudElement);
        when(cloudEnvironmentService.findOne(2L)).thenReturn(Optional.of(cloudEnvironment));

        // Calling controller
        ResponseEntity<CloudElement> res = cloudElementController.create(cloudElement);

        // assertions
        assertEquals(201, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an CloudElement")
    @Test
    public void updateCloudElementTest() throws URISyntaxException {

        // Update input
        CloudElement updateCloudElement = new CloudElement();
        updateCloudElement.setId(1l);

        // For return
        CloudElement returnCloudElement = new CloudElement();
        returnCloudElement.setId(1l);

        // Mocking
        when(cloudElementService.save(updateCloudElement)).thenReturn(returnCloudElement);
        when(cloudElementRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<CloudElement> res = cloudElementController.update(1l, updateCloudElement);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an CloudElement")
    @Test
    public void partialUpdateCloudElementTest() throws URISyntaxException {

        // Update input
        CloudElement updateCloudElement = new CloudElement();
        updateCloudElement.setId(1l);

        // For return
        Optional<CloudElement> returnCloudElement = Optional.of(new CloudElement());

        // Mocking
        when(cloudElementService.partialUpdate(updateCloudElement)).thenReturn(returnCloudElement);
        when(cloudElementRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<CloudElement> res = cloudElementController.partialUpdate(1l, updateCloudElement);

        // assertions
        assertEquals(200, res.getStatusCode().value());
    }

    @Test
    public void getAllCloudElementsTest() throws IOException {

        // For return
        CloudElement returnCloudElement = new CloudElement();
        returnCloudElement.setId(1l);
        List<CloudElement> list = new ArrayList<>();
        list.add(returnCloudElement);

        // Mocking
        when(cloudElementService.findAll()).thenReturn(list);

        // Calling controller
        List<CloudElement> resList = cloudElementController.getAll();

        // assertions
        assertEquals(1l, resList.get(0).getId());
    }

    @Test
    public void getCloudElementsTest() throws IOException {

        // For return
        CloudElement returnCloudElement = new CloudElement();
        returnCloudElement.setId(1l);

        // Mocking
        when(cloudElementService.findOne(1l)).thenReturn(Optional.of(returnCloudElement));

        // Calling controller
        ResponseEntity<CloudElement> res = cloudElementController.get(1l);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    @Test
    public void deleteCloudElementsTest() throws IOException {

        // Mocking
        doNothing().when(cloudElementService).delete(1l);

        // Calling controller
        ResponseEntity<Void> res = cloudElementController.delete(1l);

        // assertions
        assertEquals(204, res.getStatusCode().value());
    }

    @Test
    public void searchCloudElementsTest() throws IOException {

        // For return
        CloudElement returnCloudElement = new CloudElement();
        returnCloudElement.setId(1l);
        List<CloudElement> list = new ArrayList<>();
        list.add(returnCloudElement);

        // Controller input
        Map<String, String> map = new HashMap<>();

        // Mocking
        when(cloudElementService.search(map)).thenReturn(list);

        // Calling controller
        ResponseEntity<List<CloudElement>> resList = cloudElementController.search(map);

        // assertions
        assertEquals(200, resList.getStatusCode().value());
        assertEquals(1l, resList.getBody().get(0).getId());
    }
}
