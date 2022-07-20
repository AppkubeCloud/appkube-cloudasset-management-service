package com.synectiks.asset.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CredentialController {
	
	private static final Logger logger = LoggerFactory.getLogger(CredentialController.class);
	
	@GetMapping("/credential/{accountId}")
	public ResponseEntity<Map<String, String>> getCredential(@PathVariable Long accountId) {
		logger.info("Request to get credential. AccountId: "+accountId);
		Map<String, String> map = new HashMap<>();
		map.put("secureCreds", "ew0KCSJ2YXVsdElkIjogIjEyMzQ1NiIsDQoJImNsb3VkVHlwZSI6ICJhd3MiLA0KCSJhY2NvdW50SWQiOiAiMTIzNDU2Nzg5IiwNCgkiY3JlZGVudGlhbHMiOiBbDQoJCXsNCgkJCSJhY2Nlc3NLZXkiOiAiMTIzNDU2Nzg5IiwNCgkJCSJzZWNyZXRLZXkiOiAiaWRrSWxlMDk4a2xkbGtqZkIiDQoJCX0sDQoJCXsNCgkJCSJhY2Nlc3NLZXkiOiAiOTg3NjUiLA0KCQkJInNlY3JldEtleSI6ICJmdmJndDY3dWoiDQoJCX0NCgldDQp9");
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
		
}
