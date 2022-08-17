package com.psl.config;

import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerDocsConfig {
	@Bean
	 public   Docket createDocket() {
		
		   return  new Docket(DocumentationType.SWAGGER_2) 
				   .securityContexts(java.util.Arrays.asList(securityContext())) 
				   .securitySchemes(java.util.Arrays.asList(apiKey()))
				              .select() 
				              .apis(RequestHandlerSelectors.basePackage("com.psl.controller")) 
				              .paths(PathSelectors.regex("/patientTretmentInfo.*")) 
				              .build()
				              //.securitySchemes(java.util.Arrays.asList(apiKey()))
				              .useDefaultResponseMessages(true)
				              .apiInfo(getApiInfo());
	 }
	
	private  ApiInfo getApiInfo() {
		Contact contact=new Contact("parvbhatt","https://github.com/parvbhatt","parv02@gmail.com");
		return  new ApiInfo("Patient Treatment API",
				                            "Here you will get all Patient Treatment Information", 
				                            "3.4.RELEASE",
				                            "http://www.hcl.com/license",
				                            contact,
				                            "GNU Public",
				                            "http://apache.org/license/gnu", 
				                            Collections.emptyList());
	}
	
	private SecurityContext securityContext() { 
	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 

	private List<SecurityReference> defaultAuth() { 
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope; 
	    return java.util.Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
	}
	
	private ApiKey apiKey() { 
	    return new ApiKey("JWT", "Authorization", "header"); 
	}
	

	

}//class

