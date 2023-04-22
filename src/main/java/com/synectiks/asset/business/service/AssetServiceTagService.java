package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synectiks.asset.business.domain.AssetServiceTag;
import com.synectiks.asset.business.domain.DiscoveredAssets;
import com.synectiks.asset.business.domain.ServiceAllocation;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.AssetServiceTagRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

@Service
public class AssetServiceTagService {

	private final Logger logger = LoggerFactory.getLogger(AssetServiceTagService.class);

	@Autowired
	private AssetServiceTagRepository assetServiceTagRepository;
	
	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;

	@Autowired
	private ServiceAllocationService serviceAllocationService;
	
	@Autowired
	private DiscoveredAssetsService discoveredAssetsService;
	
	
	public AssetServiceTag save(AssetServiceTag assetServiceTag) {
		logger.debug("Request to save AssetServiceTag : {}", assetServiceTag);
		AssetServiceTag d = assetServiceTagRepository.save(assetServiceTag);
		if (d != null) {
			DiscoveredAssets ds = assetServiceTag.getDiscoveredAsset();
			ds.setTagStatus(Constants.TAGGED);
			discoveredAssetsService.partialUpdate(ds);
		}
		return d;
	}

	public Optional<AssetServiceTag> partialUpdate(AssetServiceTag assetServiceTag) {
		logger.debug("Request to partially update AssetServiceTag : {}", assetServiceTag);

		return assetServiceTagRepository.findById(assetServiceTag.getId()).map(existingDepartment -> {
			if (assetServiceTag.getDiscoveredAsset() != null) {
				Optional<DiscoveredAssets> od = discoveredAssetsService.findOne(assetServiceTag.getDiscoveredAsset().getId());
				if(od.isPresent()) {
					existingDepartment.setDiscoveredAsset(od.get());
				}
			}
			if (assetServiceTag.getServiceAllocation() != null) {
				Optional<ServiceAllocation> osa = serviceAllocationService.findOne(assetServiceTag.getServiceAllocation().getId());
				if(osa.isPresent()) {
					existingDepartment.setServiceAllocation(osa.get());
				}
			}
			if (assetServiceTag.getTag() != null) {
				existingDepartment.setTag(assetServiceTag.getTag());
			}
			if (assetServiceTag.getCreatedOn() != null) {
				existingDepartment.setCreatedOn(assetServiceTag.getCreatedOn());
			}
			if (assetServiceTag.getCreatedBy() != null) {
				existingDepartment.setCreatedBy(assetServiceTag.getCreatedBy());
			}
			if (assetServiceTag.getUpdatedOn() != null) {
				existingDepartment.setUpdatedOn(assetServiceTag.getUpdatedOn());
			}
			if (assetServiceTag.getUpdatedBy() != null) {
				existingDepartment.setUpdatedBy(assetServiceTag.getUpdatedBy());
			}
			
			return existingDepartment;
		}).map(assetServiceTagRepository::save);
	}

	@Transactional(readOnly = true)
	public List<AssetServiceTag> findAll() {
		logger.debug("Request to get all AssetServiceTags");
		List<AssetServiceTag> list = assetServiceTagRepository.findAll();
		return list;
	}

	@Transactional(readOnly = true)
	public Optional<AssetServiceTag> findOne(Long id) {
		logger.debug("Request to get AssetServiceTag : {}", id);
		Optional<AssetServiceTag> osa = assetServiceTagRepository.findById(id);
		return osa;
		
	}

	public void delete(Long id) throws IOException {
		logger.debug("Request to delete AssetServiceTag : {}", id);
		Optional<AssetServiceTag> oas = findOne(id);
		if(oas.isPresent()) {
			DiscoveredAssets ds = oas.get().getDiscoveredAsset();
			assetServiceTagRepository.deleteById(id);
			Map<String, String> filter = new HashMap<>();
			filter.put(Constants.DISCOVERED_ASSET_ID, String.valueOf(oas.get().getDiscoveredAsset().getId()));
			List<AssetServiceTag> list = search(filter);
			if (list != null && list.size() == 0) {
				ds.setTagStatus(null);
				discoveredAssetsService.save(ds);
			}
		}
	}

