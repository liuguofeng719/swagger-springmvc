package com.mangofactory.swagger.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.wordnik.swagger.model.ResourceListing;

import java.io.IOException;

public class SwaggerResourceListingJsonSerializer extends JsonSerializer<ResourceListing> {

  private final ObjectMapper objectMapper;

  public SwaggerResourceListingJsonSerializer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void serialize(ResourceListing value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    jgen.writeRaw(objectMapper.writeValueAsString(value));
  }
}
