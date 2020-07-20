package com.diao.hfsupload;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClientHelper {

    /**
     * 最大线程池
     */
    public static final int THREAD_POOL_SIZE = 5;

    public interface HttpClientDownLoadProgress {
        public void onProgress(int progress);
    }

    private static HttpClientHelper httpClientDownload;

    private ExecutorService downloadExcutorService;

    private HttpClientHelper() {
        downloadExcutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static HttpClientHelper getInstance() {
        if (httpClientDownload == null) {
            httpClientDownload = new HttpClientHelper();
        }
        return httpClientDownload;
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     */
    public void download(final String url, final String filePath) {
        downloadExcutorService.execute(new Runnable() {

            @Override
            public void run() {
                httpDownloadFile(url, filePath, null, null);
            }
        });
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     * @param progress 进度回调
     */
    public void download(final String url, final String filePath,
                         final HttpClientDownLoadProgress progress) {
        downloadExcutorService.execute(new Runnable() {

            @Override
            public void run() {
                httpDownloadFile(url, filePath, progress, null);
            }
        });
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     */
    public void httpDownloadFile(String url, String filePath,
                                  HttpClientDownLoadProgress progress, Map<String, String> headMap) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            setGetHead(httpGet, headMap);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity httpEntity = response1.getEntity();
                long contentLength = httpEntity.getContentLength();
                InputStream is = httpEntity.getContent();
                // 根据InputStream 下载文件
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int r = 0;
                long totalRead = 0;
                while ((r = is.read(buffer)) > 0) {
                    output.write(buffer, 0, r);
                    totalRead += r;
                    if (progress != null) {// 回调进度
                        progress.onProgress((int) (totalRead * 100 / contentLength));
                    }
                }
                FileOutputStream fos = new FileOutputStream(filePath);
                output.writeTo(fos);
                output.flush();
                output.close();
                fos.close();
                EntityUtils.consume(httpEntity);
            } finally {
                response1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//    public void httpDownloadFile(String url, HttpServletResponse response) {
//
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        try {
//            HttpGet httpGet = new HttpGet(url);
//            CloseableHttpResponse response1 = httpclient.execute(httpGet);
//            try {
//                System.out.println(response1.getStatusLine());
//                HttpEntity httpEntity = response1.getEntity();
//                long contentLength = httpEntity.getContentLength();
//                InputStream is = httpEntity.getContent();
//
//                response.setContentLengthLong(contentLength);
//                //String fileName = image.getName();
//                //String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//                response.setHeader("Content-Type", "image/jpeg");
//                OutputStream toClient = response.getOutputStream();
//
//                //将请求返回的页面的流先放到buffer中，放一个buffer，输出response就写入一个buffer。
//                byte[] buffer = new byte[4096];
//                int r = 0;
//                while ((r = is.read(buffer)) > 0) {
//                    toClient.write(buffer, 0, r);
//                }
//
//                toClient.flush();
//
//            } finally {
//                response1.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                httpclient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void httpDownloadFileMini(String url, HttpServletResponse response,Integer w, Integer h) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity httpEntity = response1.getEntity();
                long contentLength = httpEntity.getContentLength();
                InputStream inputStream = httpEntity.getContent();

                BufferedImage prevImage = ImageIO.read(inputStream);
                int width = prevImage.getWidth();
                int height = prevImage.getHeight();

                int newWidth = width;
                int newHeight = height;

                /*缩放*/
                if (w != null) {
                    double percent = (w / (double)width);
                    newWidth = (int) (width * percent);
                    newHeight = (int) (height * percent);

                    if (h != null)
                        newHeight = h;
                }

                Graphics2D oldG = prevImage.createGraphics();

                BufferedImage newimage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
                Graphics2D graphics = newimage.createGraphics();
                newimage = graphics.getDeviceConfiguration().createCompatibleImage(newWidth,newHeight,Transparency.TRANSLUCENT);
                graphics.dispose();
                graphics = newimage.createGraphics();

                //graphics.setBackground(oldG.getBackground());
                graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);

                response.setHeader("Content-Type", "image/png");
                OutputStream toClient = response.getOutputStream();
                ImageIO.write(newimage, "png", toClient);
                toClient.flush();

            } finally {
                response1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getImgFile(String url) {
        byte[] result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                HttpEntity httpEntity = response.getEntity();
                Long contentLength = httpEntity.getContentLength();
                if (contentLength != -1)
                {
                    InputStream in = httpEntity.getContent();
                    result = new byte[contentLength.intValue()];
                    int readedLength = 0;
                    int totalLength = 0;
                    while (readedLength != contentLength) {
                        readedLength = in.read(result, totalLength, 10 * 1024);
                        if (-1 == readedLength) {
                            break;
                        }
                        totalLength += readedLength;
                    }
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

//    public void httpDownloadOfficeFile(String url, HttpServletResponse response) {
//
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        try {
//            HttpGet httpGet = new HttpGet(url);
//            CloseableHttpResponse response1 = httpclient.execute(httpGet);
//            try {
//                System.out.println(response1.getStatusLine());
//                HttpEntity httpEntity = response1.getEntity();
//                long contentLength = httpEntity.getContentLength();
//                InputStream is = httpEntity.getContent();
//
//                response.setContentLengthLong(contentLength);
//                //String fileName = image.getName();
//                //String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
////				response.setHeader("Content-Type","image/jpeg");
//                OutputStream toClient = response.getOutputStream();
//
//                //将请求返回的页面的流先放到buffer中，放一个buffer，输出response就写入一个buffer。
//                byte[] buffer = new byte[4096];
//                int r = 0;
//                while ((r = is.read(buffer)) > 0) {
//                    toClient.write(buffer, 0, r);
//                }
//
//                toClient.flush();
//
//            } finally {
//                response1.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                httpclient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public List<List<String>> getCsv2List(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        StringBuilder sb = new StringBuilder();
        List<List<String>> rowList = new ArrayList<List<String>>();
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                HttpEntity httpEntity = response1.getEntity();
                InputStream is = httpEntity.getContent();
                String charset = "utf-8";
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
                //BufferedReader reader = new BufferedReader(new FileReader("D:/csvFileFolder/a8b4caf1-883f-4d9d-ae3a-9653c17b0d3d.csv"));//换成你的文件名
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] rowArr = line.split(",", -1);//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    List<String> row = Arrays.asList(rowArr);
                    rowList.add(row);
                }
            } finally {
                response1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rowList;
    }

    public String httpDownloadJsonFile(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        StringBuilder sb = new StringBuilder();
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                HttpEntity httpEntity = response1.getEntity();
                InputStream is = httpEntity.getContent();
                String charset = "utf-8";
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } finally {
                response1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public String httpGet(String url) {
        return httpGet(url, null);
    }

    /**
     * http get请求
     *
     * @param url
     * @return
     */
    public String httpGet(String url, Map<String, String> headMap) {
        String responseContent = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            setGetHead(httpGet, headMap);
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity = response1.getEntity();
                responseContent = getRespString(entity);
                System.out.println("debug:" + responseContent);
                EntityUtils.consume(entity);
            } finally {
                response1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    public String httpPost(String url, Map<String, String> paramsMap) {
        return httpPost(url, paramsMap, null);
    }

    /**
     * http的post请求
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public String httpPost(String url, Map<String, String> paramsMap,
                           Map<String, String> headMap) {
        String responseContent = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            setPostHead(httpPost, headMap);
            setPostParams(httpPost, paramsMap);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                responseContent = getRespString(entity);
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("responseContent = " + responseContent);
        return responseContent;
    }

    /**
     * 设置POST的参数
     *
     * @param httpPost
     * @param paramsMap
     * @throws Exception
     */
    private void setPostParams(HttpPost httpPost, Map<String, String> paramsMap)
            throws Exception {
        if (paramsMap != null && paramsMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<String> keySet = paramsMap.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        }
    }

    /**
     * 设置http的HEAD
     *
     * @param httpPost
     * @param headMap
     */
    private void setPostHead(HttpPost httpPost, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            for (String key : keySet) {
                httpPost.addHeader(key, headMap.get(key));
            }
        }
    }

    /**
     * 设置http的HEAD
     *
     * @param httpGet
     * @param headMap
     */
    private void setGetHead(HttpGet httpGet, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            for (String key : keySet) {
                httpGet.addHeader(key, headMap.get(key));
            }
        }
    }

    /**
     * 上传文件
     *
     * @param serverUrl       服务器地址
     * @param localFilePath   本地文件路径
     * @param serverFieldName
     * @param params
     * @return
     * @throws Exception
     */
    public String uploadFileImpl(String serverUrl, String localFilePath,
                                 String serverFieldName, Map<String, String> params)
            throws Exception {
        String respStr = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(serverUrl);
            httppost.setConfig(RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).build());
            FileBody binFileBody = new FileBody(new File(localFilePath));

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                    .create();
            // add the file params
            multipartEntityBuilder.addPart(serverFieldName, binFileBody);
            // 设置上传的其他参数
            setUploadParams(multipartEntityBuilder, params);

            HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                respStr = getRespString(resEntity);
                EntityUtils.consume(resEntity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        System.out.println("resp=" + respStr);
        return respStr;
    }

    /**
     * 使用HttpClient4.5 post提交multipart/form-data数据实现多文件上传
     *
     * @param url            请求地址
     * @param multipartFiles post提交的文件列表
     * @param fileParName    fileKey
     * @param params         附带的文本参数
     * @param timeout        请求超时时间(毫秒)
     * @return
     */
//    public static Map<String, String> httpUploadFile(String url, List<MultipartFile> multipartFiles, String fileParName,
//                                                     Map<String, Object> params, int timeout) {
//        Map<String, String> resultMap = new HashMap<String, String>();
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String result = "";
//        try {
//            HttpPost httpPost = new HttpPost(url);
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
//            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//            String fileName = null;
//            MultipartFile multipartFile = null;
//            for (int i = 0; i < multipartFiles.size(); i++) {
//                multipartFile = multipartFiles.get(i);
//                fileName = multipartFile.getOriginalFilename();
//                builder.addBinaryBody(fileParName, multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
//            }
//            //决中文乱码
//            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
//            for (Map.Entry<String, Object> entry : params.entrySet()) {
//                if (entry.getValue() == null)
//                    continue;
//                // 类似浏览器表单提交，对应input的name和value
//                builder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);
//            }
//            HttpEntity entity = builder.build();
//            httpPost.setEntity(entity);
//            HttpResponse response = httpClient.execute(httpPost);// 执行提交
//
//            // 设置连接超时时间
//            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
//                    .setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
//            httpPost.setConfig(requestConfig);
//
//            HttpEntity responseEntity = response.getEntity();
//            resultMap.put("code", String.valueOf(response.getStatusLine().getStatusCode()));
//            resultMap.put("result", "");
//            if (responseEntity != null) {
//                // 将响应内容转换为字符串
//                result = EntityUtils.toString(responseEntity, java.nio.charset.Charset.forName("UTF-8"));
//                resultMap.put("result", result);
//            }
//        } catch (Exception e) {
//            resultMap.put("code", "error");
//            resultMap.put("result", "HTTP请求出现异常: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            try {
//                httpClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return resultMap;
//    }

    /**
     * 使用HttpClient4.5 post提交multipart/form-data数据实现多文件上传
     *
     * @param url            请求地址
     * @param multipartFiles post提交的文件列表
     * @param fileParName    fileKey
     * @param params         附带的文本参数
     * @param timeout        请求超时时间(毫秒)
     * @return
     */
    public static Map<String, String> httpUploadFile(String url, InputStream is, String fileName,
                                                     Map<String, String> params, int timeout) {
        Map<String, String> resultMap = new HashMap<String, String>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody(fileName, is, ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            //决中文乱码
            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getValue() == null)
                    continue;
                // 类似浏览器表单提交，对应input的name和value
                builder.addTextBody(entry.getKey(), entry.getValue(), contentType);
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交

            // 设置连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
            httpPost.setConfig(requestConfig);

            HttpEntity responseEntity = response.getEntity();
            resultMap.put("code", String.valueOf(response.getStatusLine().getStatusCode()));
            resultMap.put("result", "");
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, java.nio.charset.Charset.forName("UTF-8"));
                resultMap.put("result", result);
            }
        } catch (Exception e) {
            resultMap.put("code", "error");
            resultMap.put("result", "HTTP请求出现异常: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public String uploadFile(String serverUrl, File file,
                             String serverFieldName, Map<String, String> params)
            throws Exception {
        String respStr = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(serverUrl);
            FileBody binFileBody = new FileBody(file);

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                    .create();
            // add the file params
            multipartEntityBuilder.addPart(serverFieldName, binFileBody);
            // 设置上传的其他参数
            setUploadParams(multipartEntityBuilder, params);

            HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                respStr = getRespString(resEntity);
                EntityUtils.consume(resEntity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        System.out.println("resp=" + respStr);
        return respStr;
    }

    /**
     * 设置上传文件时所附带的其他参数
     *
     * @param multipartEntityBuilder
     * @param params
     */
    private void setUploadParams(MultipartEntityBuilder multipartEntityBuilder,
                                 Map<String, String> params) {
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                multipartEntityBuilder
                        .addPart(key, new StringBody(params.get(key),
                                ContentType.TEXT_PLAIN));
            }
        }
    }

    /**
     * 将返回结果转化为String
     *
     * @param entity
     * @return
     * @throws Exception
     */
    private String getRespString(HttpEntity entity) throws Exception {
        if (entity == null) {
            return null;
        }
        InputStream is = entity.getContent();
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[4096];
        int r = 0;
        while ((r = is.read(buffer)) > 0) {
            strBuf.append(new String(buffer, 0, r, "UTF-8"));
        }
        return strBuf.toString();
    }
}
