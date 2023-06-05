package com.synectiks.asset.controller;

import com.synectiks.asset.business.domain.MicroService;
import com.synectiks.asset.business.service.MicroServiceService;
import com.synectiks.asset.repository.MicroServiceRepository;
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
public class MicroServiceControllerTest {

    @Mock
    MicroServiceService microServiceService;

    @Mock
    MicroServiceRepository microServiceRepository;

    @InjectMocks
    MicroServiceController microServiceController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //    @Title("Add new MicroService")?
    @Test
    public void createMicroServiceTest() throws URISyntaxException {

        // For return
        MicroService returnMicroService = new MicroService();
        returnMicroService.setId(1l);

        MicroService MicroService = new MicroService();
        MicroService.setName("demo");

        // Mocking
        when(microServiceService.save(MicroService)).thenReturn(returnMicroService);

        // Calling controller
        ResponseEntity<MicroService> res = microServiceController.create(MicroService);

        // assertions
        assertEquals(201, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an MicroService")
    @Test
    public void updateMicroServiceTest() throws URISyntaxException {

        // Update input
        MicroService updateMicroService = new MicroService();
        updateMicroService.setId(1l);
        updateMicroService.setName("DEMO");

        // For return
        MicroService returnMicroService = new MicroService();
        returnMicroService.setId(1l);

        // Mocking
        when(microServiceService.save(updateMicroService)).thenReturn(returnMicroService);
        when(microServiceRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<MicroService> res = microServiceController.update(1l, updateMicroService);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an MicroService")
    @Test
    public void partialUpdateMicroServiceTest() throws URISyntaxException {

        // Update input
        MicroService updateMicroService = new MicroService();
        updateMicroService.setId(1l);
        updateMicroService.setName("DEMO");

        // For return
        Optional<MicroService> returnMicroService = Optional.of(new MicroService());

        // Mocking
        when(microServiceService.partialUpdate(updateMicroService)).thenReturn(returnMicroService);
        when(microServiceRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<MicroService> res = microServiceController.partialUpdate(1l, updateMicroService);

        // assertions
        assertEquals(200, res.getStatusCode().value());
    }

    @Test
    public void getAllMicroServicesTest() {

        // For return
        MicroService returnMicroService = new MicroService();
        returnMicroService.setId(1l);
        List<MicroService> list = new ArrayList<>();
        list.add(returnMicroService);

        // Mocking
        when(microServiceService.findAll()).thenReturn(list);

        // Calling controller
        List<MicroService> resList = microServiceController.getAll();

        // assertions
        assertEquals(1l, resList.get(0).getId());
    }

    @Test
    public void getMicroServicesTest() {

        // For return
        Optional<MicroService> returnMicroService = Optional.of(new MicroService());
        returnMicroService.get().setId(1l);

        // Mocking
        when(microServiceService.findOne(1l)).thenReturn(returnMicroService);

        // Calling controller
        ResponseEntity<MicroService> res = microServiceController.get(1l);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    @Test
    public void deleteMicroServicesTest() {

        // Mocking
        doNothing().when(microServiceService).delete(1l);

        // Calling controller
        ResponseEntity<Void> res = microServiceController.delete(1l);

        // assertions
        assertEquals(204, res.getStatusCode().value());
    }

    @Test
    public void searchMicroServicesTest() throws IOException {

        // For return
        MicroService returnMicroService = new MicroService();
        returnMicroService.setId(1l);
        List<MicroService> list = new ArrayList<>();
        list.add(returnMicroService);

        // Controller input
        Map<String, String> map = new HashMap<>();

        // Mocking
        when(microServiceService.search(map)).thenReturn(list);

        // Calling controller
        ResponseEntity<List<MicroService>> resList = microServiceController.search(map);

        // assertions
        assertEquals(200, resList.getStatusCode().value());
        assertEquals(1l, resList.getBody().get(0).getId());
    }
}
