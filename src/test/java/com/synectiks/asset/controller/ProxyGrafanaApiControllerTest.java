package com.synectiks.asset.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.business.service.ProxyGrafanaApiService;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

//@RunWith(SerenityRunner.class)
public class ProxyGrafanaApiControllerTest {

    @Mock
    ProxyGrafanaApiService proxyGrafanaApiService;

    @InjectMocks
    ProxyGrafanaApiController proxyGrafanaApiController;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    Map<String, String> map = new HashMap<>();

    @Test
    public void getGrafanaDataSourceTest() throws IOException {

        // To return
        String responseBody = "{\"name\":\"DEMO\"}";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(responseBody);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(responseEntity.getBody());

        // Mocking
        when(proxyGrafanaApiService.getGrafanaDatasource(map)).thenReturn(node);

        // Calling controller
        ResponseEntity<JsonNode> resList = proxyGrafanaApiController.getGrafanaDataSource(map);

        // assertions
        assertEquals(200, resList.getStatusCode().value());
    }

    @Test
    public void getGrafanaMasterDataSourceTest() throws IOException {

        // To return
        String responseBody = "{\"name\":\"DEMO\"}";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(responseBody);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(responseEntity.getBody());

        // Mocking
        when(proxyGrafanaApiService.getGrafanaMasterDatasource(map)).thenReturn(node);

        // Calling controller
        ResponseEntity<JsonNode> resList = proxyGrafanaApiController.getGrafanaMasterDataSource(map);

        // assertions
        assertEquals(200, resList.getStatusCode().value());
    }

    @Test
    public void importDashboardInGrafanaTest() throws IOException {

        // For input and return
        // Create a JsonNodeFactory
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode obj = nodeFactory.objectNode();
        obj.put("name", "DEMO");

        // Mocking
        when(proxyGrafanaApiService.importDashboardInGrafana(obj)).thenReturn(obj);

        // Calling controller
        ResponseEntity<ObjectNode> resList = proxyGrafanaApiController.importDashboardInGrafana(obj);

        // assertions
        assertEquals(200, resList.getStatusCode().value());
    }
}
