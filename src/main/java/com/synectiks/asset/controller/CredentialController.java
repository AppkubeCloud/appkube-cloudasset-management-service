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
		map.put("secureCreds", "ew0KCSJ2YXVsdElkIjoiMTIzNDU2IiwNCgkiY2xvdWRUeXBlIjogImF3cyIsDQoJImFjY291bnRJZCI6ICIxMjM0NTY3ODkiLA0KCSJjcmVkZW50aWFscyI6IHsNCgkJImFjY2Vzc0tleSI6ICIxMjM0NTY3ODkiLA0KCQkic2VjcmV0S2V5IjogImlka0lsZTA5OGtsZGxramZCIg0KCX0NCn0");
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
		
}
