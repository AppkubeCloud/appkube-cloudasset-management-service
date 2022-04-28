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
public class CatalogueResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private DevResponse dev;
	private SecResponse sec;
	private OpsResponse ops;
	
}
