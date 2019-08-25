package com.katana.tenement.web.main.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mumu on 2019/3/27.
 */
@Configuration
public class Swagger2Config {

    @Autowired
    private Environment environment;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder authorization = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        authorization.name("Authorization").description("请求头信息Authorization（即token）")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(authorization.build());

        ApiSelectorBuilder builder = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));

        //区分环境,正式环境关闭swagger
        if (environment.acceptsProfiles("test", "dev")) {
            builder = builder.paths(PathSelectors.any());
        }
        if (environment.acceptsProfiles("prod")) {
            builder = builder.paths(PathSelectors.none());
        }

        return builder.build().globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("zu 项目api文档")
                .description("该文档已app开头数据客户端文档，以manager开头数据管理端文档")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}