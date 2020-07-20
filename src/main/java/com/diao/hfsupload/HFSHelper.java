package com.diao.hfsupload;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.cnki.hfs.service.HfsClientService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class HFSHelper {
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static HfsClientService hfsClientService = null;
    private static String ip;
    private static Integer port;
    private static Integer appID;
    private static String appName;
    private static String appKey;
    private static boolean initFlag;

    public static boolean init(String ip, Integer port, Integer appID, String appName, String appKey) {
        boolean result = false;
        try {
            HFSHelper.ip = ip;
            HFSHelper.port = port;
            HFSHelper.appID = appID;
            HFSHelper.appName = appName;
            HFSHelper.appKey = appKey;

            //hfsClientService = new HfsClientService();
            //hfsClientService.loadDriver(new HfsDriver(ip, port, appID, appName, appKey));
            //hfsClientService.loadDriver(new HfsDriver("192.168.105.25", 8810, 1024, "HFMS", "f4b871d85cd746021b451487849e3cdf"));
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initFlag = result;
        return result;
    }

//    public static boolean upload(String hfsKey, String filePath) {
//        boolean result = false;
//        try {
//            if (initFlag && hfsClientService != null) {
//                result = hfsClientService.uploadFile(filePath, hfsKey);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//    public static boolean upload(String hfsKey, String fileName, byte[] content) {
//        boolean result = false;
//        try {
//            if (initFlag && hfsClientService != null) {
//                ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
//                result = hfsClientService.uploadFile(appID, inputStream, fileName, hfsKey, getMD5String(content));
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//    public static boolean upload(String hfsKey, String fileName, InputStream inputStream) {
//        boolean result = false;
//        try {
//            if (initFlag && hfsClientService != null) {
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                byte[] buff = new byte[100];
//                int rc = 0;
//                while ((rc = inputStream.read(buff, 0, 100)) > 0) {
//                    byteArrayOutputStream.write(buff, 0, rc);
//                }
//                result = upload(hfsKey, fileName, byteArrayOutputStream.toByteArray());
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//    public static boolean download(String hfsKey, String targetFilePath) {
//        boolean result = false;
//        try {
//            if (initFlag && hfsClientService != null && checkFileExist(hfsKey)) {
//                result = hfsClientService.downloadFile(hfsKey, targetFilePath);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//    public static byte[] download(String hfsKey) {
//        byte[] result = null;
//        try {
//            if (initFlag && hfsClientService != null && checkFileExist(hfsKey)) {
//                ByteArrayOutputStream output = new ByteArrayOutputStream();
//                boolean flag = hfsClientService.downloadFile(output, hfsKey);
//                if (flag) {
//                    result = output.toByteArray();
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//    public static byte[] download(String hfsKey, HttpServletResponse response) {
//        byte[] result = null;
//        try {
//            if (initFlag && hfsClientService != null && checkFileExist(hfsKey)) {
//                ByteArrayOutputStream output = new ByteArrayOutputStream();
//                boolean flag = hfsClientService.downloadFile(output, hfsKey);
//                if (flag) {
//                    String fileType = "";
//                    if (hfsKey.contains(".")) {
//                        fileType = hfsKey.substring(hfsKey.lastIndexOf(".") + 1);
//                    }
//                    response.setContentLengthLong(output.size());
//                    response.setContentType(parserMime(fileType));
//                    output.writeTo(response.getOutputStream());
//                    response.getOutputStream().flush();
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//    public static String downloadString(String hfsKey) {
//        String result = null;
//        try {
//            result = new String(download(hfsKey), "utf-8");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//    public static OutputStream downloadStream(String hfsKey) {
//        OutputStream result = null;
//        try {
//            if (initFlag && hfsClientService != null && checkFileExist(hfsKey)) {
//                ByteArrayOutputStream output = new ByteArrayOutputStream();
//                boolean flag = hfsClientService.downloadFile(output, hfsKey);
//                if (flag) {
//                    result = output;
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//    public static boolean checkFileExist(String hfsKey) {
//        boolean result = false;
//        try {
//            if (initFlag && hfsClientService != null) {
//                System.out.println("check hfs filename = "+hfsKey);
//                result = hfsClientService.isFileExist(hfsKey);
//                System.out.println("check hfs result = "+result);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//    public static long getFileSize(String hfsKey) {
//        long result = 0;
//        try {
//            if (initFlag) {
//                Pointer pointer = Clibrary.INSTANTCE.OpenStream(hfsKey, "rb");
//                Clibrary.HFS_FILE_INFO info[] = new Clibrary.HFS_FILE_INFO[1];
//                boolean streamInfoFlag = Clibrary.INSTANTCE.GetStreamInfo(pointer, info);
//                if (streamInfoFlag) {
//                    result = info[0].uiFileSize;
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }

    private static String getMD5String(byte[] bytes) {
        String result = "";
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(bytes);
            result = bufferToHex(messagedigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuilder stringbuffer = new StringBuilder(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            char c0 = hexDigits[(bytes[l] & 0xf0) >> 4];
            char c1 = hexDigits[bytes[l] & 0xf];
            stringbuffer.append(c0);
            stringbuffer.append(c1);
        }
        return stringbuffer.toString();
    }

    private static String parserMime(String fileType) {
        String result = "";
        try {
            switch (fileType.toLowerCase()) {
                case "mp3":
                case "mpeg":
                    result = "audio/mpeg";
                    break;
                case "mov":
                    result = "video/quicktime";
                    break;
                case "jpg":
                case "jpeg":
                    result = "image/jpeg";
                    break;
                case "json":
                    result = "text/json";
                    break;
                case "csv":
                    result = "text/csv";
                    break;
                case "xls":
                    result = "application/vnd.ms-excel";
                    break;
                case "xlsx":
                    result = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                    break;
                case "doc":
                    result = "application/msword";
                    break;
                case "docx":
                    result = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                    break;
                default:
                    result = "application/octet-stream";
                    break;
            }
        } catch (Exception ex) {
        }
        return result;
    }


    private static String getImgUrl;
    private static String uploadImgUrl;
    private static String fileExistUrl;

    //@Value("${modelConfig.getImgUrl}")
    public  void setGetImgUrl(String getImgUrl) {
        this.getImgUrl = getImgUrl;
    }

    //@Value("${modelConfig.uploadImgUrl}")
    //http://192.168.105.25:8088/api/Resource/UploadFile
    public void setUploadImgUrl(String uploadImgUrl) {
        this.uploadImgUrl = uploadImgUrl;
    }

    //@Value("${modelConfig.fileExistUrl}")
    public void setFileExistUrl(String fileExistUrl) {
        this.fileExistUrl = fileExistUrl;
    }

    public static boolean upload(String hfsKey, String filePath) {
        boolean result = false;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Map<String, String> map = new HashMap<>();
                map.put("filename", hfsKey);
                HttpClientHelper.getInstance().uploadFileImpl(uploadImgUrl, filePath, hfsKey, map);
                result = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static boolean upload(String hfsKey, String fileName, byte[] content) {
        boolean result = false;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("filename", hfsKey);
            InputStream in = new ByteArrayInputStream(content);
            HttpClientHelper.getInstance().httpUploadFile(uploadImgUrl, in, hfsKey, map, 50000);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static boolean upload(String hfsKey, String fileName, InputStream inputStream) {
        boolean result = false;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("filename", hfsKey);
            HttpClientHelper.getInstance().httpUploadFile(uploadImgUrl, inputStream, hfsKey, map, 50000);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static boolean download(String hfsKey, String targetFilePath) {
        boolean result = false;
        try {
            String url = getImgUrl + hfsKey;
            HttpClientHelper.getInstance().httpDownloadFile(url, targetFilePath, null, null);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static byte[] download(String hfsKey) {
        byte[] result = null;
        try {
            String url = getImgUrl + hfsKey;
            result = HttpClientHelper.getInstance().getImgFile(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

//    public static byte[] download(String hfsKey, HttpServletResponse response) {
//        byte[] result = null;
//        try {
//            String url = getImgUrl + hfsKey;
//            HttpClientHelper.getInstance().httpDownloadFile(url, response);
//        } catch (Exception ex) {
//
//        }
//        return result;
//    }

    public static byte[] downloadMini(String hfsKey, HttpServletResponse response,  Integer w, Integer h) {
        byte[] result = null;
        try {
            String url = getImgUrl + hfsKey;
            HttpClientHelper.getInstance().httpDownloadFileMini(url, response,w,h);
        } catch (Exception ex) {

        }
        return result;
    }

    public static String downloadString(String hfsKey) {
        String result = null;
        try {
            result = new String(download(hfsKey), "utf-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static OutputStream downloadStream(String hfsKey) {
        OutputStream result = null;
        try {
            byte[] content = download(hfsKey);
            result = new ByteArrayOutputStream();
            result.write(content);
            result.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static boolean checkFileExist(String hfsKey) {
        boolean result = false;
        try {
            String content = HttpClientHelper.getInstance().httpGet(fileExistUrl + hfsKey);
            JSONObject resultJson = JSON.parseObject(content);
            if (resultJson.getInteger("Status") == 200) {
                result = resultJson.getBoolean("data");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static long getFileSize(String hfsKey) {
        long result = 0;
        try {
            String content = downloadString(hfsKey);
            if (content != null) {
                result = content.length();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
