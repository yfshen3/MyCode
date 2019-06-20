package com.syf.util;

import com.syf.common.ResultBean;

public class ResultUtils {
    /**
     * 正确返回值和提示信息
     * @param msg
     * @param data
     * @return
     */
    public static ResultBean codeSuccess(String msg, Object data) {
        ResultBean result = new ResultBean();
        result.setCode(0);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * 错误返回值和提示信息
     * @param msg
     * @param data
     * @return
     */
    public static ResultBean codeError(int code, String msg, Object data) {
        ResultBean result = new ResultBean();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
