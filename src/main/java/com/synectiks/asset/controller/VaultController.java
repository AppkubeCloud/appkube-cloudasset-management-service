package com.synectiks.asset.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.VaultService;
import com.synectiks.asset.domain.Vault;
import com.synectiks.asset.response.Credential;
import com.synectiks.asset.response.VaultResponse;

@RestController
@RequestMapping("/api")
public class VaultController {
	
	private static final Logger logger = LoggerFactory.getLogger(VaultController.class);
	
	@Autowired
	private VaultService vaultService;
	
	@GetMapping("/vault/{id}")
	public ResponseEntity<Vault> getVault(@PathVariable Long id) {
		logger.info("Request to get vault. Id: "+id);
		Optional<Vault> odp = vaultService.getVault(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/vault")
	public ResponseEntity<List<Vault>> getAllVault() {
		logger.info("Request to get all vaults");
		List<Vault> list = vaultService.getAllVault();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/vault/{id}")
	public ResponseEntity<Optional<Vault>> deleteVault(@PathVariable Long id) {
		logger.info("Request to delete vault by id: {}", id);
		Optional<Vault> oSpa = vaultService.deleteVault(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/vault")
	public ResponseEntity<Vault> createVault(@RequestBody Vault obj){
		logger.info("Request to create new vault");
		Vault spa = vaultService.createVault(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/vault")
	public ResponseEntity<Vault> updateVault(@RequestBody Vault obj){
		logger.info("Request to update vault");
		Vault spa = vaultService.updateVault(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/vault")
	public ResponseEntity<Optional<Vault>> partialUpdateVault(@RequestBody Vault obj){
		logger.info("Request to partially update vault");
		Optional<Vault> oSpa = vaultService.partialUpdateVault(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/vault/search")
	public ResponseEntity<List<Vault>> searchAllVault(@RequestParam Map<String, String> obj){
		logger.info("Request to search vaults");
		List<Vault> list = vaultService.searchAllVault(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@GetMapping("/credential/account-id/{accountId}")
	public ResponseEntity<VaultResponse> getCredential(@PathVariable String accountId) {
		logger.info("Request to get credential. AccountId: "+accountId);
		Map<String, String> obj = new HashMap<>();
		obj.put("accountId", accountId);
		List<Vault> list = vaultService.searchAllVault(obj);
		VaultResponse vr = null;
		
		if(list.size() > 0) {
			vr = VaultResponse.builder().accountId(accountId).build();
		}else {
			vr = VaultResponse.builder().build();
		}
		
		List<Credential> crList = new ArrayList<>();
		vr.setCredentials(crList);
		for(Vault v: list) {
			Credential cr = Credential.builder()
					.vaultId(v.getId())
					.cloudType(v.getCloudType())
					.accessKey(v.getAccessKey())
					.secretKey(v.getSecretKey())
					.region(v.getRegion())
					.build();
			vr.getCredentials().add(cr);
		}
		return ResponseEntity.status(HttpStatus.OK).body(vr);
	}
	
}
