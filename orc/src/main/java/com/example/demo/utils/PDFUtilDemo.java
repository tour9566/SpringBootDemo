package com.example.demo.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Template;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: yaoxin
 * @Description: pdf 导出工具类
 * @createDate: 2022/3/26 10:20
 **/
public class PDFUtilDemo {
    /**
     * 字体缓存目录
     */
    private static String FONT_CACHE_DIR = null;
    
    /**
     * freemarker渲染html
     *
     * @param data 数据
     * @return
     */
    public static String freeMarkerRender(Map<String, Object> data, Template template) {
        Writer out = new StringWriter();
        try {
            template.setOutputEncoding("UTF-8");
            // 合并数据模型与模板
            //将合并后的数据和模板写入到流中，这里使用的字符流
            template.process(data, out);
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 创建pdf文件并渲染pdf
     *
     * @param content pdf内容
     * @param tempDir pdf生成目录
     * @param font    字体resource路径
     * @throws IOException
     * @throws DocumentException
     */
    public static void createPdf(String content, String tempDir, String font) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4, 36.0F, 36.0F, 60.0F, 36.0F);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(tempDir));
        document.open();
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);

        /**
         * 此处从resource目录下获取字体并拷贝到临时目录，并缓存目录
         * 因为打成jar包后无法获取直接获取文件，最好是拷贝到服务器写绝对目录
         */
        if (StrUtil.isBlankOrUndefined(FONT_CACHE_DIR)) {
            File fontTemp = new File( "./templates/font/SIMYOU.ttc");
            if (!fontTemp.exists()) {
                fontTemp.createNewFile();
            }
            ClassPathResource simResource = new ClassPathResource(font);
            FileUtil.writeFromStream(simResource.getInputStream(), fontTemp);
            FONT_CACHE_DIR = fontTemp.getPath();
        }
        fontImp.register(FONT_CACHE_DIR);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new ByteArrayInputStream(content.getBytes()), null, Charset.forName("UTF-8"), fontImp);
        document.close();
        writer.close();
    }

    public static void main(String[] args) {
//        //添加测试数据
//        Map<String, Object> data = new HashMap();
//        data.put("username", "法外狂徒张三");
//        data.put("age", "24");
//        //根据配置类拿到模板名称，再通过FreeMarkerConfigurer获取Template对象
//        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("");
//        String content = PDFUtilDemo.freeMarkerRender(data, template);
//        File exportFile = File.createTempFile(EXPORT_NAME, EXPORT_SUFFIX);
//        PDFUtilDemo.createPdf(content, exportFile.getPath(), pdfExportConfig.getFont());
    }
}
