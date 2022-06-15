package com.example.demo.utils;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class TestPDF {
    public static void main(String[] args) {
        // 模板文件路径
        String inputFileName = "D:\\WorkSpaces\\IdeaProjects\\springBootDemo\\orc\\src\\main\\resources\\templates\\pdf\\demoT.pdf";
        // 生成的文件路径
        String outputFileName = "E:\\pdfWork\\test9.pdf";

        OutputStream os = null;
        PdfStamper ps = null;
        PdfReader reader = null;
        PdfStamper stamper = null;

        try {
            os = new FileOutputStream(new File(outputFileName));
            // 2 读入pdf表单
            reader = new PdfReader(inputFileName);
            // 3 根据表单生成一个新的pdf
            ps = new PdfStamper(reader, os);
            // 4 获取pdf表单
            AcroFields form = ps.getAcroFields();
            // 5给表单添加中文字体
            BaseFont bf = BaseFont.createFont("templates/font/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            form.addSubstitutionFont(bf);
            // 6查询数据================================================
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("name", "夏侯惇");
            data.put("role", "战士");

            // 7遍历data 给pdf表单表格赋值
            for (String key : data.keySet()) {
                form.setField(key, data.get(key).toString());
            }
            ps.setFormFlattening(true);
            System.out.println("===============PDF导出成功=============");
        } catch (Exception e) {
            System.out.println("===============PDF导出失败=============");
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                reader.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
