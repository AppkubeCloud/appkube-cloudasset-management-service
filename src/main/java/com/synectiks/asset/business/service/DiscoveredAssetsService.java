package com.synectiks.asset.business.service;

import java.io.IOException;
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

import com.synectiks.asset.business.domain.DiscoveredAssets;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.DiscoveredAssetsRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

/**
 * Service Implementation for managing {@link DiscoveredAssets}.
 */
@Service
@Transactional
public class DiscoveredAssetsService {

    private final Logger log = LoggerFactory.getLogger(DiscoveredAssetsService.class);

    @Autowired
    private DiscoveredAssetsRepository discoveredAssetsRepository;

	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;
	
    public DiscoveredAssets save(DiscoveredAssets discoveredAssets) {
        log.debug("Request to save DiscoveredAssets : {}", discoveredAssets);
        return discoveredAssetsRepository.save(discoveredAssets);
    }

    public Optional<DiscoveredAssets> partialUpdate(DiscoveredAssets discoveredAssets) {
        log.debug("Request to partially update DiscoveredAssets : {}", discoveredAssets);

        return discoveredAssetsRepository
            .findById(discoveredAssets.getId())
            .map(existingDiscoveredAssets -> {
                if (discoveredAssets.getCloud() != null) {
                    existingDiscoveredAssets.setCloud(discoveredAssets.getCloud());
                }
                if (discoveredAssets.getElementId() != null) {
                    existingDiscoveredAssets.setElementId(discoveredAssets.getElementId());
                }
                if (discoveredAssets.getElementType() != null) {
                    existingDiscoveredAssets.setElementType(discoveredAssets.getElementType());
                }
                if (discoveredAssets.getLandingZone() != null) {
                    existingDiscoveredAssets.setLandingZone(discoveredAssets.getLandingZone());
                }
                if (discoveredAssets.getProductEnclave() != null) {
                    existingDiscoveredAssets.setProductEnclave(discoveredAssets.getProductEnclave());
                }
                if (discoveredAssets.getArn() != null) {
                    existingDiscoveredAssets.setArn(discoveredAssets.getArn());
                }
                if (discoveredAssets.getStatus() != null) {
                    existingDiscoveredAssets.setStatus(discoveredAssets.getStatus());
                }
                if (discoveredAssets.getTagStatus() != null) {
                    existingDiscoveredAssets.setTagStatus(discoveredAssets.getTagStatus());
                }
                if (discoveredAssets.getCreatedOn() != null) {
                    existingDiscoveredAssets.setCreatedOn(discoveredAssets.getCreatedOn());
                }
                if (discoveredAssets.getUpdatedOn() != null) {
                    existingDiscoveredAssets.setUpdatedOn(discoveredAssets.getUpdatedOn());
                }
                if (discoveredAssets.getUpdatedBy() != null) {
                    existingDiscoveredAssets.setUpdatedBy(discoveredAssets.getUpdatedBy());
                }
                if (discoveredAssets.getCreatedBy() != null) {
                    existingDiscoveredAssets.setCreatedBy(discoveredAssets.getCreatedBy());
                }

                return existingDiscoveredAssets;
            })
            .map(discoveredAssetsRepository::save);
    }

    @Transactional(readOnly = true)
    public List<DiscoveredAssets> findAll() {
        log.debug("Request to get all DiscoveredAssets");
        return discoveredAssetsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DiscoveredAssets> findOne(Long id) {
        log.debug("Request to get DiscoveredAssets : {}", id);
        return discoveredAssetsRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete DiscoveredAssets : {}", id);
        discoveredAssetsRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
	public List<DiscoveredAssets> search(Map<String, String> filter) throws IOException {
		log.debug("Request to get all DiscoveredAssets on given filters");

		DiscoveredAssets discoveredAssets = jsonAndObjectConverterUtil
				.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, DiscoveredAssets.class);

		// unset default value if createdOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.CREATED_ON))) {
			discoveredAssets.setCreatedOn(null);
		}
		// unset default value if updatedOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.UPDATED_ON))) {
			discoveredAssets.setUpdatedOn(null);
		}
		List<DiscoveredAssets> list = discoveredAssetsRepository.findAll(Example.of(discoveredAssets),
				Sort.by(Sort.Direction.ASC, "elementType"));

		return list;
	}

}