	@Transactional(readOnly = true)
	public List<AssetServiceTag> search(Map<String, String> filter) throws IOException {
		logger.debug("Request to get all assetServiceTag on given filters");

		DiscoveredAssets discoveredAssets = null;
		if (!StringUtils.isBlank(filter.get(Constants.DISCOVERED_ASSET_ID))) {
			discoveredAssets = new DiscoveredAssets();
			discoveredAssets.setId(Long.parseLong(filter.get(Constants.DISCOVERED_ASSET_ID)));
			discoveredAssets.setCreatedOn(null);
			discoveredAssets.setUpdatedOn(null);
			filter.remove(Constants.DISCOVERED_ASSET_ID);
		}
		ServiceAllocation serviceAllocation = null;
		if (!StringUtils.isBlank(filter.get(Constants.SERVICE_ALLOCATION_ID))) {
			serviceAllocation = new ServiceAllocation();
			serviceAllocation.setId(Long.parseLong(filter.get(Constants.SERVICE_ALLOCATION_ID)));
			serviceAllocation.setCreatedOn(null);
			serviceAllocation.setUpdatedOn(null);
			filter.remove(Constants.SERVICE_ALLOCATION_ID);
		}
		
		AssetServiceTag assetServiceTag = jsonAndObjectConverterUtil.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, AssetServiceTag.class);

		// unset default value if createdOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.CREATED_ON))) {
			assetServiceTag.setCreatedOn(null);
		}
		// unset default value if updatedOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.UPDATED_ON))) {
			assetServiceTag.setUpdatedOn(null);
		}
		if (discoveredAssets != null) {
			assetServiceTag.setDiscoveredAsset(discoveredAssets);
		}
		if (serviceAllocation != null) {
			assetServiceTag.setServiceAllocation(serviceAllocation);
		}
		List<AssetServiceTag> list = assetServiceTagRepository.findAll(Example.of(assetServiceTag), Sort.by(Sort.Direction.DESC, "id"));
		
		return list;
	}
	
	
	@Transactional(readOnly = true)
	public List<AssetServiceTag> searchTag(Map<String, String> filter) throws IOException {
		logger.debug("Request to get all tags on given filters");

		DiscoveredAssets discoveredAssets = null;
		if (!StringUtils.isBlank(filter.get(Constants.DISCOVERED_ASSET_ID))) {
			discoveredAssets = new DiscoveredAssets();
			discoveredAssets.setId(Long.parseLong(filter.get(Constants.DISCOVERED_ASSET_ID)));
			discoveredAssets.setCreatedOn(null);
			discoveredAssets.setUpdatedOn(null);
			filter.remove(Constants.DISCOVERED_ASSET_ID);
		}
		
		List<ServiceAllocation> saList = serviceAllocationService.search(filter);
		ServiceAllocation serviceAllocation = null;
		if(saList != null && saList.size() > 0) {
			serviceAllocation = saList.get(0);
		}
		
		AssetServiceTag assetServiceTag = new AssetServiceTag();
		assetServiceTag.setCreatedOn(null);
		assetServiceTag.setUpdatedOn(null);
		assetServiceTag.setDiscoveredAsset(discoveredAssets);
		assetServiceTag.setServiceAllocation(serviceAllocation);

		// unset default value if createdOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.CREATED_ON))) {
			assetServiceTag.setCreatedOn(null);
		}
		// unset default value if updatedOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.UPDATED_ON))) {
			assetServiceTag.setUpdatedOn(null);
		}
		
		List<AssetServiceTag> list = assetServiceTagRepository.findAll(Example.of(assetServiceTag), Sort.by(Sort.Direction.DESC, "id"));
		
		return list;
	}

}
