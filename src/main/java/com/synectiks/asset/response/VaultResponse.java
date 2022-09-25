package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.synectiks.asset.domain.Vault;

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
public class VaultResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountId;
	private List<Credential> credentials;
	
	public static VaultResponse toVaultResponse(Vault vault) {
		VaultResponse vr = VaultResponse.builder()
				.accountId(vault.getAccountId())
				.build();
		List<Credential> crList = new ArrayList<>();
		vr.setCredentials(crList);
		return vr;
	}
}
