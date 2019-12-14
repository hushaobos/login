package com.hjc.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hjc.token.TokenInterceptor;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
public class MvcConfig extends WebMvcConfigurationSupport {

//    @Override
//    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
//        Object[] interrceptors = new Object[]{};
//        RequestMappingHandlerMapping requestMappingHandlerMapping = super.requestMappingHandlerMapping();
//        requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
//        requestMappingHandlerMapping.setUseTrailingSlashMatch(false);
//        requestMappingHandlerMapping.setInterceptors(interrceptors);
//        return requestMappingHandlerMapping;
//    }

    //适配器添加类型转换器
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter()
    {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter=new RequestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setWebBindingInitializer(customBinder());

        List<HttpMessageConverter<?>> list = new ArrayList<>();
        list.add(mappingJacksonHttpMessageConverter());

        requestMappingHandlerAdapter.setMessageConverters(list);
        return requestMappingHandlerAdapter;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(RequestMethod.PUT.toString(),
                        RequestMethod.DELETE.toString(),
                        RequestMethod.HEAD.toString(),
                        RequestMethod.PATCH.toString(),
                        RequestMethod.TRACE.toString(),
                        RequestMethod.POST.toString(),
                        RequestMethod.GET.toString(),
                        RequestMethod.OPTIONS.toString())
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    //类型转换器添加conversionService
    @Bean(name="customBinder")
    public ConfigurableWebBindingInitializer customBinder()
    {
        ConfigurableWebBindingInitializer initializer=new ConfigurableWebBindingInitializer();
        initializer.setValidator(getValidator());
        return initializer;
    }

    @Override
    @Bean
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setProviderClass(HibernateValidator.class);
        return validator;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter(){
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        supportedMediaTypes.add(MediaType.ALL);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper()
    {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper;
    }
}
