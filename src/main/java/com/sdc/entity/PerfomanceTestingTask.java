package com.sdc.entity;

public class PerfomanceTestingTask extends Task {
    private static final TaskType TASK_TYPE = TaskType.PERFORMANCE_TESTING;

    @Override
    public int getSleepTimeInSeconds() {
        return 2;
    }

    @Override
    public String getTaskName() {
        return TASK_TYPE.getTypeName();
    }
}