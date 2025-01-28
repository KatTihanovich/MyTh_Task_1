package com.sdc.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.TimeUnit;

public abstract class Task implements Runnable {
    private static final Logger logger = LogManager.getLogger(Task.class);

    public abstract int getSleepTimeInSeconds();

    public abstract String getTaskName();

    @Override
    public void run() {
        try {
            logger.info(getTaskName() + " is starting.");
            TimeUnit.SECONDS.sleep(getSleepTimeInSeconds());
            logger.info(getTaskName() + " is completed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(getTaskName() + " was interrupted", e);
            throw new RuntimeException(e);
        }
    }
}