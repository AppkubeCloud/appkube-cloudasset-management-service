package com.synectiks.asset.controller;

import com.synectiks.asset.business.domain.Department;
import com.synectiks.asset.business.service.DepartmentService;
import com.synectiks.asset.repository.DepartmentRepository;
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
public class DepartmentControllerTest {

    @Mock
    DepartmentService departmentService;

    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    DepartmentController departmentController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //    @Title("Add new Department")?
    @Test
    public void createDepartmentTest() throws URISyntaxException {

        // For return
        Department returnDepartment = new Department();
        returnDepartment.setId(1l);

        Department Department = new Department();
        Department.setName("demo");

        // Mocking
        when(departmentService.save(Department)).thenReturn(returnDepartment);

        // Calling controller
        ResponseEntity<Department> res = departmentController.create(Department);

        // assertions
        assertEquals(201, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an Department")
    @Test
    public void updateDepartmentTest() throws URISyntaxException {

        // Update input
        Department updateDepartment = new Department();
        updateDepartment.setId(1l);
        updateDepartment.setName("DEMO");

        // For return
        Department returnDepartment = new Department();
        returnDepartment.setId(1l);

        // Mocking
        when(departmentService.save(updateDepartment)).thenReturn(returnDepartment);
        when(departmentRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<Department> res = departmentController.update(1l, updateDepartment);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    //    @Title("Update an Department")
    @Test
    public void partialUpdateDepartmentTest() throws URISyntaxException {

        // Update input
        Department updateDepartment = new Department();
        updateDepartment.setId(1l);
        updateDepartment.setName("DEMO");

        // For return
        Optional<Department> returnDepartment = Optional.of(new Department());

        // Mocking
        when(departmentService.partialUpdate(updateDepartment)).thenReturn(returnDepartment);
        when(departmentRepository.existsById(1l)).thenReturn(true);

        // Calling controller
        ResponseEntity<Department> res = departmentController.partialUpdate(1l, updateDepartment);

        // assertions
        assertEquals(200, res.getStatusCode().value());
    }

    @Test
    public void getAllDepartmentsTest() throws IOException {

        // For return
        Department returnDepartment = new Department();
        returnDepartment.setId(1l);
        List<Department> list = new ArrayList<>();
        list.add(returnDepartment);

        // Mocking
        when(departmentService.findAll()).thenReturn(list);

        // Calling controller
        List<Department> resList = departmentController.getAll();

        // assertions
        assertEquals(1l, resList.get(0).getId());
    }

    @Test
    public void getDepartmentsTest() throws IOException {

        // For return
        Optional<Department> returnDepartment = Optional.of(new Department());
        returnDepartment.get().setId(1l);

        // Mocking
        when(departmentService.findOne(1l)).thenReturn(returnDepartment);

        // Calling controller
        ResponseEntity<Department> res = departmentController.get(1l);

        // assertions
        assertEquals(200, res.getStatusCode().value());
        assertEquals(1l, res.getBody().getId());
    }

    @Test
    public void deleteDepartmentsTest() throws IOException {

        // Mocking
        doNothing().when(departmentService).delete(1l);

        // Calling controller
        ResponseEntity<Void> res = departmentController.delete(1l);

        // assertions
        assertEquals(204, res.getStatusCode().value());
    }

    @Test
    public void searchDepartmentsTest() throws IOException {

        // For return
        Department returnDepartment = new Department();
        returnDepartment.setId(1l);
        List<Department> list = new ArrayList<>();
        list.add(returnDepartment);

        // Controller input
        Map<String, String> map = new HashMap<>();

        // Mocking
        when(departmentService.search(map)).thenReturn(list);

        // Calling controller
        List<Department> resList = departmentController.search(map);

        // assertions
        assertEquals(1l, resList.get(0).getId());
    }
}
