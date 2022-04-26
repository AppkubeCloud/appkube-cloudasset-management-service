package com.synectiks.asset.business.service;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.google.gson.Gson;
import com.synectiks.asset.response.ServiceDetailResponse;

@Converter(autoApply = true)
public class ServiceDetailConverter implements AttributeConverter<ServiceDetailResponse, String> {

  private final static Gson GSON = new Gson();

  @Override
  public String convertToDatabaseColumn(ServiceDetailResponse mjo) {
    return GSON.toJson(mjo);
  }

  @Override
  public ServiceDetailResponse convertToEntityAttribute(String dbData) {
    return GSON.fromJson(dbData, ServiceDetailResponse.class);
  }
}