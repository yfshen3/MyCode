package org.lf.admin.api.baseapi.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.lf.admin.api.baseapi.core.ServiceException;

/**
 * @description: 阿里云短信API调用
 * @author: FZ
 * @create: 2018-06-15 21:40
 **/
public class SmsUtils {

    /**
     * 状态码-返回OK代表请求成功,其他错误码详见错误码列表
     *
     *
     */
    public static String OK = "OK";

    /**
     * RAM权限DENY
     *
     * 当提示RAM权限不足时，就需要给当前使用的AK对应子账号进行授权：AliyunDysmsFullAccess（权限名称）。
     * 具体权限授权详见：https://help.aliyun.com/document_detail/55764.html?spm=5176.product44282.6.548.bKZJL2
     */
    public static String RAM_PERMISSION_DENY = "isp.RAM_PERMISSION_DENY";

    /**
     * 业务停机
     *
     * 请先查看账户余额，若余额大于零，则请通过创建工单联系工程师处理
     */
    public static String OUT_OF_SERVICE = "isv.OUT_OF_SERVICE";
    /**
     * 	未开通云通信产品的阿里云客户
     *
     * 	未开通云通信产品的阿里云客户（该AK所属的账号尚未开通云通信的服务，包括短信、语音、流量等服务）
     * 	注：阿里云短信服务包含：1、消息服务 2、云通信短信服务 3、云市场短信接口，账号和短信接口不可混用。
     * 	当出现此类提示报错需要检查当前AK是否已经开通阿里云云通信短信服务，如已开通消息服务，则参照消息服务文档调用接口。
     */
    public static String PRODUCT_UN_SUBSCRIPT = "isv.PRODUCT_UN_SUBSCRIPT";
    /**
     * 产品未开通
     *
     * 产品未订购（该AK所属的账号尚未开通当前接口的产品，如仅开通了短信服务的用户调用语音接口。）
     * ，检查AK对应账号是否已开通调用接口对应的服务。短信服务开通链接：https://www.aliyun.com/product/sms
     */
    public static String PRODUCT_UNSUBSCRIBE = "isv.PRODUCT_UNSUBSCRIBE";
    /**
     * 账户不存在
     *
     * 请确认使用的账号是否与申请的账号一致
     */
    public static String ACCOUNT_NOT_EXISTS = "isv.ACCOUNT_NOT_EXISTS";
    /**
     * 账户异常
     *
     * 请确认使用的账号是否与申请的账号一致
     */
    public static String ACCOUNT_ABNORMAL = "isv.ACCOUNT_ABNORMAL";
    /**
     * 短信模板不合法
     *
     * TemplateCode参数请传入审核通过的模板ID，
     * 模板见：见：https://dysms.console.aliyun.com/dysms.htm#/template
     */
    public static String SMS_TEMPLATE_ILLEGAL = "isv.SMS_TEMPLATE_ILLEGAL";
    /**
     * 短信签名不合法
     *
     * SignName请传入审核通过的签名内容，
     * 签名见：https://dysms.console.aliyun.com/dysms.htm#/sign
     */
    public static String SMS_SIGNATURE_ILLEGAL = "isv.SMS_SIGNATURE_ILLEGAL";
    /**
     * 参数异常
     *
     * 对照文档，检查参数格式。
     * 例：短信查询接口SendDate日期格式yyyyMMdd，错误：2017-01-01正确：20170101
     */
    public static String INVALID_PARAMETERS = "isv.INVALID_PARAMETERS";
    /**
     * isp.SYSTEM_ERROR
     *
     * 请重试接口调用，如仍存在此情况请创建工单反馈工程师查看
     */
    public static String SYSTEM_ERROR = "isp.SYSTEM_ERROR";
    /**
     * 非法手机号
     *
     * PhoneNumbers参数请传入11位国内号段的手机号码
     */
    public static String MOBILE_NUMBER_ILLEGAL = "isv.MOBILE_NUMBER_ILLEGAL";
    /**
     * 手机号码数量超过限制
     *
     * 短信接收号码,支持以英文逗号分隔的形式进行批量调用，
     * 批量上限为1000个手机号码，PhoneNumbers参数单次调用不传入过多接收号码
     */
    public static String MOBILE_COUNT_OVER_LIMIT = "isv.MOBILE_COUNT_OVER_LIMIT";
    /**
     * 模板缺少变量
     *
     * TemplateParam中需要以json格式字符串给使用的模板中出现的所有变量进行赋值。
     * 例：模板为：您好${name}，验证码${code} TemplateParam={“name”:”Tom”,”code”:”123”}
     */
    public static String TEMPLATE_MISSING_PARAMETERS = "isv.TEMPLATE_MISSING_PARAMETERS";
    /**
     * 业务限流
     *
     * 将短信发送频率限制在正常的业务流控范围内，
     * 默认流控：短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时 ，累计10条/天。
     */
    public static String BUSINESS_LIMIT_CONTROL = "isv.BUSINESS_LIMIT_CONTROL";
    /**
     * JSON参数不合法，只接受字符串值
     *
     * TemplateParam入参以Json格式字符串形式传入。例：正确{“code”:”123”}
     */
    public static String INVALID_JSON_PARAM = "isv.INVALID_JSON_PARAM";
    /**
     * 黑名单管控
     *
     * 黑名单管控是指变量内容含有限制发送的内容，变量不支持透传url，
     * 同时检查通过变量是否透传了一些敏感信息触发关键字
     */
    public static String BLACK_KEY_CONTROL_LIMIT = "isv.BLACK_KEY_CONTROL_LIMIT";
    /**
     * 参数超出长度限制
     *
     * 单个变量长度限制在20字符内。
     */
    public static String PARAM_LENGTH_LIMIT = "isv.PARAM_LENGTH_LIMIT";
    /**
     * 不支持URL
     *
     * 变量不支持透传url，同时检查通过变量是否透传了一些敏感信息触发关键字
     */
    public static String PARAM_NOT_SUPPORT_URL = "isv.PARAM_NOT_SUPPORT_URL";
    /**
     * 账户余额不足
     *
     * 转入金额不足以发送当前信息，确保余额足够发送当前短信
     */
    public static String AMOUNT_NOT_ENOUGH = "isv.AMOUNT_NOT_ENOUGH";
    /**
     * 模板变量里包含非法关键字
     *
     * 变量不支持透传url，同时检查通过变量是否透传了一些敏感信息触发关键字
     */
    public static String TEMPLATE_PARAMS_ILLEGAL = "isv.TEMPLATE_PARAMS_ILLEGAL";

    /**
     * 产品名称:云通信短信API产品,
     */
    private static final String product = "Dysmsapi";
    /**
     * 产品域名
     */
    private static final String domain = "dysmsapi.aliyuncs.com";

    private static String accessKeyId = "LTAIPenxojKKWczH";
    private static String accessKeySecret = "y335nwwTRcuNOhx02GiHMeiuECFELI";
    private static String signName = "考研信息评测系统";
    private static String identifyingTempleteCode = "SMS_137412424";

    /**
     *
     * 发送短信
     *
     * @param mobile 手机号
     * @param templateParam  参数
     * @param templateCode 模板code
     * @return
     * @throws ClientException
     */
    public static SendSmsResponse sendSms(String mobile, String templateParam, String templateCode)
            throws ClientException {

        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();

        // 必填:待发送手机号
        request.setPhoneNumbers(mobile);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);

        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    /**
     * 进入微信小程序时验证手机号
     *
     * @param mobile
     * @param code
     * @return
     */
    public static SendSmsResponse sendIdentifyingCode(String mobile, String code) {
        try {
            return sendSms(mobile, "{\"code\":\"" + code + "\"}", identifyingTempleteCode);
        } catch (ClientException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
