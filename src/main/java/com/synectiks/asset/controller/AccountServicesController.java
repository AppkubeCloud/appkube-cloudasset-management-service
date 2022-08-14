package com.synectiks.asset.controller;

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

import com.synectiks.asset.business.service.AccountServicesService;
import com.synectiks.asset.domain.AccountServices;

@RestController
@RequestMapping("/api")
public class AccountServicesController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountServicesController.class);
	
	@Autowired
	AccountServicesService accountServicesService;
	
	@GetMapping("/account-services/{id}")
	public ResponseEntity<AccountServices> getAccountServices(@PathVariable Long id) {
		logger.info("Request to get account services. Id: "+id);
		Optional<AccountServices> odp = accountServicesService.getAccountServices(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("/account-services")
	public ResponseEntity<List<AccountServices>> getAllAccountServices() {
		logger.info("Request to get all account services");
		List<AccountServices> list = accountServicesService.getAllAccountServices();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/account-services/{id}")
	public ResponseEntity<Optional<AccountServices>> deleteAccountServices(@PathVariable Long id) {
		logger.info("Request to delete account-services by id: {}", id);
		Optional<AccountServices> oSpa = accountServicesService.deleteAccountServices(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/account-services")
	public ResponseEntity<AccountServices> createAccountServices(@RequestBody AccountServices obj){
		logger.info("Request to create new account-services");
		AccountServices spa = accountServicesService.createAccountServices(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/account-services")
	public ResponseEntity<AccountServices> updateAccountServices(@RequestBody AccountServices obj){
		logger.info("Request to update account-services");
		AccountServices spa = accountServicesService.updateAccountServices(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/account-services")
	public ResponseEntity<Optional<AccountServices>> partialAccountServices(@RequestBody AccountServices obj){
		logger.info("Request to partially update account-services");
		Optional<AccountServices> oSpa = accountServicesService.partialUpdateAccountServices(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/account-services/search")
	public ResponseEntity<List<AccountServices>> searchAllAccountServices(@RequestParam Map<String, String> obj){
		logger.info("Request to search account-services");
		List<AccountServices> asList = accountServicesService.searchAccountServices(obj);
		return ResponseEntity.status(HttpStatus.OK).body(asList);
	}
	
//	@PostMapping("/account-services/create-bulk-data")
//	public ResponseEntity<Void> createBulkData(@RequestBody ObjectNode objNode) throws IOException {
//		JsonNode objArray = objNode.get("masterServices");
//		for(JsonNode node: objArray) {
//			productServiceDetailService.createBulkData(node);
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(null);
//	}
	
//	@GetMapping("/service-detail/search-with-filter")
//	public ResponseEntity<ServiceDetailReportResponse> searchServiceDetailWithFilter(@RequestParam Map<String, String> obj){
//		ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(obj);
//		return ResponseEntity.status(HttpStatus.OK).body(sdr);
//	}
	
//	public Map<String, String> convertKeyValuePairToMap(String keyValuePairs) {
//		String dt[] = keyValuePairs.split("&");
//		Map<String, String> obj = new HashMap<>();
//		for(String pair: dt) {
//			String tmp [] = pair.split("=");
//			obj.put(tmp[0], tmp[1]);
//		}
//		return obj;
//	}
}
