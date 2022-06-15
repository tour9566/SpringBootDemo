package com.example.demo.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据pdf模板生成pdf文件生成到某路径下或导出
 *
 */
public class PdfUtil {
    /**
     * 利用模板生成pdf保存到某路径下
     */
    public static void pdfOut(Map<String, Object> inputMap) {

        // 生成的新文件路径
        String path0 = "E:\\test\\ProhibitDelete";
        File f = new File(path0);

        if (!f.exists()) {
            f.mkdirs();
        }
        // 模板路径
        String templatePath = "E:\\test\\ProhibitDelete\\demoT.pdf";
        // 创建文件夹
        String newPdfPath = "E:\\test\\ProhibitDelete\\testMould.pdf";
        File file = new File(templatePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file1 = new File(newPdfPath);
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            BaseFont bf = BaseFont.createFont("./templates/font/SIMYOU.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // 输出流
            out = new FileOutputStream(newPdfPath);
            // 读取pdf模板
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            //文字类的内容处理
            Map<String, String> datemap = (Map<String, String>) inputMap.get("dateMap");
            form.addSubstitutionFont(bf);
            for (String key : datemap.keySet()) {
                String value = datemap.get(key);
                form.setField(key, value);
            }
            stamper.setFormFlattening(false);
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
        } catch (IOException | DocumentException e) {
            System.out.println(e);
        }

    }

    /**
     * 利用模板生成pdf导出
     */
    public static void pdfExport(Map<String, Object> inputMap, HttpServletResponse response) {

        // 生成的新文件路径
        String path0 = "E:\\test\\ProhibitDelete\\demoT.pdf";
        File f = new File(path0);

        if (!f.exists()) {
            f.mkdirs();
        }
        // 模板路径
        String templatePath = "E:\\test\\ProhibitDelete\\template12.pdf";

        File file = new File(templatePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PdfReader reader;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        OutputStream out = null;
        try {
            Map<String, String> datemap = (Map<String, String>) inputMap.get("dateMap");
            BaseFont bf = BaseFont.createFont("./templates/font/SIMYOU.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // 输出流
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + URLEncoder.encode(datemap.get("billId") + ".pdf", "UTF-8"));
            out = new BufferedOutputStream(response.getOutputStream());
            // 读取pdf模板
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            //文字类的内容处理
            form.addSubstitutionFont(bf);
            for (String key : datemap.keySet()) {
                String value = datemap.get(key);
                form.setField(key, value);
            }
            stamper.setFormFlattening(false);
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();

        } catch (IOException | DocumentException e) {
            System.out.println(e);
        } finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Map<String, Object> data = new HashMap();
        data.put("name", "法外狂徒张三");
        data.put("age", "24");
        Map<String, Object> data1 = new HashMap();
        data1.put("dateMap",data);
        pdfOut(data1);
//        pdfExport(data1);
    }
}

