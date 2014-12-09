package com.wordnik.swagger.model;

import java.util.List;

public class AllowableListValues implements AllowableValues {
  private final List<String> values;
  private final String valueType;

  public AllowableListValues(List<String> values, String valueType) {
    this.values = values;
    this.valueType = valueType;
  }

  public List<String> values() {
    return values;
  }

  public String valueType() {
    return valueType;
  }
}