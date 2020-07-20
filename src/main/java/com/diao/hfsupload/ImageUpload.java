package com.diao.hfsupload;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author: chenzhidiao
 * @date: 2020/7/16 14:53
 * @description:
 * @version: 1.0
 */
public class ImageUpload {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        HFSHelper hfsHelper = new HFSHelper();
        hfsHelper.setUploadImgUrl("http://192.168.103.90:8088/api/Resource/UploadFile");
        HttpClientHelper httpClientHelper = HttpClientHelper.getInstance();
        FileInputStream fis = new FileInputStream(new File("E:\\work\\ancient_books\\images\\5a2a3b3483b942490b78ff25.jpeg"));
        ArrayList<String> files = new ArrayList<String>();
        File file = new File("E:\\work\\ancient_books\\images");
        File[] tempLists = file.listFiles();
        System.out.println(tempLists.length);
        for (File file1 : tempLists) {
            if (file1.isFile()&&file1.getName().endsWith(".jpeg")){
                String name = file1.getName();
                String fileName = file1.getAbsolutePath();
                HFSHelper.upload(name,fileName);
                Thread.sleep(2000);
            }
        }
        //HFSHelper.upload("5a2a3b3483b942490b78ff25.jpeg","E:\\work\\ancient_books\\images\\5a2a3b3483b942490b78ff25.jpeg");
//        byte[] bytes = HFSHelper.download("5a2a3b3483b942490b78ff25.jpeg");
//        System.out.println(bytes);


    }
}
