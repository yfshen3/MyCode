package org.lf.admin.api.baseapi.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.lf.admin.api.baseapi.core.Result;
import org.lf.admin.api.baseapi.core.ResultCode;
import org.lf.admin.api.baseapi.core.ServiceException;
import org.lf.admin.api.baseapi.util.WebUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author sunwill
 * @date 2018/04/25
 */
@Component
@Slf4j
public class AppHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        Result result = new Result();
        if (e instanceof ServiceException) {
            //业务失败的异常，如“账号或密码错误”
            result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
            log.debug(e.getMessage());
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<FieldError> fieldErrors = bindException.getFieldErrors();
            JSONArray objects = fieldErrorsToJSONArray(fieldErrors);
            result.setCode(ResultCode.BIND_FAIL).setData(objects);
            log.debug(e.getMessage());
        } else if (e instanceof NoHandlerFoundException) {
            result.setCode(ResultCode.NOT_FOUND).setMessage("链接 [" + request.getRequestURI() + "] 不存在");
        } else if (e instanceof ServletException) {
            result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            JSONArray objects = fieldErrorsToJSONArray(fieldErrors);
            result.setCode(ResultCode.BIND_FAIL).setData(objects);
            log.debug(e.getMessage());
        } else {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR)
                    .setMessage("链接 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
            String message;
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                message = String
                        .format("链接 [%s] 出现异常，方法：%s.%s，异常摘要：%s", request.getRequestURI(), handlerMethod.getBean()
                                .getClass()
                                .getName(), handlerMethod
                                .getMethod().getName(), e.getMessage());
            } else {
                message = e.getMessage();
            }
            log.error(message, e);
        }
        if (handler instanceof HandlerMethod && WebUtils.isAjax((HandlerMethod) handler)) {
            WebUtils.responseResult(response, result);
            return new ModelAndView();
        }
        ModelAndView error = new ModelAndView("error");
        error.addObject(result);
        return error;
    }

    private JSONArray fieldErrorsToJSONArray(List<FieldError> fieldErrors) {
        JSONArray objects = new JSONArray();
        for (FieldError fieldError : fieldErrors) {
            JSONObject error = new JSONObject();
            error.put("field", fieldError.getField());
            error.put("rejectedValue", fieldError.getRejectedValue());
            error.put("defaultMessage", fieldError.getDefaultMessage());
            objects.add(error);
        }
        return objects;
    }
}
