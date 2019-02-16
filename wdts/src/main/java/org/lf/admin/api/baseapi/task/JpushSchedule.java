package org.lf.admin.api.baseapi.task;

import org.lf.admin.api.baseapi.service.AsynService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JpushSchedule {

    private static final Logger logger = LoggerFactory.getLogger(JpushSchedule.class);

    @Autowired
    AsynService asynService;

//    /**
//     *  定时任务，每半小时极光推送addFriends
//     */
//    @Scheduled(cron = "0/30 * * * * ?")
//    @Transactional(rollbackFor = Exception.class)
//    public void startAddFriend() {
//    }

}
