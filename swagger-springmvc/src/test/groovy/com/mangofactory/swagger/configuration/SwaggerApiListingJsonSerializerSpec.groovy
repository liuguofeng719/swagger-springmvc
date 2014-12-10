package com.mangofactory.swagger.configuration
import com.fasterxml.jackson.databind.ObjectMapper
import com.mangofactory.swagger.mixins.ApiListingSupport
import com.mangofactory.swagger.mixins.AuthSupport
import spock.lang.Specification


@Mixin([ApiListingSupport, AuthSupport])
class SwaggerApiListingJsonSerializerSpec extends Specification {

  final ObjectMapper mapper = new ObjectMapper();

//  def setup() {
//    mapper.registerModule(new DefaultScalaModule())
//    SimpleModule mod = new SimpleModule("SwaggerApiListingJsonSerializerMod")
//    mod.addSerializer(ApiListing.class, new SwaggerApiListingJsonSerializer())
//    mapper.registerModule(mod)
//  }

//  def "should serialize Authorization types with models attached"() {
//  given:
//    SwaggerSchemaConverter parser = new SwaggerSchemaConverter();
//    Option<Model> sModel = parser.read(DummyModels.BusinessModel, new scala.collection.immutable.HashMap());
//    Model model = fromOption(sModel);
//    Map<String, Model> models = new HashMap<String, Model>()
//    models.put("Business", model)
//
//    List<Authorization> authorizations = defaultAuth()
//
//    ApiListing apiListing = apiListing(toScalaList(authorizations), toOption(toScalaModelMap(models)))
//
//  when:
//    StringWriter stringWriter = new StringWriter()
//    mapper.writeValue(stringWriter, apiListing)
//    def jsonString = stringWriter.toString()
//    def json = new JsonSlurper().parseText(jsonString)
//  then:
//    json.authorizations.oauth2[0].scope == 'global'
//    json.authorizations.oauth2[0].description == 'accessEverything'
//  }
}
