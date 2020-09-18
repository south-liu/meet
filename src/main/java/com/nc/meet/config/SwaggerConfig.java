package com.nc.meet.config;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ClassUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Swagger 配置
 * @author zigang
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    @Value("${nature.restApi.enabled}")
    private Boolean enable;

    /**
     * 项目应用名
     */
    @Value("${application.name}")
    private String applicationName;

    /**
     * 项目版本信息
     */
    @Value("${application.version}")
    private String applicationVersion;

    /**
     * 项目描述信息
     */
    @Value("${application.description}")
    private String applicationDescription;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                .enable(enable)
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .paths(PathSelectors.any())
                .build()
                // 支持的通讯协议集合
                .protocols(newHashSet("https", "http"))

                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(securitySchemes())

                // 授权信息全局应用
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(applicationName + " Api Doc")
            .description(applicationDescription)
            .version("Application Version: " + applicationVersion + ", Spring Boot Version: " + SpringBootVersion.getVersion())
            .build();
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes()
    {
        return Collections.singletonList(new ApiKey("token", "token", "header"));
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts()
    {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference("token", new AuthorizationScope[] {
                                new AuthorizationScope("global", "")
                        }))).build());
    }

    @SafeVarargs
    private final <T> Set<T> newHashSet(T... ts)
    {
        if (ts.length > 0)
        {
            return new LinkedHashSet<>(Arrays.asList(ts));
        }
        return null;
    }
    /**
     * 通用拦截器排除swagger设置，所有拦截器都会自动加swagger相关的资源排除信息
     */
    @Override
    @SuppressWarnings("unchecked")
    public void addInterceptors(InterceptorRegistry registry) {
        try
        {
            Field registrationsField = ClassUtils.getClass("InterceptorRegistry").getField("registrations");
            List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
            if (registrations != null)
            {
                for (InterceptorRegistration interceptorRegistration : registrations)
                {
                    interceptorRegistration
                            .excludePathPatterns("/swagger**/**")
                            .excludePathPatterns("/webjars/**")
                            .excludePathPatterns("/v3/**")
                            .excludePathPatterns("/doc.html");
                }
            }
        }
        catch (Exception e) {

        }
    }

}
