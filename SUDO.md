### `CMDB Port: 6057`
### `1. organization wise products`

```
Method - GET
```
```
API End Point - /organizations/{orgId}/products
Request - Path variable {orgId}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getProduct(@Param("orgId") Long orgId);
		4.1 This getProduct method accepts long type organization id and pass it to the native sql
		4.2 Native sql returns the list of products (List<String>) of the given organization id
	5. JPA repository returns this list of products to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>

### `1.1 organization wise landing Zones`

```
Method - GET
```
```
API End Point - /organizations/{orgId}/landing-zone
Request - Path variable {orgId}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getLandingZones(@Param("orgId") Long orgId);
        4.1 This getLandingZones method accepts long type organization id and pass it to the native sql
		4.2 Native sql returns the list of landing zone (List<String>) of the given organization id
	5. JPA repository returns this list of landing zone to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>

### `1.2 organization wise product enclaves`
```
Method - GET
```
```
API End Point - /organizations/{orgId}/product-enclave
Request - Path variable {orgId}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getOrganizationProductEnclave(@Param("orgId") Long orgId);
        4.1 This getOrganizationProductEnclave method accepts long type organization id and pass it to the native sql
		4.2 Native sql returns the list of product enclaves (List<String>) of the given organization id
	5. JPA repository returns this list of product enclaves to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>


### `1.3 organization wise environment resource counts`
```
Method - GET
```
```
API End Point - /organizations/{orgId}/cloud-environments/count
Request - Path variable {orgId}
Response - List<EnvironmentCountsDto>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<EnvironmentCountsDto> getCount(@Param("orgId") Long orgId);
        4.1 This getCount method accepts long type organization id and pass it to the native sql
		4.2 Native sql returns the list of landing zone (List<EnvironmentCountsDto>) of the given organization id
	5. JPA repository returns this list of landing zone to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>

### `1.4 organization wise list of landing zone and its associated product enclaves, products, app and data services`
```
Method - GET
```
```
API End Point - /organizations/{orgId}/cloud-environments/summary
Request - Path variable {orgId}
Response - List<EnvironmentDto>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<EnvironmentCountsDto> getEnvironmentSummary(@Param("orgId") Long orgId);
        4.1 This getEnvironmentSummary method accepts long type organization id and pass it to the native sql
		4.2 Native sql returns the list of landing zone (List<EnvironmentCountsDto>) Filter list cloud wise in service class. 
		4.3 Service returns the list of landing zone (List<EnvironmentCountsDto>) of the given organization id
	5. JPA repository returns this list of landing zone to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>

### `1.5. organization wise departments`

```
Method - GET
```
```
API End Point - /organizations/{orgId}/departments
Request - Path variable {orgId}
Response - Set<Department>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. JPA repository returns this list of department to service class and service class returns the same list to contorller then get department from the list and finally controller wraps the list into the ResponseEntity and return it to client

<hr>



### `2.organization and department wise products`
```
Method - GET
```
```
API End Point - /organizations/{orgId}/departments/{depId}/products	
Request - Path variable {orgId} , Path variable {depId}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getDepartmentProducts(@Param("orgId") Long orgId, @Param("depId") Long depId);
        4.1 This getDepartmentProducts method accepts long type organization id and long type department id and pass it to the native sql
		4.2 Native sql returns the list of  products of an department an Organization (List<String>) of the given organization id and department id
	5. JPA repository returns this list of  products of an department an Organization to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>

### `2.1.organization and department wise landing Zones`
```
Method - GET
```
```
API End Point - /organizations/{orgId}/departments/{depId}/landing-zone
Request - Path variable {orgId} , Path variable {depId}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getDepartmentLandingZones(@Param("orgId") Long orgId , @Param("depId") Long depId);
        4.1 This getDepartmentLandingZones method accepts long type organization id and long type department id and pass it to the native sql
		4.2 Native sql returns the list of  landing zone of an department an Organization (List<String>) of the given organization id and department id
	5. JPA repository returns this list of  landing zone of an department an Organization to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>

