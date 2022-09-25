package com.synectiks.asset.response;

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
public class Credential{
	private Long vaultId;
	private String cloudType;
	private String accessKey;
	private String secretKey;
	private String region;
}
