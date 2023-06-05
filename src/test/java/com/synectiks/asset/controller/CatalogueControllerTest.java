package com.synectiks.asset.controller;

import com.synectiks.asset.business.domain.Catalogue;
import com.synectiks.asset.business.service.CatalogueService;
import com.synectiks.asset.repository.CatalogueRepository;
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
public class CatalogueControllerTest {

    @Mock
    CatalogueService catalogueService;

    @Mock
    CatalogueRepository catalogueRepository;

    @InjectMocks
    CatalogueController catalogueController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createCatalogueTest() throws URISyntaxException {

        // For return
        Catalogue returncatalogue = new Catalogue();
        returncatalogue.setId(1l);

        Catalogue catalogue = new Catalogue();

        // Mocking
        when(catalogueService.createCatalogue(catalogue)).thenReturn(returncatalogue);

        // Calling controller
        ResponseEntity<Catalogue> res = catalogueController.createCatalogue(catalogue);

        // Assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an catalogue")
    @Test
    public void updateCatalogueTest() throws URISyntaxException {

        // Update input
        Catalogue updateCatalogue = new Catalogue();
        updateCatalogue.setId(1l);

        // For return
        Catalogue returnCatalogue = new Catalogue();
        returnCatalogue.setId(1l);

        // Mocking
        when(catalogueService.updateCatalogue(updateCatalogue)).thenReturn(returnCatalogue);

        // Calling controller
        ResponseEntity<Catalogue> res = catalogueController.updateCatalogue(updateCatalogue);

        // Assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an catalogue")
    @Test
    public void partialUpdateCatalogueTest() throws URISyntaxException {

        // Update input
        Catalogue updateCatalogue = new Catalogue();
        updateCatalogue.setId(1l);

        // For return
        Optional<Catalogue> returnCatalogue = Optional.of(new Catalogue());

        // Mocking
        when(catalogueService.partialUpdateCatalogue(updateCatalogue)).thenReturn(returnCatalogue);

        // Calling controller
        ResponseEntity<Optional<Catalogue>> res = catalogueController.partialUpdateCatalogue(updateCatalogue);

        // Assertions
        assertEquals(200, res.getStatusCode().value());
    }

    @Test
    public void getAllCataloguesTest()  {

        // For return
        Catalogue returncatalogue = new Catalogue();
        returncatalogue.setId(1l);
        List<Catalogue> list = new ArrayList<>();
        list.add(returncatalogue);

        // Mocking
        when(catalogueService.getAllCatalogue()).thenReturn(list);

        // Calling controller
        ResponseEntity<List<Catalogue>> resList = catalogueController.getAllCatalogue();

        // Assertions
        assertEquals(200, resList.getStatusCode().value());
        assertEquals(1l, resList.getBody().get(0).getId());
    }

    @Test
    public void searchAllCataloguesTest()  {

        // For return
        Catalogue returncatalogue = new Catalogue();
        returncatalogue.setId(1l);

        List<Catalogue> list = new ArrayList<>();
        list.add(returncatalogue);

        // For input
        Map<String, String> map = new HashMap<>();

        // Mocking
        when(catalogueService.searchAllCatalogue(map)).thenReturn(list);

        // Calling controller
        ResponseEntity<List<Catalogue>> resList = catalogueController.searchAllCatalogue(map);

        // Assertions
        assertEquals(200, resList.getStatusCode().value());
        assertEquals(1l, resList.getBody().get(0).getId());
    }

    @Test
    public void getCataloguesTest() {

        // For return
        Optional<Catalogue> returnCatalogue = Optional.of(new Catalogue(1l, null));

        // Mocking
        when(catalogueService.getCatalogue(1l)).thenReturn(returnCatalogue);

        // Calling controller
        ResponseEntity<Catalogue> res = catalogueController.getCatalogue(1l);

        // Assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    @Test
    public void deleteCataloguesTest() {

        // For return
        Optional<Catalogue> returnCatalogue = Optional.of(new Catalogue(1l, null));

        // Mocking
        when(catalogueService.deleteCatalogue(1l)).thenReturn(returnCatalogue);

        // Calling controller
        ResponseEntity<Optional<Catalogue>> res = catalogueController.deleteCatalogue(1l);

        // Assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().get().getId());
    }

    @Test
    public void searchCataloguesTest() throws IOException {

        // For return
        Catalogue returncatalogue = new Catalogue();
        returncatalogue.setId(1l);

        // Controller input
        Map<String, String> map = new HashMap<>();

        // Mocking
        when(catalogueService.searchCatalogue(map)).thenReturn(returncatalogue);

        // Calling controller
        ResponseEntity<Catalogue> res = catalogueController.searchCatalogue(map);

        // Assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }
}
