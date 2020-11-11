package com.cav.clairvoyance.task;

import com.cav.clairvoyance.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {

    @Autowired
    private FileService fileService;

    @Scheduled(cron = "0 0 3 ? * SUN")
    private void Clear() {
        fileService.clearTempDir();
    }
}
