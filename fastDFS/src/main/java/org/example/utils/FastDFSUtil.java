package org.example.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;

@Slf4j
@Data
@Component
public class FastDFSUtil {
    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient1 storageClient1;
    static {
        try {
            String filePath = new ClassPathResource("fastdfs_client.conf").getFile().getAbsolutePath();;
            ClientGlobal.init(filePath);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getTrackerServer();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient1 = new StorageClient1(trackerServer, storageServer);
        } catch (Exception e) {
            log.error("FastDFS Client Init Fail!",e);
        }
    }
    /**
     * 上传文件
     * @param fileContent 文件内容
     * @param extName 后缀名
     * @return
     * @throws Exception
     */
    public static String[] uploadFile(byte[] fileContent, String extName) {

        try {
            //upload_file()三个参数：@param fileContent ①：文件的内容，字节数组 ②：文件扩展名 ③文件扩展信息 数组
            return storageClient1.upload_file(fileContent, extName, null);
        } catch (Exception e) {
            log.error(e.toString(), e);
            return null;
        }
    }
    /**
     * 下载文件
     * @param fileId 文件id(包括groupId)
     * @return
     */
    public static byte[] downloadFile(String fileId) {
        try {
            byte[] fileByte = storageClient1.download_file1(fileId);
            return fileByte;
        } catch (Exception e) {
            log.error(e.toString(), e);
            return null;
        }
    }
    /**
     * 删除文件
     * @param fileId 带组名的路径名称 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return -1失败,0成功 ,2找不到文件
     */
    public static int deleteFile(String fileId) {

        try {
            int i = storageClient1.delete_file1(fileId);
            log.info("delete file successfully!!!" + i);
            return 1;
        } catch (Exception e) {
            log.error(e.toString(), e);
            return 0;
        }
    }
    /**
     * @Description  ：获取文件信息
     * @author       : lihuimin
     * @param        : fileId
     * @return       :
     * @date         : 2021-07-27 17:10
     */
    public static FileInfo getFile(String fileId) {
        try {
            return storageClient1.get_file_info1(fileId);
        } catch (IOException e) {
            log.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            log.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }
    /**
     * @Description  ：获取文件上传之前保存的信息，
     * @author       : lihuimin
     * @param        : fileId
     * @return       :
     * @date         : 2021-07-27 17:22
     */
    public static void testGetFileMatedata(String fileId) {
        try {
            NameValuePair nvps[] = storageClient1.get_metadata1(fileId);
            if (null != nvps && nvps.length > 0) {
                for (NameValuePair nvp : nvps) {
                    System.out.println(nvp.getName() + ":" + nvp.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        //group1/M00/00/00/wKhkM2D_gQWAS2g_AA-itrfn0m4.tar.gz
        //group1/M00/00/00/wKhkM2D_ykmALQd9AADOwrv3BxE949.png
        //开始上传
        String path="D:\\2022-05-05_10-36-58.png";
        File f = new File(path);
        FileInputStream fin = new FileInputStream(f);
        byte[] ib = new byte[(int)f.length()];
        fin.read(ib);
        fin.close();
        String[] fileid = FastDFSUtil.uploadFile(ib,"png");
        System.out.println(fileid.length);
        if(fileid!=null&&fileid.length>0){
            for(String str:fileid){
                System.out.println(str);
            }
        }
        //上传结束
        //开始下载
//        byte[] ib = FastDFSUtil.downloadFile("group1/M00/00/00/wKhkM2D_ykmALQd9AADOwrv3BxE949.png");
//        if(ib != null){
//            String filepath ="D:\\" + "1232.png";
//            File file  = new File(filepath);
//            if(file.exists()){
//                file.delete();
//            }
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(ib,0,ib.length);
//            fos.flush();
//            fos.close();
//        }
        //下载结束
        //开始获取文件信息
//        FileInfo fileInfo = FastDFSUtil.getFile("group1/M00/00/00/wKhkM2D_ykmALQd9AADOwrv3BxE949.png");
//        System.out.println(fileInfo.toString());
        //结束获取文件信息
        //开始删除
//        int i = FastDFSUtil.deleteFile("group1/M00/00/00/wKhkM2D_gQWAS2g_AA-itrfn0m4.tar.gz");
//        System.out.println(i);
        //删除结束
        //获取文件上传之前信息
//        FastDFSUtil.testGetFileMatedata("group1/data/M00/00/00/wKhkM2D_ykmALQd9AADOwrv3BxE949.png");
    }
}

