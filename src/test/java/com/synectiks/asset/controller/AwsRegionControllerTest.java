package com.synectiks.asset.controller;

import com.synectiks.asset.business.domain.Dashboard;
import com.synectiks.asset.business.service.AwsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

//@RunWith(SerenityRunner.class)
public class AwsRegionControllerTest {

    @Mock
    AwsService awsService;

    @InjectMocks
    AwsRegionController awsRegionController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getOrganizationsTest() throws IOException {

        // For input
        Map<String, String> map = new HashMap<>();

        // Calling controller
        List<String> res = awsRegionController.getAwsRegions(map);

        // assertions
        assertNotNull(res);
    }


}
