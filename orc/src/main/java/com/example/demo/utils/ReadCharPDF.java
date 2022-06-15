package com.example.demo.utils;

import com.sun.org.apache.xml.internal.security.Init;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ReadCharPDF {
    public static void  test1(){
        String pdfpath="E:\\test";//PDF文件的路径
        String outputPath="E:\\test";//以txt文件输出的路径
        File[] files=null;
        try
        {
            File pdffile=new File(pdfpath);
            files=pdffile.listFiles();
            for(File file:files)
            {
                PDDocument doc= PDDocument.load(file);
                int pagenum=doc.getNumberOfPages();
                FileOutputStream fos=new FileOutputStream( outputPath+"/"+file.getName().replace(".pdf","")+".txt");
                Writer writer=new OutputStreamWriter(fos,"UTF-8");
                PDFTextStripper stripper=new PDFTextStripper();
                stripper.setSortByPosition(true);
                stripper.setStartPage(0);
                stripper.setEndPage(pagenum);
                stripper.writeText(doc,writer);
                writer.close();
                doc.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void test2(){
       String pdfpath="E:\\test";//PDF文件的路径
        String outputPath="E:\\test";//以图片文件输出的路径
        File[] files=null;
        try
        {
            File pdffile = new File(pdfpath);
            files=pdffile.listFiles();
            for(File file:files)
            {
                PDDocument doc=PDDocument.load(file);
                BufferedOutputStream outputStream=null;
                int pageCount=doc.getNumberOfPages();
                PDFRenderer pdfRenderer=new PDFRenderer(doc);
                for(int i=0;i<pageCount;i++)
                {
                    String imgpath=outputPath+"/"+file.getName().replace(".pdf","");
                    File img_temp=new File(imgpath);
                    if(!img_temp.exists())
                    {
                        img_temp.mkdir();
                    }
                    imgpath=imgpath+"/"+file.getName().replace(".pdf","");
                    imgpath=imgpath+"_"+i+".png";
                    outputStream = new BufferedOutputStream(new FileOutputStream(imgpath));
                    BufferedImage image = pdfRenderer.renderImageWithDPI(i, 90, ImageType.RGB);
                    ImageIO.write(image, "png", outputStream);
                    outputStream.close();
                    System.out.println("The "+file.getName()+"'s"+i+"' has complete");
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public static void main(String[] args)
    {
        test1();


    }
}
