package org.lf.admin.api.baseapi.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.lf.admin.api.baseapi.core.Result;
import org.lf.admin.api.baseapi.core.ResultGenerator;
import org.lf.admin.api.baseapi.util.EhCacheUtil;
import org.lf.admin.api.baseapi.util.SmsUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @description: 短信API
 * @author: FZ
 * @create: 2018-06-15 21:50
 **/
@Service
@AllArgsConstructor
@Slf4j
public class SmsService {


    /**
     * 调用阿里云短信API发送短信
     *
     * @param mobile 手机号
     * @return
     */
    public Result sendSmsCode(String mobile) {
        Result result = ResultGenerator.genFailResult("获取验证码发生异常错误");

        String code = getRandomIdentifyingCode();
        SendSmsResponse response = SmsUtils.sendIdentifyingCode(mobile, code);
        log.debug("短信接口返回的数据----------------");
        log.debug("Code=" + response.getCode());
        log.debug("Message=" + response.getMessage());
        log.debug("RequestId=" + response.getRequestId());
        log.debug("BizId=" + response.getBizId());
        String returnCode = response.getCode();
        if (SmsUtils.OK.equals(returnCode)) {
            Cache<String, String> cache = EhCacheUtil.getCache(EhCacheUtil.CacheNameEnum.IdentifySms, String.class, String.class);
            cache.put(mobile, code);
            result = ResultGenerator.genSuccessResult();
        } else if (SmsUtils.MOBILE_NUMBER_ILLEGAL.equals(returnCode)) {
            result = ResultGenerator.genFailResult("手机号非法");
        } else if (SmsUtils.BUSINESS_LIMIT_CONTROL.equals(returnCode)) {
            result = ResultGenerator.genFailResult("对该手机号发送验证码次数达到上限");
        }
        return result;
    }

    /**
     * 检查用户输入的验证码是否和手机匹配
     * 如果验证码验证正确则插入基本信息
     *
     * @param name
     * @param mobile
     * @param code
     * @return
     */
    public Result checkCode(String name, String mobile, String code) {
        String cacheCode = getSmsCodeFromCache(mobile);
//        if (cacheCode != null) {
//            if (cacheCode.equals(code)) {
//                SurveyAnswer surveyAnswer = new SurveyAnswer();
//                surveyAnswer.setName(name);
//                surveyAnswer.setTel(mobile);
//                return surveyAnswerService.submitBaseInfo(surveyAnswer);
//            } else {
//                return ResultGenerator.genFailResult("验证码错误");
//            }
//        }
        return ResultGenerator.genFailResult("验证码错误");
    }

    public Result checkWithoutCode(String name, String mobile) {
//        SurveyAnswer surveyAnswer = new SurveyAnswer();
//        surveyAnswer.setName(name);
//        surveyAnswer.setTel(mobile);
//        return surveyAnswerService.submitBaseInfo(surveyAnswer);
            return null;
    }

    /**
     * 生成6位验证码
     *
     * @return 6位字符串验证码
     */
    public String getRandomIdentifyingCode() {
        int code = new Random().nextInt(899999) + 100000;
        return code + "";
    }

    /**
     * 从缓存中读取验证码
     *
     * @param mobile 手机号
     * @return 手机号对应的验证码
     */
    public String getSmsCodeFromCache(String mobile) {
        Cache<String, String> cache = EhCacheUtil.getCache(EhCacheUtil.CacheNameEnum.IdentifySms, String.class, String.class);
        String code = cache.get(mobile);
        return code;
    }

}
