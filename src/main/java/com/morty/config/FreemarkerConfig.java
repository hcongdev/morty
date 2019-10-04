package com.morty.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


@Configuration
public class FreemarkerConfig implements InitializingBean {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    @Override
    public void afterPropertiesSet() throws Exception {
//        freeMarkerConfigurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }
}
