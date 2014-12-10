package com.mangofactory.swagger.models

import com.mangofactory.swagger.mixins.ModelProviderSupport
import com.mangofactory.swagger.mixins.TypesForTestingSupport
import com.wordnik.swagger.model.Model
import com.wordnik.swagger.model.ModelRef
import org.springframework.http.HttpHeaders
import spock.lang.Specification
import spock.lang.Unroll

import static com.mangofactory.swagger.models.ResolvedTypes.responseTypeName

@Mixin([TypesForTestingSupport, ModelProviderSupport])
class ContainerTypesSpec extends Specification {
  def "Response class for container types are inferred correctly"() {
    given:
    expect:
      responseTypeName(containerType) == name

    where:
      containerType                  | name
      genericListOfSimpleType()      | "List[SimpleType]"
      genericListOfInteger()         | "List"
      erasedList()                   | "List"
      genericSetOfSimpleType()       | "Set[SimpleType]"
      genericSetOfInteger()          | "Set"
      erasedSet()                    | "Set"
      genericClassWithGenericField() | "GenericType«ResponseEntity«SimpleType»»"

  }

  @Unroll
  def "Model properties of type List, are inferred correctly"() {
    given:
      def sut = typeWithLists()
      ModelProvider provider = defaultModelProvider()
      Model asInput = provider.modelFor(ModelContext.inputParam(sut)).get()
      Model asReturn = provider.modelFor(ModelContext.returnValue(sut)).get()

    expect:
      asInput.getName() == "ListsContainer"
      asInput.getProperties().containsKey(property)
      def modelProperty = asInput.getProperties().get(property)
      modelProperty.getType() == name
      modelProperty.getItems()
      ModelRef item = modelProperty.getItems()
      item.getType() == itemType
      item.getRef() == itemRef
      item.getQualifiedType() == itemQualifiedType

      asReturn.getName() == "ListsContainer"
      asReturn.getProperties().containsKey(property)
      def retModelProperty = asReturn.getProperties().get(property)
      retModelProperty.getType() == name
      retModelProperty.getItems()
      def retItem = retModelProperty.getItems()
      retItem.getType() == itemType
      retItem.getRef() == itemRef
      retItem.getQualifiedType() == itemQualifiedType

    where:
      property          | name   | itemType | itemRef       | itemQualifiedType
      "complexTypes"    | "List" | null     | "ComplexType" | "com.mangofactory.swagger.models.ComplexType"
      "enums"           | "List" | "string" | ""            | "com.mangofactory.swagger.models.ExampleEnum"
      "aliasOfIntegers" | "List" | "int"    | ""            | "java.lang.Integer"
      "strings"         | "List" | "string" | ""            | "java.lang.String"
      "objects"         | "List" | "object" | ""            | "java.lang.Object"
  }

  def "Model properties of type Set, are inferred correctly"() {
    given:
      def sut = typeWithSets()
      def provider = defaultModelProvider()
      Model asInput = provider.modelFor(ModelContext.inputParam(sut)).get()
      Model asReturn = provider.modelFor(ModelContext.returnValue(sut)).get()

    expect:
      asInput.getName() == "SetsContainer"
      asInput.getProperties().containsKey(property)
      def modelProperty = asInput.getProperties().get(property)
      modelProperty.getType() == type
      modelProperty.getItems()
      ModelRef item = modelProperty.getItems()
      item.getType() == itemType
      item.getRef() == itemRef
      item.getQualifiedType() == itemQualifiedType

      asReturn.getName() == "SetsContainer"
      asReturn.getProperties().containsKey(property)
      def retModelProperty = asReturn.getProperties().get(property)
      retModelProperty.getType() == type
      retModelProperty.getItems()
      def retItem = retModelProperty.getItems()
      retItem.getType() == itemType
      retItem.getRef() == itemRef
      retItem.getQualifiedType() == itemQualifiedType

    where:
      property          | type  | itemType | itemRef       | itemQualifiedType
      "complexTypes"    | "Set" | null     | "ComplexType" | "com.mangofactory.swagger.models.ComplexType"
      "enums"           | "Set" | "string" | ""            | "com.mangofactory.swagger.models.ExampleEnum"
      "aliasOfIntegers" | "Set" | "int"    | ""            | "java.lang.Integer"
      "strings"         | "Set" | "string" | ""            | "java.lang.String"
      "objects"         | "Set" | "object" | ""            | "java.lang.Object"
  }

