package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: MyTask
 * Package: com.sky.task
 * Description
 *
 * @Author HuanZ
 * @Create 2023/12/13 11:59
 * @Version 1.0
 */
@Component
@Slf4j
public class MyTask {
    //@Scheduled(cron = "0/5 * * * * ?")
    public void executeTask(){
      log.info("定时任务: {}",new Date());
    }
}
