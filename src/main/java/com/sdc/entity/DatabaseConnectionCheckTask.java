package com.sdc.entity;

public class DatabaseConnectionCheckTask extends Task {
    private static final TaskType TASK_TYPE = TaskType.DATABASE_CONNECTION_CHECK;

    @Override
    public int getSleepTimeInSeconds() {
        return 4;
    }

    @Override
    public String getTaskName() {
        return TASK_TYPE.getTypeName();
    }
}