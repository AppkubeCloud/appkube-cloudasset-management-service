package com.synectiks.asset.response;

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
public class SecurityResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer score;
  
}
