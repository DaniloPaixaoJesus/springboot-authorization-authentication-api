package br.com.danilopaixao.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

import br.com.danilopaixao.ws.core.MoreInfo;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@EnableSwagger2
@EnableScheduling
@SpringBootApplication
public class APIRestApplication extends SpringBootServletInitializer {

	public static void main(String[] args){
		SpringApplication.run(APIRestApplication.class, args)
				.getBean(MoreInfo.class)
				.info();
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(APIRestApplication.class);
    }


	@Bean
	public MoreInfo moreInfo() {
		return new MoreInfo();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("br.com.danilopaixao.ws"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(this.metaData());
    }
    
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("API WS")
                .description("Documentação da API Rest")
                .build();
    }
    
}