### `2.2.organization and department wise product enclaves`
```
Method - GET
```
```
API End Point - /organizations/{orgId}/departments/{depId}/product-enclave
Request - Path variable {orgId} , Path variable {depId}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getOrganizationDepartmentsProductEnclave(@Param("orgId") Long orgId , @Param("depId") Long depId);
        4.1 This getOrganizationDepartmentsProductEnclave method accepts long type organization id and long type department id and pass it to the native sql
		4.2 Native sql returns the list of  product enclaves of an department an Organization (List<String>) of the given organization id and department id
	5. JPA repository returns this list of  product enclaves of an department an Organization to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>







### `3.organization and cloud wise landing Zones `
```
Method - GET
```
```
API End Point - /organizations/{orgId}/cloud/{cloudName}/landing-zone	
Request - Path variable {orgId} , Path variable {cloudName}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getCloudnameLandingZones(@Param("orgId") Long orgId, @Param("cloudName") String cloudName );
        4.1 This getCloudnameLandingZones method accepts long type organization id and string type cloudName  and pass it to the native sql
		4.2 Native sql returns the list of  landing Zones  of an cloudName an Organization (List<String>) of the given organization id and cloudName 
	5. JPA repository returns this list of  landing Zones  of an cloudName an Organization to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>






### `4.organization, department and cloud wise landing Zones `
```
Method - GET
```
```
API End Point - /organizations/{orgId}/departments/{depId}/cloud/{cloudName}/landing-zone	
Request - Path variable {orgId} , Path variable {depId} , Path variable {cloudName}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getDepartmentCloudnameLandingZones(@Param("orgId") Long orgId, @Param("depId") Long depId, @Param("cloudName") String cloudName );
        4.1 This getDepartmentCloudnameLandingZones method accepts long type organization id and long type department id and string type cloudName  and pass it to the native sql
		4.2 Native sql returns the list of  landing Zones  of an department an cloudName an Organization (List<String>) of the given organization id and department id and cloudName 
	5. JPA repository returns this list of  landing Zones  of an cloudName an Organization to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>





### `5.organization and environment wise product`

```
Method - GET
```
```
API End Point - /organizations/{orgId}/environments/{env}/products
Request - Path variable {orgId} , Path variable {env}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getOrganizationEnvProduct(@Param("orgId"), @Param("env") String env );
        4.1 This getOrganizationEnvProduct method accepts long type organization id and string type env and pass it to the native sql
		4.2 Native sql returns the list of  product  of an department an env an Organization (List<String>) of the given organization id and  env 
	5. JPA repository returns this list of  product  of an env an Organization to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>

### `6. organization, department and environment wise product`
```
Method - GET
```
```
API End Point - /organizations/{orgId}/departments/{depId}/environments/{env}/products
Request - Path variable {orgId} , Path variable {depId} , Path variable {env}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getOrganizationDepartmentEnvProduct(@Param("orgId") Long orgId, @Param("depId") Long depId, @Param("env") String env );
        4.1 This getOrganizationDepartmentEnvProduct method accepts long type organization id and long type department id and string type env  and pass it to the native sql
		4.2 Native sql returns the list of  product  of an department an env an Organization (List<String>) of the given organization id and department id and env 
	5. JPA repository returns this list of  product  of an env an Organization to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>



### `7.organization and landing zone wise product enclaves`
```
Method - GET
```
```
API End Point - /organizations/{orgId}/landing-zone/{landingZone}/product-enclave
Request - Path variable {orgId} , Path variable {landingZone}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getOrganizationLandingzoneProductEnclave(@Param("orgId"), @Param("landingZone") String landingZone );
        4.1 This getOrganizationLandingzoneProductEnclave method accepts long type organization id and string type landingZone and pass it to the native sql
		4.2 Native sql returns the list of  product enclaves  of an department an landingZone an Organization (List<String>) of the given organization id and  landingZone 
	5. JPA repository returns this list of  product enclaves  of an landingZone an Organization to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>


### `8.organization, department and landing zone wise product enclaves`
```
Method - GET
```
```
API End Point - /organizations/{orgId}/departments/{depId}/landing-zone/{landingZone}/product-enclave
Request - Path variable {orgId} , Path variable {depId} , Path variable {landingZone}
Response - List<String>
```

	1. Request passed to spring boot request controller 
	2. Controller forward request to serivce class
	3. Service class calls JPA repository 
	4. There is method defines in the serive class - public List<String> getOrganizationDepartmentLandingzoneProductEnclave(@Param("orgId") Long orgId, @Param("depId") Long depId, @Param("landingZone") String landingZone );
        4.1 This getOrganizationDepartmentLandingzoneProductEnclave method accepts long type organization id and long type department id and string type landingZone  and pass it to the native sql
		4.2 Native sql returns the list of  product enclaves of an department an landingZone an Organization (List<String>) of the given organization id and department id and landingZone 
	5. JPA repository returns this list of  product enclaves of an landingZone an Organization to service class and service class returns the same list to contorller and finally controller wraps the list into the ResponseEntity and return it to client

<hr>

### Who do I talk to? ###

	Please mail us on
	info@syenctiks.com
Footer
© 2023 GitHub, Inc.
Footer navigation
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About






