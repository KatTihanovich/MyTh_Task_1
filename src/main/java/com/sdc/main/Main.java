package com.sdc.main;

import com.sdc.factory.TaskFactory;
import com.sdc.factory.impl.TaskFactoryImpl;
import com.sdc.scheduler.TaskScheduler;
import com.sdc.entity.Task;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String FILE_PATH = "/tasks.txt";

    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler(3);
        TaskFactory taskFactory = new TaskFactoryImpl();
        List<Task> tasks = taskFactory.createTasksFromFile(FILE_PATH);
        int[] delays = {5, 7, 4};
        for (int i = 0; i < tasks.size(); i++) {
            scheduler.scheduleTask(tasks.get(i), delays[i % delays.length], TimeUnit.SECONDS);
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        scheduler.stopScheduler();
    }
}