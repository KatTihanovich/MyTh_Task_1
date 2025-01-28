package com.sdc.entity;

public class RegressionTestsTask extends Task {
    private static final TaskType TASK_TYPE = TaskType.REGRESSION_TESTS;

    @Override
    public int getSleepTimeInSeconds() {
        return 6;
    }

    @Override
    public String getTaskName() {
        return TASK_TYPE.getTypeName();
    }
}