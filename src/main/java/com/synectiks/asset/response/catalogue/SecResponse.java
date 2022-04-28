package com.synectiks.asset.response.catalogue;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private ComplianceRules complianceRules;
	private CompliancePolicies compliancePolicies;
	private ComplianceAuditors complianceAuditors;
	private CodeSecurityTemplates codeSecurityTemplates;
	private ConainerSecurityTemplates conainerSecurityTemplates;
	private DataSecurityTemplates dataSecurityTemplates;
}
