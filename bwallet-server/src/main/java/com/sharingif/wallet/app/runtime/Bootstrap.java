package com.sharingif.wallet.app.runtime;

import com.sharingif.cube.context.annotation.ExtendedAnnotationBeanNameGenerator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration(exclude={WebMvcAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
@ComponentScan(
        basePackages = "com.sharingif.wallet.*.controller"
        ,nameGenerator = ExtendedAnnotationBeanNameGenerator.class
        ,useDefaultFilters= false
        ,includeFilters = {
        @Filter(type= FilterType.ANNOTATION,value=Controller.class)
})
public class Bootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(ApplicationContext.class)
                .child(Bootstrap.class)
                .initializers(new ApplicationContextInitializer<GenericApplicationContext>() {
                    @Override
                    public void initialize(GenericApplicationContext applicationContext) {
                        applicationContext.setAllowBeanDefinitionOverriding(false);
                    }
                }).run(args);
    }

    @EnableTransactionManagement
    @EnableAutoConfiguration(exclude={WebMvcAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
    @ComponentScan(
            basePackages = "com.sharingif.wallet.*.dao,com.sharingif.wallet.*.service,com.sharingif.wallet.common.autoconfigure,com.sharingif.wallet.app.autoconfigure,com.sharingif.cube.spring.boot.*"
            ,nameGenerator = ExtendedAnnotationBeanNameGenerator.class
            ,useDefaultFilters= false
            ,includeFilters={
            @Filter(type=FilterType.ANNOTATION,value=Repository.class)
            ,@Filter(type=FilterType.ANNOTATION,value=Service.class)
            ,@Filter(type=FilterType.ANNOTATION,value=Component.class)
    })
    protected static class ApplicationContext {
        public static void main(String[] args) {
            new SpringApplicationBuilder(ApplicationContext.class)
                    .initializers(new ApplicationContextInitializer<GenericApplicationContext>() {
                        @Override
                        public void initialize(GenericApplicationContext applicationContext) {
                            applicationContext.setAllowBeanDefinitionOverriding(false);
                        }
                    }).run(args);
        }
    }

}