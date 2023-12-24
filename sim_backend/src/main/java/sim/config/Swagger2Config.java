/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.config;

/**
 *
 * @author jeyson
 */
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "swagger")
public class Swagger2Config {


//    @Bean
//    public Docket api() {
//
//            return new Docket(DocumentationType.SWAGGER_2)
//            .select()           
//            .apis(RequestHandlerSelectors.basePackage("cabinet"))
////            .apis(RequestHandlerSelectors.basePackage("cabinet.paperless.controllers"))
////            .apis(RequestHandlerSelectors.any())
////            .paths(PathSelectors.ant("/*"))
//            .paths(PathSelectors.any())
//            .build()
//            .apiInfo(apiInfo())
//            .useDefaultResponseMessages(true);
//    }
//   
//    
// private ApiInfo apiInfo() {
//        ApiInfo apiInfo = new ApiInfo("REST full APIs", "Abon api list", "API vers 09.11.2021", "Terms of service",
//                new Contact("dit backend developers", "dev backend", "somemail"),
//                "License of API",
//                "API license URL", Collections.emptyList());
//        return apiInfo;
//    }
  @Bean
  public GroupedOpenApi publicApi() {
      return GroupedOpenApi.builder()
              .group("main")
              .pathsToMatch("/**")
              .build();
  }
}
