package org.lf.admin.api.baseapi.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * URL工具类，用于生成智慧校园api签名sign和请求参数
 *
 * @author sunwill
 */
public class URLBuilder {
    /**
     * 默认字符集
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 将参数拼接为字符串，仿PHP中的http_build_query方法,参数名不进行排序;
     * 默认采用UTF-8编码
     *
     * @param params
     * @return
     */
    public static final String httpBuildQuery(Map<String, Object> params) {
        return httpBuildQuery(params, DEFAULT_CHARSET);
    }

    /**
     * 将参数拼接为字符串，仿PHP中的http_build_query方法,参数名根据hasOrder判断是否排序;
     * 默认采用UTF-8编码
     *
     * @param params
     * @param hasOrder ：true表示参数需要排序，false表示参数不排序
     * @return
     */
    public static final String httpBuildQuery(Map<String, Object> params, boolean hasOrder) {
        return httpBuildQuery(params, DEFAULT_CHARSET, hasOrder);
    }

    /**
     * 将参数拼接为字符串，仿PHP中的http_build_query方法,参数名不进行排序
     *
     * @param params
     * @param encoding
     * @return
     */
    public static final String httpBuildQuery(Map<String, Object> params, String encoding) {
        return httpBuildQuery(params, encoding, false);
    }

