package com.synectiks.asset.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.OrganizationService;
import com.synectiks.asset.domain.Organization;

@RestController
@RequestMapping("/api")
public class OrganizationController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
	OrganizationService organizationService;
	
	@GetMapping("/org/org-id/{id}")
	private Organization getOrgById(@PathVariable Long id) {
		Organization org = organizationService.getOrgById(id);
		return org;
	}
	
	@GetMapping("/org/user-name/{userName}")
	private Organization getAllOrgUnitsByUserName(@PathVariable String userName) {
		Organization org = organizationService.getOrgByUserName(userName);
		return org;
	}
	
}
