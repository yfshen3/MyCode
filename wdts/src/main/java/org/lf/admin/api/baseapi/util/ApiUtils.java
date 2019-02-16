package org.lf.admin.api.baseapi.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: sunwill
 * @date: 2018/5/5
 */
public class ApiUtils {

    /**
     * 生成api签名
     *
     * @param paramMap
     * @param appSecret
     * @return
     */
    public static final String generateApiSign(Map<String, Object> paramMap, String appSecret) {
        // 根据指定参数生成签名
        List<String> signList = new ArrayList<>();
        signList.add("timestamp");
        signList.add("openAppID");
        signList.add("objectid");
        signList.add("objType");
        // 将参数名按ASCII码从小到大排序（字典序）
        Collections.sort(signList);
        // 拼接验证字符串
        StringBuilder signStringBuilder = new StringBuilder();
        for (String pName : signList) {
            signStringBuilder.append(pName).append("=").append(String.valueOf(paramMap.get(pName))).append("&");
        }
        // 拼接appSecret
        signStringBuilder.append("key=").append(appSecret);
        // 将拼接完成的字符串进行md5运算，并且转换为大写得到签名sign
        String generatedSign = StringUtils.toMD5(signStringBuilder.toString()).toUpperCase();
        return generatedSign;
    }

    /**
     * 生成api签名
     *
     * @param publicParams
     * @param appSecret
     * @return
     *
     *  public static final String generateApiSignByObj(PublicParamsDTO publicParams, String appSecret) {
     *         return generateApiSign(BeanUtils.beanToMap(publicParams), appSecret);
     *     }
     */

}
