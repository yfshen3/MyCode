package org.lf.admin.api.baseapi.util;

import lombok.extern.slf4j.Slf4j;
import org.lf.admin.api.baseapi.core.ServiceException;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * 微信工具类
 *
 * @author sunwill
 */
@Slf4j
public class WxUtil {
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";


    /**
     * 生成签名
     *
     * @return
     */
    public static final String getSign(Map<String, Object> map, String appSecret) {
        if (map == null || map.isEmpty() || StringUtils.isEmpty(appSecret)) {
            throw new ServiceException("参数不全或者找不到appSecret");
        }
        // 将得到的字符串进行MD5运算，再转换为大写，得到sign值。
        String queryStr = URLBuilder.httpBuildQuery(map,true);
        try {
            queryStr = URLDecoder.decode(queryStr, URLBuilder.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            queryStr = URLDecoder.decode(queryStr);
        }
        return StringUtils.toMD5(queryStr + "&key=" + appSecret).toUpperCase();
    }


    /**
     * 发送请求以https方式发送请求并将请求响应内容以String方式返回
     *
     * @param path   请求路径
     * @param method 请求方法
     * @param body   请求数据体
     * @return 请求响应内容转换成字符串信息
     */
    public static final String downloadString(String path, String method, String body) {
        if (path == null || method == null) {
            return null;
        }
        method = method.toUpperCase();
        String response = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection conn = null;
        try {
            TrustManager[] tm = {new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            log.info(path);
            URL url = new URL(path);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }

            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            response = buffer.toString();
        } catch (Exception e) {

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return response;
    }

}
