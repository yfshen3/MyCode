package org.lf.admin.api.baseapi.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置项
 *
 * @author sunwill
 * @date 2018/04/25
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements EnvironmentAware, WebMvcConfigurer {
    private String basePackage;
    private String creatName;
    private String serviceName;
    private String description;

    public SwaggerConfiguration() {
    }

    /**
     * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
     * <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
     * <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.basePackage))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(this.serviceName + " Restful APIs")
                .description(this.description)
                .contact(new Contact(this.creatName, "", "sunwill123@qq.com")).version("1.0").build();
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.basePackage = environment.getProperty("swagger.basepackage");
        this.creatName = environment.getProperty("swagger.service.developer");
        this.serviceName = environment.getProperty("swagger.service.name");
        this.description = environment.getProperty("swagger.service.description");
    }
}
