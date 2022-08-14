package com.synectiks.asset.business.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synectiks.asset.domain.ProductServiceDetail;
import com.synectiks.asset.repository.ProductServiceDetailRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ProductServiceDetailService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceDetailService.class);
		
	@Autowired
	ProductServiceDetailRepository productServiceDetailJsonRepository;
	
	public Optional<ProductServiceDetail> getProductServiceDetail(Long id) {
		logger.info("Get product service detail by id: {}", id);
		return productServiceDetailJsonRepository.findById(id);
	}
	
	public List<ProductServiceDetail> getAllProductServiceDetail() {
		logger.info("Get all product service detail");
		return productServiceDetailJsonRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<ProductServiceDetail> deleteProductServiceDetail(Long id) {
		logger.info("Delete product service detail by id: {}", id);
		Optional<ProductServiceDetail> oObj = getProductServiceDetail(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. Product service detail cannot be deleted", id);
			return oObj;
		}
		productServiceDetailJsonRepository.deleteById(id);
		return oObj;
	}
	
	public ProductServiceDetail createProductServiceDetail(ProductServiceDetail obj){
		logger.info("Create new product service detail");
		return productServiceDetailJsonRepository.save(obj);
	}
	
	public ProductServiceDetail updateProductServiceDetail(ProductServiceDetail obj){
		logger.info("Update product service detail. Id: {}", obj.getId());
		if(!productServiceDetailJsonRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ProductServiceDetail", "idnotfound");
		}
		return productServiceDetailJsonRepository.save(obj);
	}
	
	public Optional<ProductServiceDetail> partialUpdateProductServiceDetail(ProductServiceDetail obj){
		logger.info("Update product service detail partialy. Id: {}", obj.getId());
		if(!productServiceDetailJsonRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ProductServiceDetail", "idnotfound");
		}
		Optional<ProductServiceDetail> result = productServiceDetailJsonRepository.findById(obj.getId())
			.map(existingObj ->{
				return existingObj;
			})
			.map(productServiceDetailJsonRepository::save);
		return result;
	}
	
//	public ServiceDetailReportResponse searchAllServiceDetail(Map<String, String> obj) {
//		logger.info("Search service detail json");
//		Gson gson = new Gson(); 
//		String json = gson.toJson(obj); 
//		List<ServiceDetail> list = serviceDetailJsonRepository.findServiceDetails(json);
//		ServiceDetailReportResponse resp = ServiceDetailReportResponse.builder().build();
//		resp.setServices(list);
//		resp.setTotal(list.size());
//		return resp;
//	}
//	
//	public void updateViewJson(JsonNode node) {
//		String apiKey[] = {"performance","availability","reliability","endUsage","security","compliance","alerts"};
//		for(String key: apiKey) {
//			serviceDetailJsonRepository.updateViewJson(node.get("id").asText(), key);
//		}
//	}
	
//	public void createBulkData(JsonNode node) throws IOException {
//		ProductServiceDetail psd = ProductServiceDetail.builder()
//				.productName(node.get("productName").asText())
//				.env(node.get("env").asText())
//				.build();
//		MasterServiceDetailsResponse msdr = MasterServiceDetailsResponse.from(node);
//		psd.setMaster_services_details(msdr);
//		createProductServiceDetail(psd);
//	}
	


}
