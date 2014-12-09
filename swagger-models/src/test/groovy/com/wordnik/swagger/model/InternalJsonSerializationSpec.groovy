package com.wordnik.swagger.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.wordnik.swagger.model.jackson.SwaggerJacksonProvider
import groovy.json.JsonSlurper
import spock.lang.Shared
import spock.lang.Specification

class InternalJsonSerializationSpec extends Specification {

  @Shared ObjectMapper objectMapper

  def setupSpec() {
    def provider = new SwaggerJacksonProvider()
    objectMapper = provider.objectMapper
//    objectMapper.registerModule(provider.swaggerJacksonModule())
  }

  def writeAndParse(object, boolean print = true) {

    def jsonString = objectMapper.writeValueAsString(object)
    def json = new JsonSlurper().parseText(jsonString)
    if (print) {
      println jsonString
    }
    json
  }
}