    /**
     * 将参数拼接为字符串
     * Build URL string from Map of params. Nested Map and Collection is also supported
     *
     * @param params   Map of params for constructing the URL Query String
     * @param encoding encoding type. If not set the "UTF-8" is selected by default
     * @param hasOrder 是否排序（签名需要对参数进行字典排序）
     * @return String of type key=value&...key=value
     * @throws UnsupportedEncodingException if encoding isnot supported
     */
    public static final String httpBuildQuery(Map<String, Object> params, String encoding, boolean hasOrder) {
        if (params == null || params.isEmpty()) {
            throw new RuntimeException("参数不能为空");
        }
        if (isEmpty(encoding)) {
            encoding = DEFAULT_CHARSET;
        }
        StringBuilder sb = new StringBuilder();
        if (hasOrder) {
            // 需要对key值按照ascll码排序
            List<String> list = new ArrayList<>(params.keySet());
            Collections.sort(list);
            for (String name : list) {
                buildTop(sb, name, params.get(name), encoding, hasOrder);
            }
        } else {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                buildTop(sb, entry.getKey(), entry.getValue(), encoding, hasOrder);
            }
        }
        return sb.toString();
    }

    private static void buildTop(StringBuilder sb, String name, Object value, String encoding, boolean hasOrder) {

        if (sb.length() > 0) {
            sb.append('&');
        }

        if (value instanceof Map) {
            List<String> baseParam = new ArrayList<>();
            baseParam.add(name);
            String str = buildUrlFromMap(baseParam, (Map) value, encoding, hasOrder);
            sb.append(str);

        } else if (value instanceof Collection) {
            List<String> baseParam = new ArrayList<>();
            baseParam.add(name);
            String str = buildUrlFromCollection(baseParam, (Collection) value, encoding, hasOrder);
            sb.append(str);

        } else {
            sb.append(encodeParam(name, encoding));
            sb.append("=");
            sb.append(encodeParam(value, encoding));
        }

    }


    private static String buildUrlFromMap(List<String> baseParam, Map<Object, Object> map,
                                          String encoding, boolean hasOrder) {
        StringBuilder sb = new StringBuilder();
        String token = null;
        if (hasOrder) {
            List<String> list = new ArrayList<>();
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                list.add(String.valueOf(it.next()));
            }
            Collections.sort(list);
            for (String name : list) {
                build4Map(sb, name, map.get(name), encoding, hasOrder, baseParam, token);
            }
        } else {
            //Build string of first level - related with params of provided Map
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                build4Map(sb, String.valueOf(entry.getKey()), entry.getValue(), encoding, hasOrder, baseParam, token);
            }

        }

        return sb.toString();
    }

    private static void build4Map(StringBuilder sb, String name, Object value,
                                  String encoding, boolean hasOrder, List<String> baseParam, String token) {
        if (sb.length() > 0) {
            sb.append('&');
        }

        if (value instanceof Map) {
            List<String> baseParam2 = new ArrayList<>(baseParam);
            baseParam2.add(name);
            String str = buildUrlFromMap(baseParam2, (Map) value, encoding, hasOrder);
            sb.append(str);

        } else if (value instanceof List) {
            List<String> baseParam2 = new ArrayList<>(baseParam);
            baseParam2.add(name);
            String str = buildUrlFromCollection(baseParam2, (List) value, encoding, hasOrder);
            sb.append(str);
        } else {
            token = getBaseParamString(baseParam) + "[" + name + "]=" + encodeParam(value, encoding);
            sb.append(token);
        }

//        return sb;
    }

    private static String buildUrlFromCollection(List<String> baseParam, Collection coll,
                                                 String encoding, boolean hasOrder) {
        StringBuilder sb = new StringBuilder();
        String token;
        if (!(coll instanceof List)) {
            coll = new ArrayList(coll);
        }
        List arrColl = (List) coll;

        if (hasOrder) {
            // 需要排序
            Collections.sort(arrColl);
        }
        //Build string of first level - related with params of provided Map
        for (int i = 0; i < arrColl.size(); i++) {

            if (sb.length() > 0) {
                sb.append('&');
            }
            Object value = (Object) arrColl.get(i);
            if (value instanceof Map) {
                List<String> baseParam2 = new ArrayList<>(baseParam);
                baseParam2.add(String.valueOf(i));
                String str = buildUrlFromMap(baseParam2, (Map) value, encoding, hasOrder);
                sb.append(str);

            } else if (value instanceof List) {
                List<String> baseParam2 = new ArrayList<>(baseParam);
                baseParam2.add(String.valueOf(i));
                String str = buildUrlFromCollection(baseParam2, (List) value, encoding, hasOrder);
                sb.append(str);
            } else {
                token = getBaseParamString(baseParam) + "[" + i + "]=" + encodeParam(value, encoding);
                sb.append(token);
            }
        }

        return sb.toString();
    }


    private static String getBaseParamString(List<String> baseParam) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < baseParam.size(); i++) {
            String s = baseParam.get(i);
            if (i == 0) {
                sb.append(s);
            } else {
                sb.append("[" + s + "]");
            }
        }
        return sb.toString();
    }

    /**
     * Check if String is either empty or null
     *
     * @param str string to check
     * @return true if string is empty. Else return false
     */
    private static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }


    private static String encodeParam(Object param, String encoding) {
        try {
            return URLEncoder.encode(String.valueOf(param), encoding);
        } catch (UnsupportedEncodingException e) {
            return URLEncoder.encode(String.valueOf(param));
        }
    }

    private static void basicTest() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("a", "1");
        params.put("b", "2");
        params.put("c", "3");

        System.out.println(httpBuildQuery(params, "UTF-8"));
    }

    private static void testWithMap() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("a", "1");
        params.put("b", "2");

        Map<String, Object> cParams = new LinkedHashMap<String, Object>();
        cParams.put("c1", "c1val");
        cParams.put("c2", "c2val");
        params.put("c", cParams);

        System.out.println(httpBuildQuery(params, "UTF-8"));
    }

    private static void testWithNestedMap() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("a", "1");
        params.put("b", "2");

        Map<String, Object> cParamsLevel1 = new LinkedHashMap<String, Object>();
        cParamsLevel1.put("cL1-1", "cLevel1-1val");
        cParamsLevel1.put("cL1-2", "cLevel1-2val");

        Map<String, Object> cParamsLevel2 = new LinkedHashMap<String, Object>();
        cParamsLevel2.put("cL2-1", "cLevel2-1val");
        cParamsLevel2.put("cL2-2", "cLevel2-2val");
        cParamsLevel1.put("cL1-3", cParamsLevel2);

        params.put("c", cParamsLevel1);

        System.out.println(httpBuildQuery(params, "UTF-8"));
    }


    private static void testWithList() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("a", "1");
        params.put("b", "2");

        List<Object> cParams = new ArrayList<Object>();
        cParams.add("c1val");
        cParams.add("c2val");
        params.put("c", cParams);

        System.out.println(httpBuildQuery(params, "UTF-8"));
    }


    private static void testWithNestedList() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("a", "1");
        params.put("b", "2");

        List<Object> cParamsLevel1 = new ArrayList<Object>();
        cParamsLevel1.add("cL1-val1");
        cParamsLevel1.add("cL12-val2");

        List<Object> cParamsLevel2 = new ArrayList<Object>();
        cParamsLevel2.add("cL2-val1");
        cParamsLevel2.add("cL2-val2");
        cParamsLevel1.add(cParamsLevel2);

        params.put("c", cParamsLevel1);

        System.out.println(httpBuildQuery(params, "UTF-8"));
    }


    private static void testCompound() {

        Map<String, Object> params = new LinkedHashMap<String, Object>();

        //flat
        params.put("a", "1");
        params.put("b", "2");

        //Map level 1
        Map<String, Object> cParamsLevel1 = new LinkedHashMap<String, Object>();
        cParamsLevel1.put("cL1-1", "cLevel1-1val");
        cParamsLevel1.put("cL1-2", "cLevel1-2val");

        //Map level 2
        Map<String, Object> cParamsLevel2 = new LinkedHashMap<String, Object>();
        cParamsLevel2.put("cL2-1", "cLevel2-1val");
        cParamsLevel2.put("cL2-2", "cLevel2-2val");
        cParamsLevel1.put("cL1-3", cParamsLevel2);

        params.put("c", cParamsLevel1);

        //List level 1
        List<Object> dParamsLevel1 = new ArrayList<Object>();
        dParamsLevel1.add("dL1-val1");
        dParamsLevel1.add("dL12-val2");

        //List level 2
        List<Object> dParamsLevel2 = new ArrayList<Object>();
        dParamsLevel2.add("dL2-val1");
        dParamsLevel2.add("dL2-val2");
        dParamsLevel1.add(dParamsLevel2);

        params.put("d", dParamsLevel1);

        System.out.println(httpBuildQuery(params, "UTF-8"));

    }
}
