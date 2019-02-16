package org.lf.admin.api.baseapi.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * webservice测试接口
 */
@WebService(name = "TestService",
        targetNamespace = "http://webservice.baseapi.api.admin.lf.org")
public interface TestService {

    @WebMethod
    @WebResult(name = "String", targetNamespace = "")
    String sendMessage(@WebParam(name = "username") String username);
}
