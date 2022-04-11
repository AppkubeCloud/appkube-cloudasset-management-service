package com.synectiks.asset.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synectiks.asset.domain.Dashboard;

/**
 * Application constants.
 */
public final class Constants {

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ACTIVE = "ACTIVE";
    public static final String DEACTIVE = "DEACTIVE";
    public static final String DEFAULT_AWS_REGION = "us-east-1";
    public static final String DEFAULT_AWS_BUCKET = "xformation.synectiks.com";
    public static final String STATUS_READY_TO_ENABLE = "READY_TO_ENABLE";
    public static final String STATUS_ENABLED = "ENABLED";
    
    public static final String INPUT_TYPE_PERFORMANCE = "Performance";
    public static final String INPUT_TYPE_AVAILABILITY = "Availability";
    
    public static final String CLOUD_TYPE_AWS = "AWS";
    public static final String VPC = "VPC";
    public static final Map<String, Map<String, Map<String, List<Dashboard>> > > ENABLED_DASHBOARD_CACHE = new HashMap<String, Map<String, Map<String, List<Dashboard>> > >();
    
    public static final List<String> AWS_DISCOVERED_ASSETS = new ArrayList<>();
    
    public static final String STATUS_UNPAID = "UNPAID";
//    public static final Map<AwsDiscoveredAssetKey, AwsDiscoveredAssetCache > AWS_DISCOVERED_ASSET_CACHE = new HashMap<AwsDiscoveredAssetKey, AwsDiscoveredAssetCache>();
    
//    static {
//    	initAwsDiscoveredAssetItemsList();
//    }
    
//    private static void initAwsDiscoveredAssetItemsList() {
//    	AWS_DISCOVERED_ASSETS.add(VPC);
//    }
    
    private Constants() {
    }
}
