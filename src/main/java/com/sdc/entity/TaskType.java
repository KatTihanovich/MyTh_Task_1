package com.sdc.entity;

public enum TaskType {

    REGRESSION_TESTS("Regression Tests"),
    PERFORMANCE_TESTING("Performance Testing"),
    DATABASE_CONNECTION_CHECK("Database Connection Check");

    private final String typeName;

    TaskType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static TaskType fromString(String typeName) {
        for (TaskType type : TaskType.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown task type: " + typeName);
    }
}