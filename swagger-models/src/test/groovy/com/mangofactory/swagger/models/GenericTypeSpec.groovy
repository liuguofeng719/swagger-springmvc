package com.mangofactory.swagger.models

import com.mangofactory.swagger.mixins.ModelProviderSupport
import com.mangofactory.swagger.mixins.TypesForTestingSupport
import com.wordnik.swagger.model.Model
import spock.lang.Specification

import static com.google.common.base.Strings.isNullOrEmpty
import static com.mangofactory.swagger.models.ModelContext.inputParam
import static com.mangofactory.swagger.models.ModelContext.returnValue

@Mixin([TypesForTestingSupport, ModelProviderSupport])
class GenericTypeSpec extends Specification {
  def "Generic property on a generic types is inferred correctly"() {
    given:
      def provider = defaultModelProvider()
      Model asInput = provider.modelFor(inputParam(modelType)).get()
      Model asReturn = provider.modelFor(returnValue(modelType)).get()

    expect:
      asInput.getName() == expectedModelName(modelNamePart)
      asInput.getProperties().containsKey("genericField")
      def modelProperty = asInput.getProperties().get("genericField")
      modelProperty.getType() == propertyType
      modelProperty.getQualifiedType() == qualifiedType
      (modelProperty.getItems() == null) == (!"List".equals(propertyType) && !"Array".equals(propertyType))

      asReturn.getName() == expectedModelName(modelNamePart)
      asReturn.getProperties().containsKey("genericField")
      def retModelProperty = asReturn.getProperties().get("genericField")
      retModelProperty.getType() == propertyType
      retModelProperty.getQualifiedType() == qualifiedType
      (retModelProperty.getItems() == null) == (!"List".equals(propertyType) && !"Array".equals(propertyType))

    where:
      modelType                       | propertyType                       | modelNamePart                      | qualifiedType
      genericClass()                  | "SimpleType"                       | "SimpleType"                       | "com.mangofactory.swagger.models.SimpleType"
      genericClassWithTypeErased()    | "object"                           | ""                                 | "java.lang.Object"
      genericClassWithListField()     | "List"                             | "List«SimpleType»"                 | "java.util.List<com.mangofactory.swagger.models.SimpleType>"
      genericClassWithGenericField()  | "ResponseEntity«SimpleType»"       | "ResponseEntity«SimpleType»"       | "org.springframework.http.ResponseEntity<com.mangofactory.swagger.models.SimpleType>"
      genericClassWithDeepGenerics()  | "ResponseEntity«List«SimpleType»»" | "ResponseEntity«List«SimpleType»»" | "org.springframework.http.ResponseEntity<java.util.List<com.mangofactory.swagger.models.SimpleType>>"
      genericCollectionWithEnum()     | "Collection«string»"               | "Collection«string»"               | "java.util.Collection<com.mangofactory.swagger.models.ExampleEnum>"
      genericTypeWithPrimitiveArray() | "Array"                            | "Array«byte»"                      | "byte"
      genericTypeWithComplexArray()   | "Array"                            | "Array«SimpleType»"                | null
  }


  def "Generic properties are inferred correctly even when they are not participating in the type bindings"() {
    given:
      def provider = defaultModelProvider()
      Model asInput = provider.modelFor(inputParam(modelType)).get()
      Model asReturn = provider.modelFor(returnValue(modelType)).get()

    expect:
      asInput.getProperties().containsKey("strings")
      def modelProperty = asInput.getProperties().get("strings")
      modelProperty.getType() == propertyType
//    modelProperty.qualifiedType() == qualifiedType DK TODO: Fix this

      asReturn.getProperties().containsKey("strings")
      def retModelProperty = asReturn.getProperties().get("strings")
      retModelProperty.getType() == propertyType
//    retModelProperty.qualifiedType() ==qualifiedType DK TODO: Fix this

    where:
      modelType                      | propertyType | qualifiedType
      genericClass()                 | "List"       | "java.util.List<java.lang.String>"
      genericClassWithTypeErased()   | "List"       | "java.util.List<java.lang.String>"
      genericClassWithListField()    | "List"       | "java.util.List<java.lang.String>"
      genericClassWithGenericField() | "List"       | "java.util.List<java.lang.String>"
      genericClassWithDeepGenerics() | "List"       | "java.util.List<java.lang.String>"
      genericCollectionWithEnum()    | "List"       | "java.util.List<java.lang.String>"
  }

  def expectedModelName(String modelName) {
    if (!isNullOrEmpty(modelName)) {
      String.format("GenericType«%s»", modelName)
    } else {
      "GenericType"
    }
  }
}
