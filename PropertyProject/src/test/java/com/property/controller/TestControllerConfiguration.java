package com.property.controller;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import com.property.SpringBootConfigurer;
import com.property.WebMvcConfiguration;

@TestConfiguration
@SpringBootConfiguration
@Import({SpringBootConfigurer.class,  WebMvcConfiguration.class})
public class TestControllerConfiguration {
//    @MockBean
//    LoginRepository loginRepository;
//    @MockBean
//    UserHandlerInterceptor userHandlerInterceptor;
}
