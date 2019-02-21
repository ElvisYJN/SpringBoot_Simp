package com.xinhuanet.tool;

import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 模拟HTTP客户端访问，测试相关接口
 *
 * @author lvwei
 */
@SuppressWarnings("all")
public class HttpClientTool {
    private final static boolean DEBUG = true;

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientTool.class);

    private final static String CONTENT_TYPE_TEXT_JSON = "text/json";

    public static void main(String args[]) throws Exception {
    }

    /**
     * GET页面内容
     * @param url
     * @return
     */
    public static String getResponseBody(String url) {
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setSoTimeout(1000);
        httpClient.getParams().setConnectionManagerTimeout(1000);
        GetMethod getMethod = null;
        getMethod = new GetMethod(url);
        getMethod.getParams().setSoTimeout(1000);
        String responseBody = null;
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != org.apache.commons.httpclient.HttpStatus.SC_OK) {
                LOGGER.error("Method failed: " + getMethod.getStatusLine());
            }
            // 读取内容
            responseBody = getMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
            httpClient.getHttpConnectionManager().closeIdleConnections(0);
        }
        return responseBody;
    }

    /**
     * 下载文件
     * @param remoteFileName
     * @param localFileName
     */
    public static void downLoad(String remoteFileName, String localFileName) {
        HttpClient client = new HttpClient();
        GetMethod get = null;
        FileOutputStream output = null;

        try {
            get = new GetMethod(remoteFileName);

            int i = client.executeMethod(get);

            if (200 == i) {

                File storeFile = new File(localFileName);
                output = new FileOutputStream(storeFile);

                // 得到网络资源的字节数组,并写入文件
                output.write(get.getResponseBody());
            } else {
                LOGGER.error("DownLoad file " + remoteFileName + " occurs exception, the error code is :" + i);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }

            get.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }
    }

    /**
     * json参数
     *
     * @param url
     * @param param
     * @param proxy eg:new HttpHost("192.168.2.59",8080), 如果没有使用代理 ：null
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postRequest(String url, Map<String, Object> param, HttpHost proxy) throws ClientProtocolException, IOException {

        Gson gson = new Gson();
        String parameter = gson.toJson(param);
        StringEntity se = new StringEntity(parameter, Charset.forName("UTF-8"));//解决请求参数乱码
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httppost.setEntity(se);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse res = null;
        try {
            if (proxy != null) {
                RequestConfig config = RequestConfig.custom().setProxy(proxy).setMaxRedirects(3)
                        .setConnectTimeout(30000).build();
                httppost.setConfig(config);
            }
            res = httpclient.execute(httppost);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity hentity = res.getEntity();
                return EntityUtils.toString(hentity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (res != null) {
                res.close();
                httpclient.close();
            }
        }
        return null;
    }

    /**
     * POST普通参数
     *
     * @param pageUrl eg: "http://www.news.cn"
     * @param pdata
     * @param proxy   eg:new HttpHost("192.168.2.59",8080), 如果没有使用代理 ：null
     * @return
     * @throws IOException
     */
    public static String post(String pageUrl, Map<String, String> pdata, HttpHost proxy) throws IOException {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : pdata.entrySet()) {
            String name = entry.getKey().toString();
            String value = entry.getValue();
            if (value != null) {
                formparams.add(new BasicNameValuePair(name, value));
            }
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
        HttpPost httppost = new HttpPost(pageUrl);
        httppost.setEntity(entity);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse res = null;
        try {
            if (proxy != null) {
                RequestConfig config = RequestConfig.custom().setProxy(proxy).setMaxRedirects(3)
                        .setConnectTimeout(30000).build();
                httppost.setConfig(config);
            }
            res = httpclient.execute(httppost);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity hentity = res.getEntity();
                return EntityUtils.toString(hentity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (res != null) {
                res.close();
            }
        }

        return null;
    }

    /**
     * @param pageUrl
     * @param header
     * @param pdata
     * @param proxy
     * @param charset
     * @return
     * @throws IOException
     */
    public static String post(String pageUrl, Map<String, String> header, Map<String, String> pdata, HttpHost proxy, String charset) throws IOException {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : pdata.entrySet()) {
            String name = entry.getKey().toString();
            String value = entry.getValue();
            if (value != null) {
                formparams.add(new BasicNameValuePair(name, value));
            }
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, charset);
        entity.setContentEncoding(charset);
        HttpPost httppost = new HttpPost(pageUrl);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            String name = entry.getKey().toString();
            String value = entry.getValue();
            if (value != null) {
                httppost.setHeader(name, value);
            }
        }
        httppost.setEntity(entity);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse res = null;
        try {
            if (proxy != null) {
                RequestConfig config = RequestConfig.custom().setProxy(proxy).setMaxRedirects(3)
                        .setConnectTimeout(30000).build();
                httppost.setConfig(config);
            }
            res = httpclient.execute(httppost);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity hentity = res.getEntity();
                return EntityUtils.toString(hentity, Charset.forName(charset));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (res != null) {
                res.close();
            }
        }

        return null;
    }

    public static String getURL(String url, Map<String, String> pdata, HttpHost proxy) throws Exception {
        url = getParamsURL(url, pdata);
        HttpGet get = new HttpGet(url);
        return get(get, proxy);

    }

    public static String getParamsURL(String url, Map<String, String> pdata) throws Exception {
        log("Request:");
        log("GET:" + url);
        if (null != pdata && pdata.size() > 0) {
            String encodedParams = HttpClientTool.encodeParameters(pdata);
            if (-1 == url.indexOf("?")) {
                url += "?" + encodedParams;
            } else {
                url += "&" + encodedParams;
            }
        }
        return url;
    }

    public static String encodeParameters(Map<String, String> postParams) {
        StringBuffer buf = new StringBuffer();
        Iterator<String> it = postParams.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (i != 0) {
                buf.append("&");
            }
            try {
                String key = it.next();
                buf.append(URLEncoder.encode(key, "UTF-8")).append("=")
                        .append(URLEncoder.encode(postParams.get(key), "UTF-8"));
            } catch (UnsupportedEncodingException neverHappen) {
            }
            i++;
        }
        return buf.toString();
    }

    public static String get(HttpGet method, HttpHost proxy) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse res = null;
        try {
            if (proxy != null) {
                RequestConfig config = RequestConfig.custom().setProxy(proxy).setMaxRedirects(3)
                        .setConnectTimeout(30000).build();
                method.setConfig(config);
                method.addHeader("Content-type", "text/html; charset=utf-8");
            }
            res = httpclient.execute(method);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (res != null) {
                res.close();
            }
        }
        return null;
    }


    private static void log(String message) {
        if (DEBUG) {
            LOGGER.debug(message);
        }
    }

}
