package com.synectiks.asset.response;

import java.io.Serializable;
import java.util.List;

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
public class AccountTree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String account;
	private List<Vpc> vpcs;

//	public String getAccount() {
//		return account;
//	}
//
//	public void setAccount(String account) {
//		this.account = account;
//	}
//
//	public List<Vpc> getVpcs() {
//		return vpcs;
//	}
//
//	public void setVpcs(List<Vpc> vpcs) {
//		this.vpcs = vpcs;
//	}
}