  @Unroll
  def "Model properties of type Arrays are inferred correctly"() {
    given:
      def sut = typeWithArrays()
      def provider = defaultModelProvider()
      Model asInput = provider.modelFor(ModelContext.inputParam(sut)).get()
      Model asReturn = provider.modelFor(ModelContext.returnValue(sut)).get()

    expect:
      asInput.getName() == "ArraysContainer"
      asInput.getProperties().containsKey(property)
      def modelProperty = asInput.getProperties().get(property)
      modelProperty.getType() == type
      modelProperty.getItems()
      ModelRef item = modelProperty.getItems()
      item.getType() == itemType
      item.getRef() == itemRef
      item.getQualifiedType() == itemQualifiedType

      asReturn.getName() == "ArraysContainer"
      asReturn.getProperties().containsKey(property)
      def retModelProperty = asReturn.getProperties().get(property)
      retModelProperty.getType() == type
      retModelProperty.getItems()
      def retItem = retModelProperty.getItems()
      retItem.getType() == itemType
      retItem.getRef() == itemRef
      retItem.getQualifiedType() == itemQualifiedType

    where:
      property          | type    | itemType | itemRef       | itemQualifiedType
      "complexTypes"    | "Array" | null     | "ComplexType" | "com.mangofactory.swagger.models.ComplexType"
      "enums"           | "Array" | "string" | ""            | "com.mangofactory.swagger.models.ExampleEnum"
      "aliasOfIntegers" | "Array" | "int"    | ""            | "java.lang.Integer"
      "strings"         | "Array" | "string" | ""            | "java.lang.String"
      "objects"         | "Array" | "object" | ""            | "java.lang.Object"
      "bytes"           | "Array" | "byte"   | ""            | "byte"
  }

  def "Model properties of type Map are inferred correctly"() {
    given:
      def sut = mapsContainer()
      def provider = defaultModelProvider()
      Model asInput = provider.modelFor(ModelContext.inputParam(sut)).get()
      Model asReturn = provider.modelFor(ModelContext.returnValue(sut)).get()

    expect:
      asInput.getName() == "MapsContainer"
      asInput.getProperties().containsKey(property)
      def modelProperty = asInput.getProperties().get(property)
      modelProperty.getType() == type
      modelProperty.getItems()
      ModelRef item = modelProperty.getItems()
      item.getType() == null
      item.getRef() == itemRef
      item.getQualifiedType() == itemQualifiedType

      asReturn.getName() == "MapsContainer"
      asReturn.getProperties().containsKey(property)
      def retModelProperty = asReturn.getProperties().get(property)
      retModelProperty.getType() == type
      retModelProperty.getItems()
      def retItem = retModelProperty.getItems()
      retItem.getType() == null
      retItem.getRef() == itemRef
      retItem.getQualifiedType() == itemQualifiedType

    where:
      property              | type   | itemRef                      | itemQualifiedType
      "enumToSimpleType"    | "List" | "Entry«string,SimpleType»"   | "com.mangofactory.swagger.models.alternates.Entry"
      "stringToSimpleType"  | "List" | "Entry«string,SimpleType»"   | "com.mangofactory.swagger.models.alternates.Entry"
      "complexToSimpleType" | "List" | "Entry«Category,SimpleType»" | "com.mangofactory.swagger.models.alternates.Entry"
  }

  def "Model properties of type Map are inferred correctly on generic host"() {
    given:
      def sut = responseEntityWithDeepGenerics()
      def provider = defaultModelProvider()

      def modelContext = ModelContext.inputParam(sut)
      modelContext.seen(resolver.resolve(HttpHeaders.class))
      Model asInput = provider.dependencies(modelContext).get("MapsContainer")

      def returnContext = ModelContext.returnValue(sut)
      returnContext.seen(resolver.resolve(HttpHeaders.class))
      Model asReturn = provider.dependencies(returnContext).get("MapsContainer")

    expect:
      asInput.getName() == "MapsContainer"
      asInput.getProperties().containsKey(property)
      def modelProperty = asInput.getProperties().get(property)
      modelProperty.getType() == type
      modelProperty.getItems()
      ModelRef item = modelProperty.getItems()
      item.getType() == null
      item.getRef() == itemRef
      item.getQualifiedType() == itemQualifiedType

      asReturn.getName() == "MapsContainer"
      asReturn.getProperties().containsKey(property)
      def retModelProperty = asReturn.getProperties().get(property)
      retModelProperty.getType() == type
      retModelProperty.getItems()
      def retItem = retModelProperty.getItems()
      retItem.getType() == null
      retItem.getRef() == itemRef
      retItem.getQualifiedType() == itemQualifiedType

    where:
      property              | type   | itemRef                      | itemQualifiedType
      "enumToSimpleType"    | "List" | "Entry«string,SimpleType»"   | "com.mangofactory.swagger.models.alternates.Entry"
      "stringToSimpleType"  | "List" | "Entry«string,SimpleType»"   | "com.mangofactory.swagger.models.alternates.Entry"
      "complexToSimpleType" | "List" | "Entry«Category,SimpleType»" | "com.mangofactory.swagger.models.alternates.Entry"
  }
}
