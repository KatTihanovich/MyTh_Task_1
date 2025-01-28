package com.sdc.factory.impl;

import com.sdc.factory.TaskFactory;
import com.sdc.entity.*;
import com.sdc.reader.TaskReader;
import java.util.ArrayList;
import java.util.List;

public class TaskFactoryImpl implements TaskFactory {

    @Override
    public List<Task> createTasksFromFile(String file) {
        List<String> data = TaskReader.readTasksFromFile(file);
        List<Task> tasks = new ArrayList<>();
        for (String line : data) {
            tasks.add(createTask(TaskType.fromString(line)));
        }
        return tasks;
    }

    @Override
    public Task createTask(TaskType taskType) {
        return switch (taskType) {
            case REGRESSION_TESTS -> new RegressionTestsTask();
            case PERFORMANCE_TESTING -> new PerfomanceTestingTask();
            case DATABASE_CONNECTION_CHECK -> new DatabaseConnectionCheckTask();
        };
    }
}