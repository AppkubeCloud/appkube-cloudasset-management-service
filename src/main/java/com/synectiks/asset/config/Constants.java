package com.synectiks.asset.config;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.synectiks.asset.business.domain.Dashboard;

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

    public static final String SERVICE_ID = "serviceId";
    public static final String STATUS_UNPAID = "UNPAID";
    public static final String SERVICE_FIREWALL = "Firewall";
    public static final String SERVICE_LOAD_BALANCER = "Load Balancer";
    public static final String SERVICE_GATEWAY_SERVICE = "Gateway Services";
    public static final String SERVICE_BUSINESS_SERVICE = "Business Services";
    public static final String APP_SERVICES = "APP";
    public static final String DATA_SERVICES = "DATA";

    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_MM_DD_YYYY = "MM-dd-yyyy";
    public static final String DATE_FORMAT_DD_MM_YYYY = "dd-MM-yyyy";
    public static final String DATE_FORMAT_DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy H:mm:s";
    
    
    public static final String DEFAULT_DATE_FORMAT =  DATE_FORMAT_MM_DD_YYYY;

    public static final String PERFORMANCE  = "performance";
    public static final String AVAILABILITY = "availability";
    public static final String RELIABILITY  = "reliability";
    public static final String ENDUSAGE     = "endusage";
    public static final String SECURITY     = "security";
    public static final String COMPLIANCE   = "compliance";
    public static final String ALERTS       = "alerts";

    public static final String DASHBOARD_TYPE[] = {PERFORMANCE,AVAILABILITY, RELIABILITY, ENDUSAGE, SECURITY, COMPLIANCE, ALERTS};
	public static final List<String> VIEW_JSON_KEYS = Arrays.asList("id", "slug", "uid", "cloudElement", "accountId", "url", "cloudElementId"
			, "associatedOU","associatedDept","associatedProduct","associatedEnv","serviceType","serviceNature","serviceName","serviceInstance");
	public static final List<String> DASHBOARD_TYPE_KEYS = Arrays.asList(DASHBOARD_TYPE);

	private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
			.appendOptional(DateTimeFormatter.ISO_DATE_TIME).appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
			.appendOptional(DateTimeFormatter.ISO_INSTANT)
			.appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
			.appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
			.appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toFormatter().withZone(ZoneOffset.UTC);

	public static OffsetDateTime parseDateTimeString(String str) {
		return ZonedDateTime.from(DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
	}

	public static ObjectMapper instantiateMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		SimpleModule module = new SimpleModule();
		module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
			@Override
			public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
					throws IOException, JsonProcessingException {
				String value = jsonParser.getText();
				return parseDateTimeString(value);
			}
		});
		mapper.registerModule(module);
		return mapper;
	}

    public static String PROXY_GRAFANA_BASE_API = "";
    public static String PROXY_GRAFANA_USER = "";
    public static String PROXY_GRAFANA_PASSWORD = "";
//    public static String IMPORT_DASHBOARD_TO_GRAFANA_API = "";
    public static String DEPARTMENT_WISE_ANALYTICS_CACHE_KEY ="department-wise-analytics";
    public static String PRODUCT_CACHE_KEY ="product";
    public static Map<String, Object> cache = new HashMap<>();
    public static String AVAILABLE_CLOUDS [] = {"AWS","AZURE","GCP","OTHER"};
    public static final String AWS = "AWS";
    public static final String AZURE = "AZURE";
    public static final String GCP = "GCP";
    public static String VAULT_URL = "";
    public static final DecimalFormat decfor = new DecimalFormat("0.00");
    public static final String DEFAULT_TIMEZONE = "Asia/Kolkata";
    public static final String ORGANIZATION_ID = "organizationId";
    public static final String ORGANIZATION_NAME = "organizationName";
    public static final String DEPARTMENT_ID = "departmentId";
    public static final String DEPARTMENT_NAME = "departmentName";
    public static final String PRODUCT_ID = "productId";
    public static final String PRODUCT_NAME = "productName";
    public static final String ACCOUNT_ID = "accountId";
    public static final String LANDING_ZONE = "landingZone";
    public static final String CREATED_ON = "createdOn";
    public static final String UPDATED_ON = "updatedOn";
    
    public static final String DEPLOYMENT_ENVIRONMENT_ID = "deploymentEnvironmentId";
    public static final String DEPLOYMENT_ENVIRONMENT_NAME = "deploymentEnvironmentName";
    public static final String SERVICE_NAME = "serviceName";
    public static final String SERVICE_TYPE = "type";
    public static final String SERVICE_NATURE = "serviceNature";
    public static final String SERVICES_ID = "servicesId";
    public static final String TAGGED = "Tagged";
    public static final String DISCOVERED_ASSET_ID = "discoveredAssetId";
    public static final String SERVICE_ALLOCATION_ID = "serviceAllocationId";
    public static final String CLOUD_ENVIRONMENT_ID = "cloudEnvironmentId";
    
    public static final String MODULE_ID = "moduleId";
    
    private Constants() {
    }
}
