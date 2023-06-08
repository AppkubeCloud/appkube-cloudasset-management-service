package com.synectiks.asset.controller;

import com.synectiks.asset.business.domain.Dashboard;
import com.synectiks.asset.business.domain.Organization;
import com.synectiks.asset.business.service.AwsService;
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
public class DashboardPreviewControllerTest {

    @Mock
    AwsService awsService;

    @InjectMocks
    DashboardPreviewController dashboardPreviewController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getOrganizationsTest() throws IOException {

        // For input
        Map<String, String> map = new HashMap<>();

        // For return
        Dashboard dashboard = new Dashboard();

        // Mocking
        when(awsService.getDashboardFromAwsS3(map)).thenReturn(dashboard);

        // Calling controller
        ResponseEntity<Dashboard> res = dashboardPreviewController.previewDashboard(map);

        // assertions
        assertEquals(200, res.getStatusCode().value());
    }


}
