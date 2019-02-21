package com.xinhuanet.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduleTask {

    @Scheduled(cron = "0/10 * * * * ?")
    public void job() {
        log.info("自动任务开始执行了~~~~~~");
    }

}
