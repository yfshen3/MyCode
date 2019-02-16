package org.lf.admin.api.baseapi.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsynService {

    @Async("asyncServiceExecutor")
    public void SendJpush() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("current time: " + System.currentTimeMillis());
    }
}
