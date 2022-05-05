package com.synectiks.asset.business.service;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.google.gson.Gson;
import com.synectiks.asset.response.ViewJsonResponse;

@Converter(autoApply = true)
public class ViewJsonConverter implements AttributeConverter<ViewJsonResponse, String> {

  private final static Gson GSON = new Gson();

  @Override
  public String convertToDatabaseColumn(ViewJsonResponse mjo) {
    return GSON.toJson(mjo);
  }

  @Override
  public ViewJsonResponse convertToEntityAttribute(String dbData) {
    return GSON.fromJson(dbData, ViewJsonResponse.class);
  }
}