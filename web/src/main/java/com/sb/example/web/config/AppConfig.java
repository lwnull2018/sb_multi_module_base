package com.sb.example.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 应用配置类
 * Created by lwnull2018@gmail.com ON 2021/2/10.
 */
@Component
public class AppConfig {

    @Value("${app.upload.path}")
    public String uploadPath;

}
