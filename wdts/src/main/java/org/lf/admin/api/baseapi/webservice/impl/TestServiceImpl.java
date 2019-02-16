package org.lf.admin.api.baseapi.webservice.impl;

import org.lf.admin.api.baseapi.webservice.TestService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@WebService(name = "TestService",
        targetNamespace = "http://webservice.baseapi.api.admin.lf.org",
        endpointInterface = "org.lf.admin.api.baseapi.webservice.TestService")
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String sendMessage(String username) {
        return "hello " + username;
    }
}
