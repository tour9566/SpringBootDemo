package com.example.demo.controller;


import cn.hutool.json.JSONUtil;
import com.example.demo.config.PDFExportConfig;
import com.example.demo.utils.PDFUtilDemo;
import freemarker.template.Template;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yaoxin
 * @Description:
 * @createDate: 2022/3/28 17:23
 **/

@RestController
@AllArgsConstructor
public class UserController {

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    private final PDFExportConfig pdfExportConfig;
    /**
     * 导出文件名
     */
    private static String EXPORT_NAME = "export";
    /**
     * 导出后缀
     */
    private static String EXPORT_SUFFIX = "pdf";

    /**
     * 测试导出pdf
     *
     * @param response
     */
    @SneakyThrows
    @GetMapping(value = "/export")
    public void domesticViolenceSee(HttpServletResponse response) {

        //添加测试数据
        Map<String, Object> data = new HashMap();
        data.put("username", "法外狂徒张三");
        data.put("age", "24");
        //根据配置类拿到模板名称，再通过FreeMarkerConfigurer获取Template对象
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(pdfExportConfig.getUserFtl());
        String content = PDFUtilDemo.freeMarkerRender(data, template);
        File exportFile = File.createTempFile(EXPORT_NAME, EXPORT_SUFFIX);
        PDFUtilDemo.createPdf(content, exportFile.getPath(), pdfExportConfig.getFont());
        FileInputStream fin;
        try {
            fin = new FileInputStream(exportFile);
            OutputStream output = response.getOutputStream();
            byte[] buf = new byte[1024];
            int r;
            response.setContentType("application/pdf;charset=GB2312");
            while ((r = fin.read(buf, 0, buf.length)) != -1) {
                output.write(buf, 0, r);
            }
            fin.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exportFile.delete();
        }
    }
}
