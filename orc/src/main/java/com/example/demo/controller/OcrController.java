package com.example.demo.controller;


import com.example.demo.utils.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


@RestController
public class OcrController {

    /**
     * 第三种对象注入
     */
    @Autowired
    private OcrProperties ocrProperties;

    @GetMapping("/hello")
    public String hello() {
        return "hello ocr";
    }

    @ApiOperation(value = "图片识别", notes = "图片识别")
    @PostMapping(value = "/ocr", headers = "content-type=multipart/form-data")
    @ResponseBody
    public Object ocr(@ApiParam("文件") @RequestParam(value = "image") MultipartFile image,
                      @ApiParam(name = "language",
                              value = "语言包(默认:chi_sim 简体中文),支持数字中文简体(chi_sim),中文简体竖版(chi_sim_vert)，" +
                                      "繁体((chi_tra))，繁体((chi_tra_vert))，英文(eng)")
                      @RequestParam(name = "language", defaultValue = "chi_sim") String language) throws Exception {
        if (null == image) {
            return ResponseUtil.badArgument();
        }
        String fileName = image.getOriginalFilename();    // 文件名称
        String suffixName = fileName.substring(fileName.lastIndexOf("."));    // 图片后缀
        // 判断文件后缀是否为后端默认的后缀名
        if (isImageFile(suffixName)) {

            ITesseract instance = new Tesseract();
            //设置训练库的位置
            instance.setDatapath(ocrProperties.getTessdata());
//            instance.setLanguage("chi_sim");
            instance.setLanguage(language);

            BufferedImage testImage = ImageIO.read(image.getInputStream());
            String code = instance.doOCR(testImage);
            return ResponseUtil.ok(code);
        }
        return ResponseUtil.unsupportedFormat();
    }


    // 判断后缀
    private Boolean isImageFile(String fileName) {
//        String[] img_type = new String[]{".jpg", ".jpeg", ".png", ".bmp"};
        if (fileName == null) {
            return false;
        }
        fileName = fileName.toLowerCase();

        String[] img_type = ocrProperties.getFile_suffix();

        for (String type : img_type) {
            if (fileName.endsWith(type)) {
                return true;
            }
        }
        return false;
    }

}
