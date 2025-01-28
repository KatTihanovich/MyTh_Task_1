package com.sdc.factory;

import com.sdc.entity.Task;
import com.sdc.entity.TaskType;
import java.util.List;

public interface TaskFactory {
    List<Task> createTasksFromFile(String file);
    Task createTask(TaskType taskType);
}