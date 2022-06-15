package com.example.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
@Data
public class PDFExportConfig {

    /**
     * 字体目录
     */
    @Value("${font}")
    private String font;

    /**
     * 线索报告——查看 模板名称
     */
    @Value("${user-ftl}")
    private String userFtl;


}